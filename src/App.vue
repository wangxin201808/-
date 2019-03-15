<template>
  <div id="app">

      <header><MyHeader/></header>
      <!--<div><my-main/></div>-->
    <router-view></router-view>
      <footer><MyFooter/></footer>

  </div>
</template>

<script>

  import MyHeader from "./components/MyHeader"
  import MyMain from "./components/MyMain"
  import MyFooter from "./components/MyFooter"
  // import {getTradeNo} from './api/api'

  export default {
    name: 'App',
    components: { MyHeader, MyMain, MyFooter },
    mounted() {
      window.addEventListener('unload', this.saveState);

    },
    methods: {
      saveState() {
        sessionStorage.setItem('state', JSON.stringify(this.$store.state));
      },


      getPath () {
        console.log(this.$route.path);
        return this.$route.path;
      }

    },

  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    /*text-align: center;*/
    color: #2c3e50;
    padding-bottom: 50px;
  }


</style>
