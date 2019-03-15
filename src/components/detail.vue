<template>
    <div class="wrap">
      <Row >
        <Col span="8" class="left">
          <Row>
            <Col span="24" class="pic-zoom">
              <!--<img :src="data.img1" alt="">-->
              <!--<pic-zoom :url="data.img1" :scale="3"></pic-zoom>-->
              <Plus :url="showPic" :outShow="true" id="img"></Plus>
            </Col>
          </Row>
          <Row type="flex"  justify="space-between" class="mar-t-10">
            <Col span="5" >
                <img :src="data.img1" alt=""  id="img1" @click="addBorder('img1')" class="imgBorder">
            </Col>
            <Col span="5" >
              <img :src="data.img2" alt="" id="img2" @click="addBorder('img2')">
            </Col>
            <Col span="5" >
              <img :src="data.img3" alt="" id="img3" @click="addBorder('img3')">
            </Col>
            <Col span="5" >
              <img :src="data.img4" alt="" id="img4" @click="addBorder('img4')">
            </Col>
          </Row>
        </Col>
        <Col span="14" :offset="1" class="layout">
          <div class="description">{{data.name}}</div>
          <Row class="parameter bg" type="flex" >
            <Col span="4">
              售价
            </Col>
            <Col span="20" class="float-left price">
              ￥ {{data.price}} <span  class="oldPrice" v-if="data.oldPrice"> ￥ {{data.oldPrice}}</span>
            </Col>
          </Row>
          <Row class="parameter" >
            <Col span="4">
              规格
            </Col>
            <Col span="20" class="font-left">
                  {{data.parameter}}
            </Col>
          </Row>
          <Row class="parameter" type="flex" >
            <Col span="4">
              数量
            </Col>
            <Col span="20" class="font-left">
              <el-input-number v-model="data.num" @change="handleChange" :min="1"  label="描述文字"></el-input-number>
            </Col>
          </Row>
          <Row class="parameter">
            <Col span="4" :offset="4">
              <Button type="warning" size="large" @click="addCar">
                <Icon type="md-cart" />  加入购物车
              </Button>
            </Col>
          </Row>
        </Col>
      </Row>
    </div>
</template>

<script>
  import Plus from "./plus"
  import {getDetail, joinCar, getUserInfo} from "../api/api";
  // 图片放大器
  import PicZoom from 'vue-piczoom'
  export default {
        name: "detail",
      components:{PicZoom, Plus},
      data() {
        return {
          num: 1,
          data:{
            num: 1
          },
          showPic: '',
          order: 'img1'
        }
      },
      methods: {
        addBorder(id) {
          // console.log(id);
          this.showPic = this.data[id];
          document.getElementById('img1').classList.remove("imgBorder");
          document.getElementById('img2').classList.remove("imgBorder");
          document.getElementById('img3').classList.remove("imgBorder");
          document.getElementById('img4').classList.remove("imgBorder");
          document.getElementById(id).classList.add('imgBorder');

        },

        handleChange() {
          console.log(this.data.num);
        },
        showDetail(id) {
          // document.getElementById('img1').className = 'imgBorder';
          let param = {'id': id};
          // console.log("params", );
          getDetail(param).then( res => {
            console.log(res.data.data);
            this.data = Object.assign(res.data.data, this.data);
            this.showPic = this.data.img1;
            console.log("data", this.data);
          })
        },

        // 加入购物车
        addCar() {
          // 没有登录
          if (this.$store.getters.getUser.id == null) {
            this.$Message.error({
              content: "请先登录",
              duration: 5
            });
            this.$router.push({name: 'Login'});
            // return;
          }
          // 登录了没有填写个人信息
          let infoParams = {'id': this.$store.getters.getUser.id}
          getUserInfo(infoParams).then(res => {
            console.log("userInfoRes", res);
            if (res.data.data == null) {
              this.$Message.error({
                content: "请先完善您的个人信息",
                duration: 5
              });
              this.$router.push({name: 'Profile'});

            }

            let params = {
              'id': this.data.id,
              'num': this.data.num,
              'uId': this.$store.getters.getUser.id
            }
            joinCar(params).then( res => {
              console.log("joinCar", res);
              if (res.data.status == 200) {
                this.$Message.success("成功添加到购物车");
              } else {
                this.$Message.error("网络错误");
              }
            })
          })


        }
      },
      created() {
        console.log(this.$route.params.id);
        this.showDetail(this.$route.params.id);

      }
    }
</script>

<style scoped>

  .bg {
    background-color: #dcdee2;
    padding: 10px 0 10px 0;
  }

  .wrap {
    margin: 50px auto 0;
    width: 1300px;
    text-align: center;
  }
  .left{
  }
  .layout{
    height: 355px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  img{
    background: no-repeat;
    width:100%;
    height:100%;
  }
  .description {
    font-size: xx-large;
    color: orangered;
  }
  .parameter {
    margin-top: 20px;
    font-size: larger;
    color: #515a6e;
  }
  .float-left {
    margin-left:-20px ;
  }
  .price {
    color: red;
    font-size: xx-large;
    margin-left:-200px ;
  }
  .oldPrice {
    font-size: large;
    color: #000;
    text-decoration-line: line-through;
  }
  .font-left {
    text-align: left;
  }

  .mar-t-10 {
    margin-top: 10px;
  }

  .pic-zoom {
    /*width: 400px;*/
    /*height: 500px;*/
  }
  .imgBorder {
    border: 5px #ff9900 solid;
    border-radius: 10px;
  }
</style>
