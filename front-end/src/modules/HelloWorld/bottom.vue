<template>
    <div class="bottom">
        <p>PS：你的star是我学习的动力</p>
        <p>github地址：<a href="https://github.com/codeyoyo/spring-boot-learn" target="_bank">
        https://github.com/codeyoyo/spring-boot-learn</a></p>
        <p>也可以选择以下两种方式支持我</p>
        <row type="flex" justify="center" :gutter="20" class="row">
            <i-col span='3'>
                <div>
                    <img src="../../assets/weichat.png" />
                </div>
                <p>微信支持</p>
            </i-col>
            <i-col span='3'>
                <div>
                    <img src="../../assets/zfb.png" />
                </div>
                <p>支付宝支持</p>
            </i-col>
        </row>
    </div>
</template>

<script>
    export default {
        
    }
</script>

<style scoped lang='less'>
    .bottom{
        background-color: #000;
        padding-top: 30px;
        line-height: 30px;
        padding-bottom: 30px;
        p,a{
            color: #fff;
        }
        .row{
            margin-top: 20px;
        }
    }
</style>