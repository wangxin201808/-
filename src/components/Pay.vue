<template>
  <div class="wrap">
    <Row class="test">
      <Col span="20" :offset="2" class="">
        <div v-for="(item, index) in address" class="content">
          <div class="left">
            <span class="dot" v-if="index != 0"></span>
            <span v-else><img src="../assets/img/icons/truck.png" alt="" width="20px" height="20px" ></span>
            <span class="long" v-if="index != total"></span>
          </div>
          <div class="right" v-if="index != 0">
            {{address[total - index].datetime}}<br />
            {{address[total - index].remark}}
          </div>
          <div class="right doing" v-else>
            {{address[total - index].datetime}}<br />
            {{address[total - index].remark}}
          </div>

        </div>


      </Col>
    </Row>
  </div>

</template>

<script>
  import {getKuaidi} from '../api/api'
    export default {
        name: "Pay",
      data () {
          return {
            address:{},
            total:0
          }
      },
      mounted() {
        console.log("id", this.$route.params.id);
        let params = {id: '3834533818225', com: 'yd'};


        getKuaidi(params).then( res => {
          console.log(res);
          this.address = res.data.data.result.list
          this.total = this.address.length - 1 ;
        })
      },
      methods: {

      }
    }
</script>

<style scoped>
  @-webkit-keyframes masked-animation {
    0% {
      background-position: 0 0
    }
    to {
      background-position: -100% 0
    }
  }

  .wrap {
    width: 1000px;
    margin: 10px auto;
    padding: 20px;
    background-color: #f8f8f9;
    border-radius: 20px;
  }
  .test {

  }
  .content {
    margin-top: 10px;
    font-size: large;
    color: #808695;
    height: 100px;
  }
  .left {
    position: absolute;
    float: left;
    width: 20px;
  }
  .dot {
    display: inline-block;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background-color: #ddd;
    position: relative;
    top:0;
  }
  .doing {
    font-size: x-large;
    font-weight: 900;
    color: hsla(0, 0%, 100%, .65);
    background-image: -webkit-linear-gradient(left, #2db7f5, #19be6b 25%, #2db7f5 50%, #19be6b 75%, #cddc39);
    -webkit-text-fill-color: transparent;
    -webkit-background-clip: text;
    -webkit-background-size: 200% 100%;
    -webkit-animation: masked-animation 2s infinite linear;
  }
  .long {
    display: inline-block;
    margin-top: -10px;
    margin-left: 7px;
    width: 5px;
    height: 110px;
    background-color: #c5c8ce;
  }
  .right {
    margin-left: 30px;
  }



</style>
