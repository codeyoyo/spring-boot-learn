<template>
  <div id="app">
    <div class="menu wrapper-header-nav">
      <i-menu mode="horizontal" theme='light' active-name="1" @on-select="toLink">
        <div :key="index" v-for="(item,index) in routerList">
          <menu-item :name="item.path" v-if="!item.children">
              <Icon type="ios-paper" />
                {{item.cnName}}
          </menu-item>
          <submenu v-if="item.children" :name='item.name'>
              <template slot="title">
                  <icon type="md-cog" />
                  {{item.cnName}}
              </template>
              <menu-item :key="idx" v-for="(im,idx) in item.children" :name="item.path+'/'+im.path">{{im.cnName}}</menu-item>
          </submenu>
        </div>
      </i-menu>
    </div>
    <div style="height:61px;"></div>
    <router-view/>
    <bottom />
    <back-top></back-top>
  </div>
</template>

<script>
import bottom from "@/modules/common/bottom";
import hljs from "highlight.js";
export default {
  name: "App",
  data() {
    return {
      routerList: []
    };
  },
  components: {
    bottom
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
  },
  watch: {
    $route: {
      handler: function(val, oldVal) {
        this.$nextTick(function() {
          //页面加载完成后执行
          document.body.scrollTop = document.documentElement.scrollTop = 0;
          let blocks = document.querySelectorAll("pre code");
          blocks.forEach(block => {
            hljs.highlightBlock(block);
          });
        });
      },
      // 深度观察监听
      deep: true
    }
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
