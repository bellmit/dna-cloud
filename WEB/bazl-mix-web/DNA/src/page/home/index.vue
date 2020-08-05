<template>
  <div>
    <!-- <Top_nav></Top_nav> -->
    <HomeBox></HomeBox>
    <Footer></Footer>
  </div>
</template>
<script>
  import Top_nav from "../../components/top-nav";
  import HomeBox from "./home_box";
  import Footer from "../../components/footer";
  export default {
    name:"Home",
    components: {Footer, HomeBox, Top_nav}
  }
</script>
