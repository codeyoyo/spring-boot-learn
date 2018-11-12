<template>
    <div class="context-box">
        <row type="flex" justify="center">
            <i-col span='14' class="shadow">
                <h1 v-text="title"></h1>
                <p class="article-meta">
                    <span class="label label-success">原创</span>
                    <icon type="md-calendar" size='20' />
                    <span class="date" v-text="date"></span>
                </p>
                <div class="context">
                    <slot />
                </div>
            </i-col>
        </row>
        <context-btn :up="up" :next="next" />
    </div>
</template>

<script>
import contextBtn from "@/modules/common/contextBtn.vue";
export default {
  props: ["title", "date", "up", "next"],
  components: {
    contextBtn
  }
};
</script>

<style scoped>
</style>