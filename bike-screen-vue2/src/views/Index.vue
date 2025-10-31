<template>
  <div>
    <el-container>
      <el-aside :width="asideWidth" class="aside">
        <Aside :isCollapse="isCollapse"></Aside>
      </el-aside>
      <el-container class="container">
        <el-header
          style="text-align: right;font-size: 12px;width: 100%;border-bottom: blue;line-height: 60px;background-color: dodgerblue;color: black;">
          <Header @doCollapse="doCollapse" :icon="icon"></Header>
        </el-header>
        <el-main>

          <router-view></router-view>
        </el-main>
        <el-footer class="footer">
          Copyright @2024 钮扣学说.All rights reserved.
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import Aside from "../components/Aside.vue"
import Header from "../components/Header.vue"
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Index",
  components: { Aside, Header },
  data() {
    return {

      isCollapse: false,
      asideWidth: "220px",
      icon: "el-icon-s-fold",
    }
  },
  methods: {
    doCollapse() {
      this.isCollapse = !this.isCollapse;
      if (!this.isCollapse) {
        // 展开
        this.asideWidth = "220px";
        this.icon = "el-icon-s-fold";
      } else {
        this.asideWidth = "64px";
        this.icon = "el-icon-s-unfold";
      }
    },
  },

}
</script>
<style>
body {
  margin: 0;
  background-image: url(http://localhost:8888/upload/file/bj);
  background-size: 100% 100%;
}

.aside {
  min-height: 98vh;
}

.container {
  min-height: 100vh;
}

.footer {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: cornflowerblue;
  font-size: 20px;
  line-height: 60px;
}
</style>