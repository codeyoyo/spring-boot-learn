<template>
    <div class="btns">
        <row type="flex" justify="center">
            <i-col span='12'>
                <i-button type="primary" @click="upClick">
                    <icon type="ios-arrow-back"></icon>
                    <router-link :to="{ path: up}">上一篇</router-link>
                </i-button>
            </i-col>
            <i-col span='12'>
                <i-button type="primary" @click="nextClick">
                    <router-link :to="{ path: next}">下一篇</router-link>
                    <icon type="ios-arrow-forward"></icon>
                </i-button>
            </i-col>
        </row>
    </div>
</template>

<script>
export default {
  props: ["up", "next"],
  methods: {
    upClick() {
        if(this.up){
            this.$router.push(this.up);
        }
    },
    nextClick() {
        if(this.next){
            this.$router.push(this.next);
        }
    }
  }
};
</script>

<style scoped lang='less'>
.btns {
  line-height: 60px;
  text-align: center;
  a {
    color: #fff;
  }
}
</style>