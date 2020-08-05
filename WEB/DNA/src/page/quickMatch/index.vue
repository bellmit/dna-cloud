<template>
  <div>
    <Top_nav></Top_nav>
    <QuickMatch></QuickMatch>
  </div>
</template>
<script>
  import Top_nav from "../../components/top-nav";
  import QuickMatch from "./quick_match";
  export default {
    components: {QuickMatch, Top_nav}
  }
</script>
