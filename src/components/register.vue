<template>
  <Row>
    <Col span="16" :offset="4" >
      <div class="wrap">
        <Steps :current="current" class="steps">
          <Step title="注册手机号"></Step>
          <Step title="设置密码"></Step>
          <Step title="完成"></Step>
        </Steps>
        <div class="phoneWrap">
          <PhoneRegister ref="PhoneRegister" v-on:sendCode="changeCode" v-if="current === 0"></PhoneRegister>
          <SecretRegister ref="SecretRegister" v-on:sendCode="changeCode" v-if="current === 1"></SecretRegister>
          <img src="../assets/img/icons/success.png" alt="任务完成" v-if="current === 2"/>
          <Button type="primary" @click="next" v-if="isNext">下一步</Button>
          <span v-else class="input">{{message}}</span>
        </div>
      </div>
    </Col>
  </Row>

</template>

<script>
  import PhoneRegister from "../components/phoneRegister"
  import SecretRegister from "../components/secretRegister"
    export default {
      name: "register",
      data () {
        return {
          code: 0,
          isNext: true,
          current: 0,
          message: ""
        }
      },
      components: {PhoneRegister, SecretRegister},
      methods: {
        changeCode (data) {
          this.code = data;
          console.log("changcode", this.code);
          if (this.code == 200) {// 成功注册
            this.current = 1;
          }
          if (this.code == 300) {
            this.current = 2;
            this.isNext = false;

            var that = this;
            var t = 5;
            var intervalId = window.setInterval(function(){
              t --;
              that.message = '注册成功，' + t + '秒后跳转到首页';
              if (t <= 0) {
                clearInterval(intervalId);
                that.$router.push({name: "MyMain"});
              }
            }, 1000);
          }
        },


        next () {
          console.log(this.current);

          if (this.current == 0) {
            this.$refs.PhoneRegister.handleSubmit();

          } else {
            this.$refs.SecretRegister.handleSubmit();

          }

        }
      }
    }
</script>

<style scoped>

  .wrap {
    width: 100%;
    margin: 100px auto;
    padding: 20px;
    border-radius: 10px;
    background-color: #f8f8f9;
    overflow: hidden;
  }
  .phoneWrap {
    width: 300px;
    margin: 40px auto;
    text-align: center;
  }
  .steps {
    width: 140%;
  }
  .input {
    display: inline-block;
    color: #2d8cf0;
  }

</style>
