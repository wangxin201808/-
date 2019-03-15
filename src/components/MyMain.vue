<template>
    <div>
      <div><Carousel/></div>


      <NewProduct todo="新品上市" url="1"></NewProduct>
      <NewProduct todo="热销商品" url="2"></NewProduct>

    </div>
</template>

<script>

  import Carousel from "../components/Carousel"
  import NewProduct from "../components/NewProduct"
  export default {
    name: "MyMain",
    components: { Carousel, NewProduct }
  }
</script>

<style scoped>

</style>
