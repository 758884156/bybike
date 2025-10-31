import Vue from "vue";
import VueRouter from "vue-router";
import store from "../store";
Vue.use(VueRouter);
const routes = [
 
  {
    path: "/",
    name: "index",
    component: () => import("../views/Index.vue"),
    children: [
      {
        path: "/Home",
        name: "home",
        meta: {
          title: "首页",
        },
        component: () => import("../views/back/system/SystemHome.vue"),
      }, 
      // { path: "/Map", name: "map", component: () => import("../components/Amap.vue") },
    ],
  },
  {
    path: "/Datav",
    name: "datav",
    component: () => import("../views/datav/Index.vue"),
    children: [
      {
        path: "/BikeChart",
        name: "bikeChart",
        meta: {
          title: "单车可视化",
        },
        component: () => import("../views/datav/BikeChart.vue"),
      },
      {
        path: "/OrderChart",
        name: "orderChart",
        meta: {
          title: "订单可视化",
        },
        component: () => import("../views/datav/OrderChart.vue"),
      },
      {
        path: "/MapChart",
        name: "mapChart",
        meta: {
          title: "地图可视化",
        },
        component: () => import("../views/datav/MapChart.vue"),
      },
    ],
  },
  {
    path: "/Login",
    name: "login",
    component: () => import("../views/Login.vue"),
  },
  {
    path: "/Register",
    name: "register",
    component: () => import("../views/Register.vue"),
  },
];
const router = new VueRouter({
  mode: "history",
  routes,
});
router.beforeEach((to, from, next) => {
  let hasRoute = store.state.hasRoutes;
  const curUser = localStorage.getItem("CurUser");
  const menu = localStorage.getItem("Menu");
  let routes = router.options.routes;
  if (routes[0].children.length == 1) {
    hasRoute = false;
    store.commit("changeRoutesStatus", hasRoute);
  }
  if (!curUser && to.path !== "/Login" && to.path !== "/Register") {
    next({ path: "/Login" });
    console.log("登录");
  } else if (curUser && !hasRoute) {
    addRoutes(JSON.parse(menu));
    store.commit("changeRoutesStatus", true);

    next({ ...to, replace: true });
    console.log("构建路由");
  } else {
    next();
    console.log(to.path);
  }
});
function addRoutes(menuList) {
  let routes = router.options.routes;
  routes.forEach((routeItem) => {
    if (routeItem.path == "/") {
      menuList.forEach((menu) => {
        let childRoute = {
          path: "/" + menu.menuclick,
          name: menu.menuname,
          meta: {
            title: menu.menuname,
          },
          component: () => import("../views/back/" + menu.menucomponent),
        };
        routeItem.children.push(childRoute);
      });
    }
  });
  router.addRoutes(routes);
}

// 修改 router.push 和 router.replace 方法，增加错误处理
const originalPush = router.push;
router.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject);
  return originalPush.call(this, location).catch((err) => err);
};

const originalReplace = router.replace;
router.replace = function replace(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalReplace.call(this, location, onResolve, onReject);
  return originalReplace.call(this, location).catch((err) => err);
};

export default router;
