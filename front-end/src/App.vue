<template>
  <div id="app">
    <div class="menu wrapper-header-nav">
      <i-menu mode="horizontal" theme='light' active-name="1" @on-select="toLink">
        <div :key="index" v-for="(item,index) in routerList">
          <menu-item :name="item.path" v-if="!item.children">
              <Icon type="ios-paper" />
                {{item.cnName}}
          </menu-item>
          <submenu v-if="item.children" name=''>
              <template slot="title">
                  <icon type="md-cog" />
                  {{item.cnName}}
              </template>
              <menu-item :key="idx" v-for="(im,idx) in item.children" :name="im.path">{{im.cnName}}</menu-item>
          </submenu>
        </div>
      </i-menu>
    </div>
    <router-view/>
    <back-top></back-top>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      routerList: []
    };
  },
  methods: {
    toLink(name) {
      if (name) {
        this.$router.push(name);
      }
    }
  },
  created() {
    this.routerList = this.$router.options.routes;
    console.log("routerList", this.routerList);
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  overflow: hidden;
}

.wrapper-header-nav {
    position: fixed;
    width: 100%;
    top: 0;
    right: 0;
    z-index: 1000;
}
</style>
