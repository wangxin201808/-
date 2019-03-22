<template>
  <div class="wrap">
    <div class="slogan"><h2>{{todo}}</h2></div>
    <Row :gutter="24" >
      <Col span="8" v-for="(item, index) in 3" :key="index">
        <div @click="showDetail(index)">
          <img :src="values[index].img" alt="" width="417" height="300" v-if="values[index]"/>
          <div v-if="values[index]">{{values[index].name}}</div>
          <div v-if="values[index]"><span class="first">￥{{values[index].price}}</span><span class="second">￥ {{values[index].oldPrice}}</span></div>
        </div>
      </Col>

    </Row>
    <Row :gutter="24" >
      <Col span="8" v-for="(item, index) in 3" :key="index">
        <div @click="showDetail(index+3)">
          <img :src="values[index+3].img" alt="" width="417" height="300" v-if="values[index+3]" />
          <div v-if="values[index+3]">{{values[index+3].name}}</div>
          <div v-if="values[index+3]"><span class="first">￥{{values[index+3].price}}</span><span class="second">￥ {{values[index+3].oldPrice}}</span></div>
        </div>
      </Col>

    </Row>
  </div>

</template>

<script>
  import {hot, news} from '../api/api'
    export default {
      name: "NewProduct",
      props: {
        todo: String,
        url: String
      },
      data () {
          return {
            values: []
          }
      },
      methods: {
        showDetail(index) {
          let id = this.values[index].id;
          this.$router.push({name: 'Detail', params: {id} })
        }
      },
      created() {
        if (this.url == '1') {
          news().then( res => {
            let data = res.data.data.furnitures;
            let temp = [];
            for (var i = 0; i < data.length; i++) {
              let obj = {};
              obj.id = data[i].id;
              obj.name = data[i].name;
              obj.img = data[i].img1;
              obj.price = data[i].price;
              obj.oldPrice = data[i].oldPrice;
              temp.push(obj);

            }
            this.values = temp;
            console.log("news", this.values)
          })
        }
        if (this.url == '2') {
          hot().then( res => {
            this.values = res.data.data;
            console.log("hot", this.values)
          })

        }
      }
    }
</script>

<style scoped>
  .wrap {
    width: 1300px;
    margin: 100px;
  }
  .slogan {
    margin: 50px auto 20px;
    font-size: x-large;
    text-align: center;
  }

  .first{
    font-size: larger;
    font-weight: bolder;
  }
  .second {
    text-decoration: line-through;
    font-size: small;
  }


</style>
