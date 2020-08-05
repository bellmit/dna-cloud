<template>
  <div>
    <Top_nav></Top_nav>
    <DataBase_box></DataBase_box>
    <Footer></Footer>
  </div>
</template>
<script>
  import Top_nav from "../../components/top-nav";
  import DataBase_box from "./data_box";
  import Footer from "../../components/footer";
  export default {
    components: {Footer, DataBase_box, Top_nav}
  }
</script>
<style lang="less">

</style>

