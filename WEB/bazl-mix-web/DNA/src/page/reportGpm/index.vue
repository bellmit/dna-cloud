<template>
    <div>
        <!-- <Top_nav></Top_nav> -->
        <reportGpm></reportGpm>
        <Footer></Footer>
    </div>
</template>
<script>
    import Top_nav from "../../components/top-nav";
    import reportGpm from "./reportGpm";
    import Footer from "../../components/footer";
    export default {
        components: {Footer, reportGpm, Top_nav}
    }
</script>
