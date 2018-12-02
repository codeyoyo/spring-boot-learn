<template>
  <div>
    <div class="hello" v-lazy>
      <div class="context">
        <h1 class="tada animated">Spring Boot<span>2.0</span></h1>
        <h2>学习笔记</h2>
        <p>简介：我第一次接触到Spring Boot这个框架是在2017年11月，那时候公司对系统进行了重新架构，偶合之下接触以及学到Spring Boot 这个框架；因为某些原因离开了上家公司，来到新公司是做华为外包的，信息这方面比较封闭，我们PM希望我们能分享下外面的新知识，所以我就再重新学习Spring Boot做下这一系列的学习笔记与大家分享共勉。</p>
      </div>
    </div>
    <start/>
    <first-app/>
  </div>
</template>

<script>
import Start from "@/modules/HelloWorld/Start";
import FirstApp from "@/modules/HelloWorld/FirstApp";
export default {
  name: "HelloWorld",
  data() {
    return {};
  },
  components: {
    Start,
    FirstApp
  },
  methods: {},
  created() {}
};
</script>

<style scoped lang='less'>
.hello {
  background: url("../assets/bg.jpg");
  background-size: 100% 100%;
  background-position: 0 0;
  text-align: center;
  overflow: hidden;
  height: 800px;
  background-color: #000;
  color: #fff;
  position: relative;
  .context {
    margin-top: 10%;
  }
  h1 {
    font-size: 50px;
    margin-top: 50px !important;
    font-weight: bold;
    span {
      margin-left: 10px;
      font-size: 20px;
    }
  }
  h2{
    margin-top: 20px;
  } 
  p {
    width: 50%;
    margin: 0 auto;
    font-size: 18px;
    text-indent: 2rem;
    margin-top: 20px;
  }
}
</style>
