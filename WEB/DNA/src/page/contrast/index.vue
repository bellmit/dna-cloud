<template>
    <div>
        <top_nav></top_nav>
        <ContrastBox></ContrastBox>
    </div>
</template>
<script>
    import Top_nav from "../../components/top-nav";
    import ContrastBox from "./contrast_box";
    export default {
        components: {ContrastBox, Top_nav}
    }
</script>
