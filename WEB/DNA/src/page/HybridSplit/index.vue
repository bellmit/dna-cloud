<template>
  <div>
    <Top_nav></Top_nav>
    <HybridSplitBox></HybridSplitBox>
    <Footer></Footer>
  </div>
</template>
<script>
  import Top_nav from "../../components/top-nav";
  import HybridSplitBox from "./HybridSplit_box";
  import Footer from "../../components/footer";
  export default {
    components: {Footer, HybridSplitBox, Top_nav}
  }
</script>
<style>

</style>
