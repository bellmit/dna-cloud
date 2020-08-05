<template>
  <div>
    <Top_nav></Top_nav>
    <ComScheduleBox></ComScheduleBox>
    <!-- <Footer></Footer> -->
  </div>
</template>
<script>
  import Top_nav from "../../components/top-nav";
  import ComScheduleBox from "./ComSchedule_box";
//   import Footer from "../../components/footer";
  export default {
    components: {ComScheduleBox, Top_nav}
  }
</script>
