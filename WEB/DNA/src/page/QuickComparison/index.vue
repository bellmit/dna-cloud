<template>
    <div>
        <top_nav></top_nav>
        <QuickComparisonBox></QuickComparisonBox>
    </div>
</template>
<script>
    import Top_nav from "../../components/top-nav";
    import QuickComparisonBox from "./QuickComparison_box";
    export default {
        components: {QuickComparisonBox, Top_nav}
    }
</script>
