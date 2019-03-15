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
                <MenuItem name="5-3">退出</MenuItem>
              <!--</MenuGroup>-->
            </Submenu>
            <MenuItem name="6" v-else>
              {{nav[6]}}
            </MenuItem>
          </Menu>
        </Col>
      </Row>
    </div>

</template>

<script>
    export default {
      name: "myheader",
      data () {
        return {
          id: this.$store.getters.getUser.id,
          // 搜索值
          value: '',
          // 判断是否登录， true：已登录， false： 未登录
          isLogin: false,
          nav: ['', '沙发', '桌子', '椅子', '购物车', '个人中心', '登录']
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


</style>
