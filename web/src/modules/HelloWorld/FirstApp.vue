<template>
    <div class="first">
        <h2>编写HelloWorld</h2>
        <tabs>
            <tab-pane label="项目结构" icon="md-code">
                <div class="tab-item">
                    <h3>项目结构</h3>
                    <div class="img-box">
                        <img src="../../assets/helloworld.png" />
                    </div>
                </div>
            </tab-pane>
            <tab-pane label="引入Web模块" icon="md-code">
                <div class="tab-item">
                  <h3>引入Web模块</h3>
                  <div class="context-box">
                    <p>当前的<span class="code">pom.xml</span>内容如下，仅引入了两个模块：</p>
                    <ul>
                      <li>
                        <span class="code">spring-boot-starter</span>：核心模块，包括自动配置支持、日志和YAML
                      </li>
                      <li>
                       <span class="code">spring-boot-starter-test</span>：测试模块，包括JUnit、Hamcrest、Mockito
                      </li>
                    </ul>
                    <pom v-highlight />     
                      <p>
                        引入Web模块，需添加<span class="code">spring-boot-starter-web</span>模块：
                      </p>
                      <web v-highlight />
                  </div>
                </div>
            </tab-pane>
            <tab-pane label="编写HelloWorld服务" icon="md-code">
              <div class="tab-item">
                <h3>编写HelloWorld服务</h3>
                <div class="context-box">
                  <ul>
                    <li>
                      创建
                      <span class="code">package</span>
                      命名为
                      <span class="code">com.example.lesson1</span>
                      （根据实际情况修改）
                    </li>
                    <li>
                      创建
                      <span class="code">HelloController</span>类，内容如下:
                    </li>
                  </ul>
                  <hello-code v-highlight />
                  <ul>
                    <li>
                      启动主程序，打开浏览器访问
                      <span class="code">http://localhost:8080/hello</span>，可以看到页面输出
                      <span class="code">
                        Hello World
                      </span>
                    </li>
                  </ul>
                </div>
              </div>
            </tab-pane>
            <tab-pane label="编写单元测试用例" icon="md-code">
              <div class="tab-item">
                  <h3>编写单元测试用例</h3>
                  <div class="context-box">
                    <p>
                      打开的<span class="code">src/test/</span>下的测试入口<span class="code">Chapter1ApplicationTests</span>类。下面编写一个简单的单元测试来模拟http请求，具体如下：
                    </p>
                    <test v-highlight />
                    <p>
                      使用<span class="code">MockServletContext</span>来构建一个空的<span class="code">WebApplicationContext</span>，这样我们创建的<span class="code">HelloController</span>就可以在<span class="code">@Before</span>函数中创建并传递到<span class="code">MockMvcBuilders.standaloneSetup（）</span>函数中。
                    </p>
                    <ul>
                      <li>
                        注意引入下面内容，让<span class="code">status</span>、
                        <span class="code">content</span>、<span>equalTo</span>函数可用
                      </li>
                    </ul>
                    <import-dom v-highlight />
                    <p>
                      至此已完成目标，通过Maven构建了一个空白Spring Boot项目，再通过引入web模块实现了一个简单的请求处理。
                    </p>
                  </div>
              </div>
            </tab-pane>
        </tabs>
    </div>
</template>

<script>
import pom from "./pom.md";
import web from "./web.md";
import helloCode from "./helloCode.md";
import test from "./test.md";
import importDom from "./import.md";
export default {
  data() {
    return {};
  },
  components: {
    pom,
    web,
    helloCode,
    test,
    importDom
  },
  mounted() {}
};
</script>

<style scoped lang='less'>
.first {
  background-color: #fff;
  color: #000;
  padding: 30px 0px;
  h2 {
    font-size: 25px;
    margin-bottom: 20px;
  }
  .tab-item {
    margin: 0 auto;
    margin-top: 30px;
    width: 80%;
    h3 {
      font-size: 20px;
    }
    .img-box {
      margin-top: 20px;
    }
  }
}
</style>