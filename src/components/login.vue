<template>
    <div class="wrap">
      <span class="span">欢迎登陆</span>
      <Tabs size="small" class="tab">
        <TabPane label="账号登陆">
          <Form ref="formInline" :model="formInline" :rules="ruleInline" class="form">
            <FormItem prop="name">
              <Input type="text" v-model="formInline.name" placeholder="请输入用户名">
                <Icon type="ios-person-outline" slot="prepend"></Icon>
              </Input>
            </FormItem>
            <FormItem prop="password">
              <Input type="password" v-model="formInline.password" placeholder="请输入密码" v-if="!passwordShow" >
                <Icon type="ios-lock-outline" slot="prepend" @click="showPassword()"></Icon>
              </Input>
              <Input type="text" v-model="formInline.password" placeholder="请输入密码" v-if="passwordShow">
                <Icon type="ios-unlock-outline" slot="prepend" @click="showPassword()"></Icon>
              </Input>
              <router-link to="/register" class="register">注册</router-link>
            </FormItem>
            <FormItem>

              <Button type="primary" @click.native.prevent="handleSubmit">登 陆</Button>
            </FormItem>
          </Form>
        </TabPane>

        <TabPane label="手机登录">
          <Form ref="verification" :model="verification" :rules="ruleInline2" class="form">
            <FormItem prop="phone">
              <Input type="text" v-model="verification.phone" placeholder="请输入手机号">
                <Icon type="ios-person-outline" slot="prepend"></Icon>
              </Input>
            </FormItem>
            <FormItem prop="pwd">
              <Input type="text" v-model="verification.pwd" placeholder="请输入验证码" class="verification" :disabled="flag">
              </Input>
              <Button type="default" shape="circle" class="verbtn" @click="time()" > {{second}}</Button>
              <router-link to="/register" class="register">注册</router-link>
            </FormItem>
            <FormItem>

              <Button type="primary" @click="handleSubmit2">登 陆</Button>
            </FormItem>
          </Form>
        </TabPane>
      </Tabs>
      <Spin size="large" fix v-if="spinShow"></Spin>

    </div>
</template>

<script>
    import { requestLogin, getValidator, loginByPhone } from '../api/api'
    export default {
        name: "login",
      data () {
        return {
          // 是否显示加载中
          spinShow: false,
          // 验证码输入框是否可以输入，true：不可以， false：可以
          flag: true,


          // 密码是否显示
          passwordShow: false,
          formInline: {
            name: '',
            password: ''
          },
          ruleInline: {
            name: [
              { required: true, message: '请输入用户名', trigger: 'blur' }
            ],
            password: [
              { required: true, message: '请输入密码.', trigger: 'blur' },
              { type: 'string', min: 6, message: '密码长度不能低于6位', trigger: 'blur' }
            ]
          },

          second: '获取验证码',
          verification: {
            phone: '',
            pwd: ''
          },
          ruleInline2: {
            phone: [
              { required: true, message: '请输入手机号', trigger: 'blur'}
            ],
            pwd: [
              { required: true, message: '请输入验证码', trigger: 'blur' },
              { type: 'string', min: 6, message: '密码长度不能低于6位', trigger: 'blur' }
            ]
          }
        }
      },
      methods: {
        // 账号密码登录
        handleSubmit(ev) {
          this.$refs.formInline.validate((valid) => {
            if (valid) {
              this.spinShow = true;
              console.log(this.formInline);
              let params = {name: this.formInline.name, password: this.formInline.password};
              console.log(params);
              requestLogin(params).then(res => {
                this.spinShow = false;
                console.log(res);
                if (res.data.status == 200) {
                  this.$Message.success('登录成功!');
                  console.log(res.data.data.id);
                  this.$store.dispatch('asyncUpdateUserID', res.data.data.id);
                  this.$store.dispatch('asyncUpdateUserName', res.data.data.name);
                  this.$router.push({name: "MyMain"})
                } else {
                  this.$Message.error({
                    content: res.data.message,
                    duration: 5
                  });
                }

              }).catch()

            } else {
              this.$Message.error('Fail!');
            }
          })
        },

        // 手机号登录
        handleSubmit2(ev) {
          console.log("handleSubmit2************");
          this.$refs.verification.validate((valid) => {
            if (valid) {
              this.spinShow = true;
              console.log(this.verification);
              let params = {phone: this.verification.phone, validator: this.verification.pwd};
              console.log(params);
              loginByPhone(params).then(res => {
                this.spinShow = false;
                console.log(res);
                if (res.data.status == 200) {
                  this.$Message.success('登录成功!');
                  console.log(res.data.data.id);
                  this.$store.dispatch('asyncUpdateUser', res.data.data.id);
                  this.$router.push({name: "MyMain"})
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

        // 密码是否显示
        showPassword () {
          this.passwordShow = !this.passwordShow;
        },

        // 计时
        time() {
          var t = 60;
          var that = this;
          getValidator({phone: this.verification.phone}).then(res => {
            this.flag = false;
            var intervalId = window.setInterval(function(){
              // 静止点击
              document.getElementsByClassName("verbtn")[0].disabled = true;
              t --;
              that.second = t + 's';

              if (t == 0) {
                clearInterval(intervalId);
                // 可以点击
                document.getElementsByClassName("verbtn")[0].disabled = false;
                that.flag = true;
                that.second = '获取验证码';

              }
            }, 1000);
          })
        }
      },
      mounted() {
          this.$store.commit('updateUser');
      }
    }
</script>

<style scoped>
 .wrap {
   width: 280px;
   height: 370px;
   border: 1px solid #dcdee2;
   border-radius: 10px;
   margin: 90px auto;
   padding: 20px;
   background-color: rgba(249, 249, 249, 0.26);
   text-align: center;
 }
 .span{
   font-size: x-large;
   color: darkgrey;
 }
 .tab {
   margin-top: 30px;
 }
  .form{
    height: 200px;
    margin-top: 30px;
  }
  .verification{
    width: 140px;
  }
  .verbtn {
    width: 90px;
  }
  .register{
    float: right;
  }
</style>
