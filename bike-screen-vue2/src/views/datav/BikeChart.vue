<template>
    <div>
        <div class="host-body">
            <div class="body-box">
                <div class="left">
                    <div class="left-top">
                        <dv-border-box-13>
                            <HeaderTitle title="平台资产统计" />
                            <div style="width: 100%;height: 150px;" id="container1">

                            </div>
                        </dv-border-box-13>
                    </div>
                    <div class="left-center">
                        <dv-border-box-13>
                            <HeaderTitle title="单车投放总量" />
                            <div style="width: 100%;height:150px;" id="container2"></div>
                        </dv-border-box-13>
                    </div>
                    <div class="left-bottom">
                        <dv-border-box-13>
                            <HeaderTitle title="单车回收总量" />
                            <div style="width: 100%;height:150px;" id="container3"></div>
                        </dv-border-box-13>
                    </div>
                </div>
                <div class="center">
                    <div style="text-align: center;">
                        <span class="center-header-size">共享单车可视化</span>
                    </div>
                    <div class="center-top">
                        <dv-border-box-13>
                            <HeaderTitle title="单车详情" />
                            <div style="width: 100%;height: 350px;" id="container4"></div>
                        </dv-border-box-13>
                    </div>
                    <div class="center-bottom">
                        <dv-border-box-13>
                            <HeaderTitle title="公告通知"></HeaderTitle>
                            <div style="width: 100%;height:150px;">
                                <dv-scroll-board style="width: 95%;height: 120px;;padding: 0 20px; text-align: center;"
                                    :config="noticeConfig"></dv-scroll-board>
                            </div>
                        </dv-border-box-13>
                    </div>
                </div>
                <div class="right">
                    <div class="right-top">
                        <dv-border-box-13>
                            <HeaderTitle title="单车数量统计" />
                            <div style="width: 100%;height:150px;" id="container6">
                            </div>
                        </dv-border-box-13>
                    </div>
                    <div class="right-center">
                        <dv-border-box-13>
                            <HeaderTitle title="单车用户比例" />
                            <div style="width: 100%;height:150px;" id="container7">
                            </div>
                        </dv-border-box-13>

                    </div>
                    <div class="right-bottom">
                        <dv-border-box-13>
                            <HeaderTitle title="单车平台词云" />
                            <div style="width: 100%;height:150px;" id="container8"></div>
                        </dv-border-box-13>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>
  
<script>
import * as echarts from "echarts";
import 'echarts-wordcloud';
export default {

    // eslint-disable-next-line vue/multi-word-component-names
    name: "Index",
    data() {
        return {
            offsetData: [
                [10, 70],
                [50, 70],
                [90, 70],
                [90, 30],
                [50, 30],
                [10, 30],
            ],
            color: ["#fd9173", "#f36f8a", "#5f71d2", "#42a4eb", "#4ac7f5"],
            noticeConfig: {

            },
            noticeData: []
        }
    },
    methods: {
        getBike() {
            this.$axios
                .get(this.$httpUrl + "/bike/list")
                .then((res) => res.data)
                .then((res) => {
                    this.draw4(res);
                });
        },
        draw4(data) {
            var chart = document.getElementById("container4");

            // 判断是否已经初始化
            var existingChart = echarts.getInstanceByDom(chart);
            if (existingChart) {
                // 已初始化，销毁实例
                existingChart.dispose();
            }

            var myChart = echarts.init(chart);
            var option = {
                tooltip: {
                    trigger: 'item',
                    axisPointer: {
                        type: 'shadow'
                    },

                },
                legend: {
                    data: ['起步价', '起步时长', '计时价'],
                    textStyle: {
                        color: 'red',  // 设置图例文字颜色
                    },
                },
                grid: {
                    left: '6%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    data: data.map(item => item.name)
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '起步价',
                        type: 'line',
                        label: {
                            show: true,
                            formatter: '{c} 元'  // {c} 表示数据值，显示为数值后跟上单位 元
                        },
                        data: data.map(item => item.firstPrice)
                    },
                    {
                        name: '起步时长',
                        type: 'line',
                        label: {
                            show: true,
                            formatter: '{c} 分钟'  // 添加单位 分钟
                        },
                        data: data.map(item => item.firstDistance)
                    },
                    {
                        name: '计时价',
                        type: 'line',
                        label: {
                            show: true,
                            formatter: '{c} 元/分钟'  // 添加单位 元
                        },
                        data: data.map(item => item.price)
                    }
                ]
            };
            myChart.setOption(option);
        },
        getBikeType() {
            this.$axios
                .get(this.$httpUrl + "/echart/pie1")
                .then((res) => res.data)
                .then((res) => {
                    this.draw6(res.data)
                    this.draw8(res.data)
                });
        },
        getBikeUser() {
            this.$axios
                .get(this.$httpUrl + "/echart/bikeUser")
                .then((res) => res.data)
                .then((res) => {
                    this.draw7(res)
                });
        },
        draw7(data) {

            var chart = document.getElementById("container7");
            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chart)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chart).dispose();
            }
            //初始化echarts
            var myChart = echarts.init(chart);

            var option = {
                tooltip: {
                    trigger: "item",
                    formatter: "{a} <br/>{b}: {c} 人",
                },
                color: this.color,
                legend: {
                    //图例组件，颜色和名字
                    left: '6%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true,
                    orient: "vertical",
                    itemGap: 10, //图例每项之间的间隔
                    itemWidth: 8,
                    itemHeight: 8,
                    icon: "rect",
                    data: data.map(item => item.name),
                    textStyle: {
                        fontStyle: "normal",
                        fontFamily: "微软雅黑",
                        fontSize: 12,
                    },
                }, series: [
                    {
                        name: "单车用户",
                        type: "pie",
                        clockwise: false, //饼图的扇区是否是顺时针排布
                        minAngle: 20, //最小的扇区角度（0 ~ 360）
                        center: ["50%", "50%"], //饼图的中心（圆心）坐标
                        radius: [20, 66], //饼图的半径
                        avoidLabelOverlap: true, ////是否启用防止标签重叠
                        itemStyle: {
                            //图形样式
                            normal: {
                                borderColor: "#1e2239",
                                borderWidth: 1.5,
                            },
                        },
                        label: {
                            //标签的位置
                            normal: {
                                show: true,
                                position: "inside", //标签的位置

                                textStyle: {
                                    color: "#fff",
                                },
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontWeight: "bold",
                                },
                            },
                        },
                        data: data,
                    },


                ]
            };
            myChart.setOption(option);

        },
        getNotice() {
            this.$axios
                .get(this.$httpUrl + "/notice/list")
                .then((res) => res.data)
                .then((res) => {
                    this.noticeData = res
                    this.noticeConfig = {
                        header: ['标题', '内容', '日期'],
                        data: this.noticeData.map(item => ([
                            item.title,
                            item.content,
                            this.formatDate(item.createTime)
                        ])),
                        headerBGC: 'rgba(41, 105, 177, 0.3)', // 表头背景色
                        oddRowBGC: 'transparent', // 奇数行背景色
                        evenRowBGC: 'transparent', // 偶数行背景色
                        carousel: "single",
                        columnWidth: [140, 340, 140],
                        align: ['center', 'center', 'center'],

                    }

                });
        },
        formatDate(data) {

            let dt = new Date(data);
            return (
                dt.getFullYear() +
                "年" +
                (dt.getMonth() + 1) +
                "月" +
                dt.getDate() +
                "日"
            );
        },
        getPlatform() {
            this.$axios
                .get(this.$httpUrl + "/echart/platformList")
                .then((res) => res.data)
                .then((res) => {
                    this.draw1(res)

                });
        },
        draw1(data) {
            var chart = document.getElementById("container1");
            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chart)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chart).dispose();
            }
            //初始化echarts
            var myChart = echarts.init(chart);
            var datas = [];
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                datas.push({
                    name: item.name + '\n' + item.property + '万亿元',
                    value: this.offsetData[i],
                    symbolSize: item.property / 100,

                    itemStyle: {
                        normal: {
                            color: new echarts.graphic.RadialGradient(0.3, 0.5, 0.7, [
                            {
                                    offset: 0,
                                    color: '#df0e0e',
                                },
                                {
                                    offset: 1,
                                    color: '#0072ff',
                                },
                            ]),
                            opacity: 0.5,
                            shadowColor: '#2e78eca8',
                            shadowBlur: 10,
                            shadowOffsetX: 1,
                            shadowOffsetY: 1,
                        },
                    },
                });
            }
            var option = {
                grid: {
                    show: false,
                    top: 10,
                    bottom: 10,
                },
                xAxis: {
                    type: 'value',
                    show: false,
                    min: 0,
                    max: 100,
                    nameLocation: 'middle',
                    nameGap: 5,
                },
                yAxis: {
                    min: 0,
                    show: false,
                    max: 100,
                    nameLocation: 'middle',
                    nameGap: 30,
                },

                series: [
                    {
                        type: 'scatter',
                        symbol: 'circle',
                        symbolSize: 120,
                        label: {
                            normal: {
                                show: true,
                                formatter: '{b}',
                                color: '#fff',
                                textStyle: {
                                    fontSize: '12',
                                },
                            },
                        },

                        data: datas,
                    },
                ],
            };
            myChart.setOption(option);

        },

        draw6(data) {
            var chart = document.getElementById("container6");
            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chart)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chart).dispose();
            }
            //初始化echarts
            var myChart = echarts.init(chart);
            var datas = [];
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                datas.push({
                    name: item.name + '\n' + item.value + '辆',
                    value: this.offsetData[i],
                    symbolSize: 80,

                    itemStyle: {
                        normal: {
                            color: new echarts.graphic.RadialGradient(0.3, 0.5, 0.7, [
                                {
                                    offset: 0,
                                    color: '#0072ff',
                                },
                                {
                                    offset: 1,
                                    color: '#df0e0e',
                                },
                            ]),
                            opacity: 0.5,
                            shadowColor: '#2e78ec',
                            shadowBlur: 10,
                            shadowOffsetX: 1,
                            shadowOffsetY: 1,
                        },
                    },
                });
            }
            var option = {
                grid: {
                    show: false,
                    top: 20,
                    bottom: 1,
                },
                xAxis: {
                    type: 'value',
                    show: false,
                    nameLocation: 'middle',
                    nameGap: 5,
                },
                yAxis: {
                    show: false,
                    nameLocation: 'middle',
                    nameGap: 30,
                },

                series: [
                    {
                        type: 'scatter',
                        symbol: 'circle',
                        symbolSize: 100,
                        label: {
                            normal: {
                                show: true,
                                formatter: '{b}',
                                color: '#fff',
                                textStyle: {
                                    fontSize: '12',
                                },
                            },
                        },

                        data: datas,
                    },
                ],
            };
            myChart.setOption(option);

        },
        draw8(data) {
            var that = this;
            var chart = document.getElementById("container8");
            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chart)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chart).dispose();
            }
            //初始化echarts
            var myChart = echarts.init(chart);

            var option = {
                tooltip: {
                    show: true
                },
                series: [
                    {
                        name: '单车平台',
                        type: 'wordCloud',
                        size: ['9%', '99%'],
                        sizeRange: [14, 50],
                        textRotation: [0, 45, 90, -45],
                        rotationRange: [-45, 90],
                        shape: 'circle',
                        textPadding: 0,
                        width: '80%',
                        // 画布高
                        height: '80%',
                        autoSize: {
                            enable: true,
                            minSize: 6,
                        },
                        textStyle: {
                            normal: {
                                color: that.getRandomColor()
                            },
                            emphasis: {
                                shadowBlur: 2,
                                shadowColor: '#333'
                            }
                        },
                        data: data
                    }
                ]
            };
            myChart.setOption(option);

        },
        getBikeInvert() {
            this.$axios
                .get(this.$httpUrl + "/echart/bikeInvert/" + "全部")
                .then((res) => res.data)
                .then((res) => {
                    this.draw2(res)
                });
        },
        draw2(data) {
            console.log(data)
            var that = this; // 保存当前 Vue 实例的引用
            var chart = document.getElementById("container2");
            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chart)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chart).dispose();
            }
            var myChart = echarts.init(chart);
            var option = {
                tooltip: {
                    trigger: 'item',
                    axisPointer: {
                        type: 'shadow'
                    },
                    formatter: function (params) {
                        return params.name + '<br/>' + params.value + '辆';
                    }
                },
                grid: {
                    top: "3%",
                    left: '6%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                legend: {
                    //图例组件，颜色和名字

                    containLabel: true,
                    orient: "vertical",
                    itemGap: 10, //图例每项之间的间隔
                    itemWidth: 8,
                    itemHeight: 8,
                    icon: "rect",
                    data: data.map(item => item.name),
                    textStyle: {
                        fontStyle: "normal",
                        fontFamily: "微软雅黑",
                        fontSize: 12,
                    },
                },
                xAxis: {
                    type: 'value',
                },
                yAxis: {
                    type: 'category',
                    data: data.map(item => item.name)
                },
                series: [{
                    name: "车辆数",
                    type: 'bar',
                    data: data.map(item => {
                        return {
                            value: item.value,
                            itemStyle: {
                                color: that.getRandomColor()
                            }
                        }
                    })
                }]
            };
            myChart.setOption(option);
        },
        getRandomColor() {
            // 生成随机的颜色代码
            return this.color[Math.floor(Math.random() * (this.color.length + 1))]
        },
        getBikeRecovery() {
            this.$axios
                .get(this.$httpUrl + "/echart/bikeRecovery/" + "全部")
                .then((res) => res.data)
                .then((res) => {
                    this.draw3(res)
                });
        },
        draw3(data) {
            var that = this; // 保存当前 Vue 实例的引用
            var chart = document.getElementById("container3");

            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chart)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chart).dispose();
            }
            var myChart = echarts.init(chart);
            var option = {
                tooltip: {
                    trigger: 'item',
                    axisPointer: {
                        type: 'shadow'
                    }, formatter: function (params) {
                        return params.name + '<br/>' + params.value + "辆";
                    }
                },
                grid: {
                    top: '3%',
                    left: '6%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                // 将原先的 xAxis 改为 yAxis，类型改为 category
                yAxis: {
                    type: 'value',  // 注意这里的类型是 'value'
                },
                // 将原先的 yAxis 改为 xAxis，类型改为 category
                xAxis: {
                    type: 'category',
                    data: data.map(item => item.name)
                },
                series: [{
                    name: '辆',
                    type: 'bar',  // 保持柱状图类型不变
                    // 数据项的结构要调整，x 轴和 y 轴对调
                    data: data.map(item => {
                        return {
                            name: item.name,
                            value: item.value,
                            itemStyle: {
                                color: that.getRandomColor()
                            }
                        };
                    })
                }],
            };
            myChart.setOption(option);
        },
    },
    mounted() {
        this.getPlatform()
        this.getBikeType()
        this.getBikeInvert()
        this.getBikeRecovery()
        this.getNotice()
        this.getBikeUser()
        this.getBike()
    },
}
</script>
  
<style scoped>
.left-bottom,
.left-center,
.center-bottom,
.right-center,
.right-bottom {
    margin-top: 20px;
}


.host-body .body-box {
    margin-top: 25px;
    display: flex;
}

.host-body .body-box .left {
    height: 33%;
    flex: 3;
}

.host-body .body-box .center {
    flex: 5;
    /* background-color: #fff; */
}
.body{
    background-color: #0072ff,#df0e0e;
}
.host-body .body-box .right {
    height: 33%;
    flex: 3;

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
</style>