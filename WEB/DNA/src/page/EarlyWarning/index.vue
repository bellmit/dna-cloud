<template>
  <div>
    <Top_nav></Top_nav>
    <EarlyWarning_box></EarlyWarning_box>
    <!-- <Footer></Footer> -->
  </div>
</template>
<script>
  import Top_nav from "../../components/top-nav";
  import EarlyWarning_box from "./EarlyWarning_box";
  // import Footer from "../../components/footer";
  export default {
    components: {EarlyWarning_box, Top_nav}
  }
</script>
<style lang="less">

</style>

