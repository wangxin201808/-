<template>
  <div>

    <el-carousel :interval="4000" type="card" height="400px">
      <el-carousel-item v-for="item in obj" :key="item.id">
          <img v-bind:src="item.img" alt="item.name" width="759.6" height="400" @click="showDetail(item.id)">

      </el-carousel-item>
    </el-carousel>
  </div>

</template>

<script>
  import { getRecommend } from '../api/api'
    export default {
      // 轮播图
      name: "Carousel",
      data () {
        return {
          obj: {}
        }
      },
      methods: {
        showDetail(id) {
          this.$router.push({name: 'Detail', params: {id: id} })
        },
        getPic() {
          getRecommend().then( res => {
            console.log(res.data.data);
              this.obj = res.data.data;
          })
        }
      },
      mounted() {
        this.getPic();
      }
    }
</script>

<style scoped>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
