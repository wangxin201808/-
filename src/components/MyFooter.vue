<template>
  <div class="bg">
    <Row class="#515a6e">
        <h3 class="footer-title">联系我们</h3>
    </Row>
    <Row>
      <Col  span="8">
        <Icon type="md-home" />
        地址：禄口金宝小商品市场 <span>家具区 二楼</span>
      </Col >
      <Col  span="8">
        <Icon type="ios-phone-portrait" />电话: 138-8888-8888
        <Icon type="ios-call-outline" />
        <span>固定电话: 025-86199056</span>
      </Col >
      <Col  span="8">
        <Icon type="md-mail" />
        <span>邮箱: 1192693362@qq.com</span>
      </Col >
    </Row>
    <div class="text-right">版权 &copy; 2019.我的商城.</div>
  </div>

</template>

<script>
    export default {
        name: "MyFooter"
    }
</script>

<style scoped>
  .bg {
    background-color: #808695;
    text-align: center;
    width: 100%;
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
  }
</style>
