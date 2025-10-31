
<template>
    <el-menu default-active="/Home" :unique-opened="true" :collapse="isCollapse" class="menu" menu-trigger="click"
        active-text-color="#ffd04b" text-color="#ee7708" router="">
        <div v-for="(item, i) in menu" :key="i">
            <el-menu-item v-if="item.menulevel == 1" :index="'/' + item.menuclick">
                <i :class="item.menuicon"></i>
                <span slot="title">&nbsp;&nbsp;{{ item.menuname }}</span>
            </el-menu-item>
            <el-submenu v-if="item.menulevel==0" :index="'/' + item.menuclick">
            <template slot="title">
                <i :class="item.menuicon"></i>
                <span slot="title">&nbsp;&nbsp;{{ item.menuname }}</span>

            </template>
            <div v-for="(menu, n) in menu" :key="n">
            <el-menu-item v-if="item.menucode == menu.parentmenu" :index="'/' + menu.menuclick">
                <i :class="menu.menuicon"></i>
                <span slot="title">&nbsp;&nbsp;{{ menu.menuname }}</span>
            </el-menu-item>
          
            </div>
            </el-submenu>
        </div>

    </el-menu>
</template>
  
<script>
export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: "Aside",
    props: {
        isCollapse: Boolean
    },

    computed: {
        menu: {
            get() {
                return JSON.parse(localStorage.getItem("Menu"))
            }
        }
    },
    methods: {
        firstClick() {
            this.$router.push("/Home")
        }
    },
    beforeMount() {
        this.firstClick()
    },
};
</script>
<style scoped>
.menu {
    height: 100%;
    background-color: #3e045e;
    color: #deb210;
}
</style>
  