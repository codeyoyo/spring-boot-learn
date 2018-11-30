<template>
    <div class="start">
        <h2>项目搭建</h2>
        <row type="flex" justify="start" class="code-row-bg row">
            <i-col span='15'>
                <p class="p-indent">第一种搭建方式：可访问<a href="http://start.spring.io/" target="_bank">http://start.spring.io/</a> 
                选择构建工具Maven Project、Spring Boot版本1.3.2以及一些工程基本信息，可参考下图所示：</p>
                <div class="img-box">
                    <img src="../../assets/spring-download.png" />
                </div>
            </i-col>
        </row>
        <row type="flex" justify="end" class="code-row-bg row">
            <i-col span='15'>
                <p class="p-indent">第二种搭建方式：可通过IDEA进行项目构建，可参考下图所示：</p>
                <div class="img-box">
                    <img src="../../assets/idea.png" />
                </div>
            </i-col>
        </row>
    </div>
</template>

<script>
    export default {
        
    }
</script>

<style scoped lang='less'>
    .start{
        background-color: #292727e6;
        color: #fff;
        padding: 30px 0px;
        h2{
            font-size: 25px;
            margin-bottom: 20px;
        }
        .img-box{
            margin: 10px 20px 0px 20px;
        }
        .row{
            margin-top: 50px;
        }
    }
</style>