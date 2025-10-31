<template>
  <div class="container">

    <div class="header" v-if="!loading">
      <div class="left">
        <el-button type="warning" @click="Index" style="margin-left: 100px;">
          <span :class="activeTab === 'Index' ? 'center-header-size-active' : 'center-header-size'">单车可视化</span>
        </el-button>
        <el-button type="warning" @click="Analyse">
          <span :class="activeTab === 'Analyse' ? 'center-header-size-active' : 'center-header-size'">订单可视化</span>
        </el-button>
        <el-button type="warning" @click="AMap">
          <span :class="activeTab === 'AMap' ? 'center-header-size-active' : 'center-header-size'">地图可视化</span>
        </el-button>
      </div>
      <div class="center">
        <dv-decoration-11 style="width:400px;height:60px;margin-right: 100px;">共享单车可视化大屏</dv-decoration-11>
      </div>
      <div class="right">

        <el-button type="warning" style="margin-right: 100px;" @click="Data">
          <span :class="activeTab === 'Data' ? 'center-header-size-active' : 'center-header-size'">数据中心</span>
        </el-button>

        <el-button type="danger" @click.native="delUser" class="item"><i class="el-icon-delete-solid">
          </i></el-button>
        <el-button type="warning" @click.native="logout" class="item"><i class="el-icon-switch-button">
          </i></el-button>

      </div>

    </div>
    <dv-decoration-5 style="width:100%;height:20px;" v-if="!loading" />
    <div class="sreen" v-if="!loading">
      <router-view></router-view>
    </div>
    <div class="loading-warpper" v-if="loading">
      <dv-loading>加载中...</dv-loading>
    </div>

  </div>
</template>
<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Index',
  data() {
    return {
      loading: true,
      activeTab: 'Index',
    }
  }, methods: {
    // loading效果
    cancelLoading() {
      setTimeout(() => {
        this.loading = false
      }, 1000)
    },
    changeStyle() {
      const buttons = document.querySelectorAll('.el-button span');
      // console.log(buttons)
      // 为每个元素添加点击事件监听器
      buttons.forEach(decoration => {
        decoration.addEventListener('click', function () {
          // 首先移除所有元素的 active 样式，添加 normal 样式
          buttons.forEach(d => {
            d.classList.remove('center-header-size-active');
            d.classList.add('center-header-size');
          });

          // 为当前点击的元素添加 active 样式，移除 normal 样式
          this.classList.remove('center-header-size');
          this.classList.add('center-header-size-active');
        });
      });
    },
    AMap() {
      this.loading = true;
      this.cancelLoading()
      this.activeTab = 'AMap'
      this.$router.push("/MapChart")


    },
    Index() {
      this.loading = true;
      this.cancelLoading()
      this.activeTab = 'Index'
      this.$router.push("/BikeChart")
    },
    Data() {
      this.activeTab = 'Data',
        this.$router.push("/")

    },
    Analyse() {
      this.loading = true;
      this.cancelLoading()
      this.activeTab = 'Analyse'
      this.$router.push("/OrderChart")
    },
  }, created() {
    this.Index()
  }
}
</script>
<style>
.container {
  width: 100%;
  height: 100vh;
  background-image: url(http://localhost:8888/upload/file/bj);
  background-size: 100% 100%;
  position: relative;
  /* 让子元素可以相对于父容器定位 */
}

.loading-wrapper {
  position: absolute;
  /* 绝对定位 */
  top: 50%;
  /* 垂直居中 */
  left: 50%;
  /* 水平居中 */
  transform: translate(-50%, -50%);
  /* 调整位置以真正居中 */
  z-index: 10;
  /* 确保在其他内容之上 */
}

.header {
  display: flex;
  /* 使用Flex布局 */
  align-items: center;
  /* 垂直居中对齐 */
  justify-content: space-between;
  /* 左右对齐，平均分布 */
  height: 60px;
  /* 设置头部高度 */
  padding: 0 20px;
  /* 左右内边距 */
  color: rgb(175, 174, 179);
}

.header .left,
.header .center,
.header .right {
  display: flex;
  /* 让每个部分内部的内容也使用Flex布局 */
  align-items: center;
  /* 垂直居中对齐 */
}

.header .left dv-decoration-11,
.header .right dv-decoration-11 {
  margin-right: 10px;
  /* 调整每个元素之间的间距 */
}

.screen {
  width: 90%;
  margin-left: 100px;
}

.center-header-size {
  padding: 0 10px;
  letter-spacing: 2px;
  font-weight: 700;
  background: linear-gradient(92deg,
      #0072ff 0%,
      #00eaff 48.8525390625%,
      #01aaff 100%);
  background-clip: text;
  -webkit-text-fill-color: transparent;
}

.center-header-size-active {
  padding: 0 10px;
  letter-spacing: 2px;
  font-weight: 700;
  background: linear-gradient(92deg,
      #eb3801 0%,
      #ac0537 48.8525390625%,
      #e30808 100%);
  background-clip: text;
  -webkit-text-fill-color: transparent;
}
</style>