<template>
    <div v-highlight>
        <post-main :title="title" :date='date' :up='up' :next='next'>
            <router-view></router-view>
        </post-main>
    </div>
</template>

<script>
import postMain from "@/modules/common/postMain";
import { setInterval } from "timers";

const eachRouters = function(hashList, routers) {
  var buildResult = function(item) {
    var obj = {
      title: "",
      date: "",
      up: "",
      next: ""
    };
    if (item.title) {
      obj.title = item.title;
    }
    if (item.date) {
      obj.date = item.date;
    }
    return obj;
  };
  for (var r in routers) {
    let rItem = routers[r];
    if (hashList[0] == rItem.name) {
      if (rItem.children && hashList.length > 1) {
        for (var c in rItem.children) {
          let cItem = rItem.children[c];
          if (cItem.name == hashList[1]) {
            return buildResult(cItem);
          }
        }
      } else {
        return buildResult(rItem);
      }
    }
  }
  return {};
};
const findRouters = function(routes, hashList, getUp) {
  var resultUrl = "";
  for (var i = 0; i < routes.length; i++) {
    var item = routes[i];
    if (item.name == hashList[0]) {
      var iPath = item.path;
      var jPath = "";
      if (item.children && hashList.length > 1) {
        for (var j = 0; j < item.children.length; j++) {
          var jtem = item.children[j];
          if (jtem.name == hashList[1]) {
            if (getUp) {
              if (j - 1 >= 0) {
                jPath = "/" + item.children[j - 1].path;
                break;
              }
            } else {
              if (j + 1 < item.children.length) {
                jPath = "/" + item.children[j + 1].path;
                break;
              }
            }
          }
        }
        if (iPath && jPath) {
          resultUrl = iPath + jPath;
          break;
        } else {
          if (getUp) {
            if (i - 1 > 0) {
              var temp = routes[i - 1];
              iPath = temp.path;
              if (temp.children) {
                jPath = "/" + temp.children[temp.children.length - 1].path;
              }
              resultUrl = iPath + jPath;
              break;
            }
          } else {
            if (i + 1 < routes.length) {
              var temp = routes[i + 1];
              iPath = temp.path;
              if (temp.children) {
                jPath = "/" + temp.children[0].path;
              }
              resultUrl = iPath + jPath;
              break;
            }
          }
        }
      }
    }
  }
  if (!resultUrl) {
    resultUrl = "/";
  }
  return resultUrl;
};
export default {
  data() {
    return {
      title: "",
      date: "",
      up: "",
      next: "",
      currLocal: ""
    };
  },
  components: {
    postMain
  },
  methods: {
    variateBulid() {
      let routers = this.$router.options.routes;
      var hash = window.location.hash;
      this.currLocal = hash;
      let replaceHash = hash.replace("#/", "");
      let hashList = replaceHash.split("/");
      let obj = eachRouters(hashList, routers);
      this.title = obj.title;
      this.date = obj.date;
      this.up = findRouters(routers, hashList, true);
      this.next = findRouters(routers, hashList, false);
    }
  },
  created() {
    this.variateBulid();
    setInterval(() => {
      var has = window.location.hash;
      if (has != this.currLocal) {
        this.variateBulid();
      }
    }, 1000);
  }
};
</script>

<style scoped>
</style>