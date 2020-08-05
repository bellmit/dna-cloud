<template>
  <div>
    <!-- <Top_nav></Top_nav> -->
    <QuickMatch></QuickMatch>
  </div>
</template>
<script>
  import Top_nav from "../../components/top-nav";
  import QuickMatch from "./quick_match";
  export default {
    components: {QuickMatch, Top_nav},
    beforeRouteEnter(to, from, next){
      next()
    },
    beforeRouteLeave(to, from, next){
      if(to.path != '/refresh'){
        sessionStorage.setItem('quickMatch',0)
      }
      next()
    },
  }
</script>
