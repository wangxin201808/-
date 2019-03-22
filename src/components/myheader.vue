<template>
    <div class="wrap">
      <Row type="flex" align="middle">
        <Col span="4"><router-link to="/"><img src="../assets/img/logo/logo.jpg"></router-link></Col>
        <Col span="4" offset="8">
          <Input search clearable v-model="value" placeholder="请输入商品名称或类型" @keyup.enter.native="search" />
        </Col>
        <Col span="7">

          <Menu mode="horizontal"  active-name="-1" @on-select="select">
            <!--<MenuItem name="0" >-->
              <!---->
            <!--</MenuItem>-->
            <MenuItem name="1">
              {{nav[1]}}
            </MenuItem>
            <MenuItem name="2">
              {{nav[2]}}
            </MenuItem>
            <MenuItem name="3">
              {{nav[3]}}
            </MenuItem>
            <MenuItem name="4">
              <Icon type="md-cart" />
              {{nav[4]}}
            </MenuItem>
            <Submenu name="5" v-if="isLogin">
              <template slot="title">
                {{nav[5]}}
              </template>
              <!--<MenuGroup title="使用">-->
                <MenuItem name="5-1">个人信息</MenuItem>
                <MenuItem name="5-2">我的订单</MenuItem>
                <MenuItem name="5-4">修改密码</MenuItem>
                <MenuItem name="5-3">退出</MenuItem>
              <!--</MenuGroup>-->
            </Submenu>
            <MenuItem name="6" v-else>
              {{nav[6]}}
            </MenuItem>
          </Menu>
        </Col>
      </Row>

      <Row v-if="isEdit">
        <Col span="24" >
          <div class="bg-black"  @click="disappear">
            <div class="bg-white" >
              <div class="item" >
                <Input type="password" placeholder="请输入密码" v-model="password1" clearable autofocus v-if="isShowPassword1">
                  <Icon type="ios-lock-outline" slot="prefix" @click="showPassword1"/>
                </Input>
                <Input type="text" placeholder="请输入密码" v-model="password1" clearable autofocus v-else>
                  <Icon type="ios-unlock-outline" slot="prefix" @click="showPassword1"/>
                </Input>
              </div>
              <div class="item">
                <Input type="password" placeholder="请确认密码" v-model="password2" clearable v-if="isShowPassword2">
                  <Icon type="ios-lock-outline" slot="prefix" @click="showPassword2"/>
                </Input>
                <Input type="text" placeholder="请确认密码" v-model="password2" clearable autofocus v-else>
                  <Icon type="ios-unlock-outline" slot="prefix" @click="showPassword2"/>
                </Input>
              </div>
              <div class="message">
                <Input placeholder="请输入短信验证码" :disabled="flag" v-model="validata"></Input>
                <Button type="default" shape="circle" class="verbtn" @click="time()" :disabled="false" style="width: 130px;height: 30px"> {{second}}</Button>
              </div>

              <Button type="primary" class="item" style="width: 200px" @click.prevent="editPassword">重置密码</Button>
            </div>
          </div>

        </Col>
      </Row>
    </div>

</template>

<script>
  import {getValidator, resetPassword, getUserInfo} from "../api/api";

  export default {
      name: "myheader",
      data () {
        return {
          id: this.$store.getters.getUser.id,

          phone:'',
          // 搜索值
          value: '',
          // 判断是否登录， true：已登录， false： 未登录
          isLogin: false,

          // 蒙层是否显示
          isEdit: false,

          // 是否显示密码; true: 不显示；false：显示
          isShowPassword1:true,
          isShowPassword2:true,

          password1: '',
          password2:'',
          // 验证码
          validata:'',

          // 验证码输入框能否输入， true：不能， false: 能
          flag: true,

          nav: ['', '沙发', '桌子', '椅子', '购物车', '个人中心', '登录'],


          second: '获取短信验证码',

          // 循环计时器
          intervalId:'',
        }
      },
      methods: {
        select(name) {
          console.log(name);

          if (name == '6'){
            this.$router.push({name: 'Login'});
          }else if (name == '5-1'){
            this.$router.push({name: 'Profile'});
          }else if (name == '5-2'){
            this.$router.push({name: 'Order'});
          }else if (name == '5-3'){
            this.isLogin = false;
            console.log(this.isLogin);
            this.$store.commit('updateUser');
            this.$router.push({name: 'Login'});
          }else if (name == '5-4'){
            this.isEdit = true;
          }else {
            name = parseInt(name);

            if (name == 4) {
              this.$router.push({ name: 'ShopCart'});
            }else {
              this.$router.push({ name: 'Search', params: {id: this.nav[name]}});
            }
          }

        },
        search () {
          console.log("header",this.value);
          this.$router.push({ name: 'Search', params: {id: this.value}});
        },

        showPassword1() {
          this.isShowPassword1 = !this.isShowPassword1;
        },
        showPassword2() {
          this.isShowPassword2 = !this.isShowPassword2;
        },

        editPassword() {
          if(this.password1 == '' || this.password2 == '') {
            this.$Message.error({
              content: '密码不能为空',
              duration: 3
            })
          } else if ( this.password1.length<6 != this.password2.length < 6) {
            this.$Message.error({
              content: '密码长度不能低于6位',
              duration: 3
            })
          } else if ( this.password1 != this.password2) {
            this.$Message.error({
              content: '两次密码输入不一致，请重新输入',
              duration: 3
            })
          } else if (this.validata == ''){
            this.$Message.error({
              content: '验证码不能为空',
              duration: 3
            })
          } else {
            let params = {
              phone: this.phone,
              password: this.password1,
              validator:this.validata
            }
            resetPassword(params).then(res => {
              console.log(res.data);
              if (res.data.status == 200) {
                this.$Message.success({
                  content: res.data.message,
                  duration: 3
                })
                this.isEdit = false;
              } else {
                this.$Message.error({
                  content: '密码更新失败',
                  duration: 3
                })

              }


            })
          }
        },
        // 计时
        time() {
          var t = 60;
          var that = this;

          // 通过id获取手机号
          getUserInfo({id: this.$store.getters.getUser.id}).then(res => {
            console.log(res.data.data.phone);
            this.phone=res.data.data.phone;
            // 向手机号返送验证码
            getValidator({phone: this.phone}).then(res => {
              // 验证码发送成功
              if (res.data.status == 200) {
                this.flag = false;
                this.intervalId = window.setInterval(function(){
                  // 静止点击
                  document.getElementsByClassName("verbtn")[0].disabled = true;
                  t --;
                  that.second = t + 's';
                  console.log(",,,,,,");
                  if (t <= 0) {
                    clearInterval(this.intervalId);
                    // 可以点击
                    that.changeState();

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
          })


        },

        // 修改状态
        changeState() {
          clearInterval(this.intervalId);
          document.getElementsByClassName("verbtn")[0].disabled = false;
          this.flag = true;
          this.second = '获取验证码';
        },

        // 消失蒙层
        disappear(event) {
          console.log(event);
          if(event.clientX> 559 && event.clientX < 959) {
            if (event.clientY > 123 && event.clientY < 523) {
              this.isEdit = true;
              return;
            }
          }
          this.isEdit = false;
          console.log(this.isEdit);
        }
      },

      computed: {
        listenID() {
          return this.$store.getters.getUser.id;
        }
      },
      watch : {
        listenID: function(newVal){
          console.log("myhead", newVal);
          if (newVal == "") {
            this.isLogin = false;
          } else {
            this.isLogin = true;
          }

        },
      },
      mounted () {
        console.log(this.$store.getters.getUser);
        let username = this.$store.getters.getUser.username;
        let phone = this.$store.getters.getUser.phone;
        if (username != "" || phone != "") {
          console.log("已经登录");
          this.isLogin = true;
        }
      }
    }
</script>

<style scoped>
  .wrap {
    text-align: center;
    margin-bottom: 10px;
    padding-top: 10px;
  }
  a {
    text-decoraction: none!important;
  }
  router-link-active {
    text-decoration: none!important;
  }

  .bg-black {
    height: 950px;
    background-color: rgba(48,47,47, 0.9);
    position: fixed; top: 0; bottom: 0; left: 0; right: 0;
    z-index: 1000;
    padding: 20px;
  }

  .bg-white {
    height: 400px;
    width: 400px;
    position:absolute;
    top:50%;
    left:50%;
    margin:-350px 0 0 -200px;
    border-radius: 10px;

    background-color: #f8f8f9;
    opacity: 1;
    z-index:10000000;


    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
  }

  .item {
    width: 300px;
  }

  .message {
    width: 300px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }

</style>
