<template>
    <div>
        <!-- <Top_nav></Top_nav> -->
        <BreakBox></BreakBox>
        <Footer></Footer>
    </div>
</template>
<script>
    import Top_nav from "../../components/top-nav";
    import BreakBox from "./break_box";
    import Footer from "../../components/footer";
    export default {
        components: {Footer, BreakBox, Top_nav}
    }
</script>
