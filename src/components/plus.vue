<template>
  <div class="pic-img">
    <div class="img-container" @mousemove="!moveEvent && mouseMove($event)" @mouseleave="!leaveEvent && mouseLeave($event)">
      <img ref="img" @load="imgLoaded" :src="url" style="width:100%"></img>
      <div v-if="!hideZoom && imgLoadedFlag && showBg" :class="['img-selector',{'circle':type === 'circle'}]" :style="[imgSelectorSize,imgSelectorPosition,!outShow && imgSelectorBg ,!outShow && imgBgPosition]"> </div>
      <div v-if="outShow" v-show="!hideOutShow" class="img-out-show" :style="[imgOutShowSize,imgSelectorBg,imgBgPosition]"></div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'plus',
    data() {
      return {
        selector: {
          width: 166,
          halfWidth: 83,
          top: 0,
          left: 0,
          bgTop: 0,
          bgLeft: 0,
          rightBound: 0,
          bottomBound: 0,
          absoluteLeft: 0,
          absoluteTop: 0
        },
        imgInfo: {},
        hideOutShow: true,
        imgLoadedFlag: false,
        screenWidth: document.body.clientWidth,
        timer: null,
        showBg: true
      };
    },
    props: {
      url: String,
      highUrl: String,
      type: {
        type: String,
        default: 'square',
        validator: function(value) {
          return ['circle', 'square'].indexOf(value) !== -1;
        }
      },
      scale: {
        type: Number,
        default: 3
      },
      moveEvent: {
        type: [Object, MouseEvent],
        default: null
      },
      leaveEvent: {
        type: [Object, MouseEvent],
        default: null
      },
      hideZoom: {
        type: Boolean,
        default: false
      },
      outShow: {
        type: Boolean,
        default: false
      }
    },
    watch: {
      moveEvent(e) {
        console.log("moveEvent");
        this.mouseMove(e);

      },
      leaveEvent(e) {
        this.mouseLeave(e);
      },
      url(e)  {
        console.log("plus.url");
        this.$watch.moveEvent();
        // this.leaveEvent(e);
        this.imgLoadedFlag = false;
      },
      screenWidth(val) {
        if (!this.timer) {
          this.screenWidth = val;
          this.timer = setTimeout(() => {
            this.imgLoaded();
            clearTimeout(this.timer);
            this.timer = null;
          }, 400);
        }
      }
    },
    computed: {
      imgSelectorPosition() {
        let { top, left } = this.selector;
        return {
          top: `${top}px`,
          left: `${left}px`
        };
      },
      imgSelectorSize() {
        let width = this.selector.width;
        return {
          width: `${width}px`,
          height: `${width}px`
        };
      },
      imgOutShowSize() {
        let {
          scale,
          selector: { width }
        } = this;
        return {
          width: `${width * scale}px`,
          height: `${width * scale}px`
        };
      },
      imgSelectorBg() {
        let {
          scale,
          url,
          highUrl,
          imgInfo: { height, width }
        } = this;
        return {
          backgroundImage: `url(${highUrl || url})`,
          backgroundSize: `${width * scale}px ${height * scale}px`
        };
      },
      imgBgPosition() {
        let { bgLeft, bgTop } = this.selector;
        return {
          backgroundPosition: `${bgLeft}px ${bgTop}px`
        };
      }
    },
    methods: {
      imgLoaded() {
        // 得到图片的位置信息
        let imgInfo = this.$refs['img'].getBoundingClientRect();
        if (JSON.stringify(this.imgInfo) == JSON.stringify(imgInfo)) {  // 位置不变不更新
          return;
        }
        this.imgLoadedFlag = true;
        // 分别得到图片的{ 宽， 高， 距离左部， 距离顶部 } 的距离
        let { width, height, left, top } = (this.imgInfo = imgInfo);

        let selector = this.selector;
        // 得到放大框的宽，和宽的一半
        let { width: selectorWidth, halfWidth: selectorHalfWidth } = selector;
        // 得到滚轮对于页面的值
        let { scrollLeft, scrollTop } = document.documentElement;
        // 得到放大框相对于图片向右移动的距离
        selector.rightBound = width - selectorWidth;
        // 得到放大框相对于图片向下移动的距离
        selector.bottomBound = height - selectorWidth;
        // 等到放大镜中心相对于界面的左部边界
        selector.absoluteLeft = left + selectorHalfWidth + scrollLeft;
        // 得到放大框中心相对于界面的顶部边界
        selector.absoluteTop = top + selectorHalfWidth + scrollTop;
      },
      reset() {
        Object.assign(this.selector, {
          top: 0,
          left: 0,
          bgLeft: 0,
          bgTop: 0
        });
      },
      mouseMove(e) {
        if (!this.hideZoom && this.imgLoadedFlag) {
          this.imgLoaded();   //防止img位置变化
          // 鼠标得到在页面的位置
          let { pageX, pageY } = e;
          let { scale, selector } = this;
          let {
            halfWidth,
            absoluteLeft,
            absoluteTop,
            rightBound,
            bottomBound
          } = selector;
          let x = pageX - absoluteLeft; // 选择器的x坐标 相对于图片
          let y = pageY - absoluteTop; // 选择器的y坐标
          if (this.outShow) {
            halfWidth = 0;
            this.hideOutShow = false;
            this.showBg =true;
          }
          selector.top = y > halfWidth ? (y < bottomBound - halfWidth ? y : bottomBound - halfWidth) : halfWidth;
          selector.left = x > halfWidth ? (x < rightBound - halfWidth ? x : rightBound - halfWidth) : halfWidth;
          // selector.bgLeft = halfWidth - (halfWidth + x) * scale ; // 选择器图片的坐标位置
          // selector.bgTop = halfWidth - (halfWidth + y) * scale;

          // selector.bgLeft = selector.bgLeft < 0 ? 0 : selector.bgLeft
          // selector.bgTop = selector.bgTop < 0 ? 0 : selector.bgTop
          selector.bgLeft = - selector.left * scale;
          selector.bgTop = - selector.top * scale;
        }
      },
      mouseLeave() {
        if (this.outShow) {
          this.hideOutShow = true;
          this.showBg =false;
        }
      }
    }
  };
</script>

<style scoped>
  .img-container {
    position: relative;
  }

  .img-selector {
    background-color: rgba(0, 0, 0, 0.6);
    position: absolute;
    background-repeat: no-repeat;
    cursor: crosshair;
    border: 1px solid rgba(0, 0, 0, 0.1);
  }

  .img-selector.circle {
    border-radius: 50%;
  }

  .img-out-show {
    position: absolute;
    top: 0;
    right: 0;
    background-repeat: no-repeat;
    transform: translate(100%, 0);
    border: 1px solid rgba(0, 0, 0, 0.1);
    z-index: 1000;
  }
</style>
