<template>
  <div>
    <Top_nav></Top_nav>
    <QuickMixedSplit></QuickMixedSplit>
    <Footer></Footer>
  </div>
</template>
<script>
  import Top_nav from "../../components/top-nav";
  import QuickMixedSplit from "./quick_mixed_split";
  import Footer from "../../components/footer";
  export default {
    components: {Footer, QuickMixedSplit, Top_nav}
  }
</script>
<style>

</style>
