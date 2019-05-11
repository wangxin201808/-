<template>
    <div class="wrap">
      <div class="title">卖家留言</div>
      <div class="pingLun">
        <textarea rows="8" cols="100" v-model="talk"></textarea>
        <Button type="primary" class="send" @click="send">发表</Button>
      </div>
      <div class="ping" v-for="item in pingLuns" :key="item.created">
        <div class="userName">{{item.userId}}：</div>
        <p>{{item.created | data}}</p>
        <div class="say">{{item.ping}}</div>
      </div>
    </div>
</template>

<script>
  import {getPinglun, savePinglun} from '../api/api'
  import Vue from 'vue'
  import moment from 'moment'

  Vue.filter('data', function (value, format) {
    return moment(value).format(format || 'YYYY-MM-DD HH:mm:ss')
  })
    export default {
      name: "PingLun",
      props: ['pId'],
      data () {
        return {
          pingLuns: '',
          username: '李四',
          time: '2019年4月19日16:53:43',
          ping:'文件xxxxxxxxxxxxxx',
          talk:''
        }
      },
      methods: {
        send() {
          if (this.$store.getters.getUser.id == '') {
            this.$Message.error({
              content: '您还没有登录，请您登录',
              duration: 5
            })
            this.$router.push({name: 'Login'})
          }
          console.log(this.talk);
          console.log(this.pId);
          console.log(this.$store.getters.getUser.id);

          let params = {
            shopId: this.pId,
            userId: this.$store.getters.getUser.id,
            ping: this.talk
          }
          savePinglun(params).then( result => {
            if (result.data.status === 200) {
              this.$Message.success({
                content: '评论发表成功',
                duration: 3
              })
              this.talk = '';

              this.getPingluns();
            }
          });
        },
        getPingluns() {
          let params = {
            shopId: this.pId
          }
          getPinglun(params).then( res => {
            console.log(res);
            this.pingLuns = res.data.data;
          });
        }
      },
      mounted () {
        this.getPingluns();

      }
    }
</script>

<style scoped>
  .wrap {
    background-color: #f8f8f9;
    margin-bottom: 100px;
    border-radius: 10px;
  }
  .title {
    font-size: xx-large;
    text-align: left;
    padding: 0 15px;
  }
  .ping {
    background-color: #f8f8f9;
    padding: 0 100px 10px 100px;
    text-align: left;

  }
  .ping .userName {
    font-size: x-large;
  }
  .ping .say {
    background-color: #f8f8f9;
    padding: 5px 10px;
    border-bottom: 1px solid #808695;
    font-size: larger;
  }
  textarea {
    font-size: larger;
  }
  .send {
    position: relative;
    top: -11px;
  }
</style>
