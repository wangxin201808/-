<template>
  <div>
    <Form ref="verification" :model="verification" :rules="ruleInline2" class="form">
      <FormItem prop="phone">
        <Input type="text" v-model="verification.phone" placeholder="请输入手机号">
          <Icon type="ios-phone-portrait" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem prop="pwd">
        <Input type="text" v-model="verification.pwd" placeholder="请输入验证码" class="verification" :disabled="flag">
        </Input>
        <Button type="default" shape="circle" class="verbtn" @click="time()" :disabled="false"> {{second}}</Button>
      </FormItem>

    </Form>


  </div>
</template>

<script>
  import {getValidator, testPhone} from '../api/api'
    export default {
      name: "phoneRegister",
      data() {
          return {
            // 输入框能否输入， true：不能， false: 能
            flag: true,
            // 定时器
            intervalId:'',
            second: '获取验证码',
            verification: {
              phone: '',
              pwd: ''
            },
            ruleInline2: {
              phone: [
                { required: true, message: '请输入手机号', trigger: 'blur'},
                { type: 'regexp', pattern: "/^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/", message: '邮箱不符合规范', trigger: 'blur' }
              ],
              pwd: [
                { required: true, message: '请输入验证码', trigger: 'blur' },
                { type: 'string', min: 6, message: '密码长度不能低于6位', trigger: 'blur' },
              ]
            }
          }
      },
      methods: {

        // 手机号注册
        handleSubmit(ev) {
          console.log("handleSubmit2************");
          this.$refs.verification.validate((valid) => {
            if (valid) {
              console.log(this.verification);
              // this.spinShow = true;
              clearInterval(this.intervalId);



              let params = {phone: this.verification.phone, validator: this.verification.pwd};
              console.log("params",params);
              testPhone(params).then(res => {

                // this.spinShow = false;
                console.log(res);
                if (res.data.status == 200) {
                  this.$Message.success('成功完成第一步!');
                  this.$store.dispatch('asyncUpdateUserPhone', params.phone);

                  this.$emit("sendCode",200);

                } else {
                  this.$Message.error({
                    content: res.data.message,
                    duration: 5
                  });
                }

              }).catch()

            } else {
              this.$Message.error('手机号或是验证码错误');
            }
          })
        },

        showPassword () {
          this.passwordShow = !this.passwordShow;
        },

        // 计时
        time() {
          var t = 60;
          var that = this;
          getValidator({phone: this.verification.phone}).then(res => {
            // 验证码发送成功
            if (res.data.status == 200) {
              this.flag = false;
              this.intervalId = window.setInterval(function(){
                // 静止点击
                document.getElementsByClassName("verbtn")[0].disabled = true;
                t --;
                that.second = t + 's';

                if (t <= 0) {
                  clearInterval(this.intervalId);
                  // 可以点击
                  this.changeState();

                }
              }, 1000);
            }

            // 验证码发送失败
            else {
              this.$Message.error({
                content: res.data.message,
                duration: 5
              });
            }
          })
        },

        // 修改状态
        changeState() {
          document.getElementsByClassName("verbtn")[0].disabled = false;
          that.flag = true;
          that.second = '获取验证码';
        }
      }

    }
</script>

<style scoped>

  .verification{
    width: 200px;
  }
  .verbtn {
    width: 90px;
  }
  .demo-spin-icon-load{
    animation: ani-demo-spin 1s linear infinite;
  }

</style>
