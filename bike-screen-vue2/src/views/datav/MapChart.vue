<template>
    <div>
        <div class="host-body">
            <div class="body-box">
                <div class="left">
                    <div class="left-top">
                        <dv-border-box-13>
                            <HeaderTitle title="投放城市词云" />
                            <div style="width: 100%;height: 270px;" id="container1">
                            </div>
                        </dv-border-box-13>
                    </div>

                    <div class="left-bottom">
                        <dv-border-box-13>
                            <HeaderTitle title="单车投放数量" />
                            <div style="width: 100%;height:270px;" id="container2"></div>
                        </dv-border-box-13>
                    </div>
                </div>
                <div class="center">
                    <div style="text-align: center;">

                        <span class="center-header-size">共享单车可视化</span>
                    </div>
                    <div class="center-top">
                        <dv-border-box-13>
                            <HeaderTitle title="单车数量地图分布" />
                            <div style="width: 100%;height:600px;" id="container3"></div>
                        </dv-border-box-13>
                    </div>

                </div>
                <div class="right">
                    <div class="right-top">
                        <dv-border-box-13>
                            <HeaderTitle title="单车回收占比" />
                            <div style="width: 100%;height:270px;" id="container4">

                            </div>
                        </dv-border-box-13>
                    </div>

                    <div class="right-bottom">
                        <dv-border-box-13>
                            <HeaderTitle title="单车回收数量" />
                            <div style="width: 100%;height:270px;" id="container5"></div>
                        </dv-border-box-13>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>
  
<script>
import china from '../../assets/china.json'
import * as echarts from "echarts";
import 'echarts-wordcloud';
export default {

    // eslint-disable-next-line vue/multi-word-component-names
    name: "Index",
    data() {
        return {
            province: "全部",
            color: ['#fd7f0e', '#04f9fa', '#16bd87', '#1aa3ff', '#ff633c', '#ffc722'],
        }
    },
    methods: {
        loadMap() {
            this.$axios
                .post(this.$httpUrl + "/echart/mapChart")
                .then((res) => res.data)
                .then((res) => {
                    this.draw3(res.data)
                });
        },
        draw3(data) {
            console.log(data)
            var chart = document.getElementById("container3");
            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chart)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chart).dispose();
            }

            echarts.registerMap('china', china)
            var myChart = echarts.init(chart);
            var option = {
                tooltip: {
                    show: true,
                    formatter: function (params) {
                        if (isNaN(params.value)) {
                            return params.name + '暂无投放数据';
                        }
                        else
                            return params.name + '单车数量：' + params.value + '辆';
                    },
                },
                visualMap: {
                    type: 'piecewise',
                    pieces: [{
                        max: 10000,
                        label: '稀疏',
                        color: '#2c9a42'
                    },
                    {
                        min: 10000,
                        max: 1000000,
                        label: '正常',
                        color: '#d08a00'
                    },
                    {
                        min: 1000000,
                        label: '密集',
                        color: '#c23c33'
                    },
                    ],
                    color: '#fff',
                    textStyle: {
                        color: '#fff',
                    },
                    visibility: 'off'
                },
                geo: {
                    map: 'china',
                    aspectScale: 0.75,
                    layoutCenter: ["50%", "51.5%"], //地图位置
                    layoutSize: '118%',
                    roam: true,
                    itemStyle: {
                        normal: {
                            borderColor: 'rgba(147, 235, 248, 1)',
                            borderWidth: 0.5,
                            color: {
                                type: 'linear-gradient',
                                x: 0,
                                y: 1500,
                                x2: 2500,
                                y2: 0,
                                colorStops: [{
                                    offset: 0,
                                    color: '#009DA1' // 0% 处的颜色
                                }, {
                                    offset: 1,
                                    color: '#005B9E' // 50% 处的颜色
                                }],
                                global: true // 缺省为 false
                            },
                            opacity: 0.5,
                        },
                        emphasis: {
                            areaColor: '#2a333d'
                        }
                    },

                    z: 2
                },
                series: [{
                    type: 'map',
                    map: 'china',
                    tooltip: {
                        show: true
                    },
                    label: {
                        show: true,
                        color: '#FFFFFF',
                        fontSize: 16
                    },
                    aspectScale: 0.75,
                    layoutCenter: ["50%", "50%"], //地图位置
                    layoutSize: '118%',
                    roam: false,
                    itemStyle: {
                        normal: {
                            borderColor: 'rgba(147, 235, 248, 0.6)',
                            borderWidth: 0.8,
                            areaColor: {
                                type: 'linear-gradient',
                                x: 0,
                                y: 1200,
                                x2: 1000,
                                y2: 0,
                                colorStops: [{
                                    offset: 0,
                                    color: '#009DA1' // 0% 处的颜色
                                }, {
                                    offset: 1,
                                    color: '#005B9E' // 50% 处的颜色
                                }],
                                global: true // 缺省为 false
                            },
                        },
                        emphasis: {
                            areaColor: 'rgba(147, 235, 248, 50)'    //鼠标滑过高亮
                        }
                    },
                    data: data,
                },

                ]
            };
            myChart.setOption(option)
            myChart.on('click', (params) => {
                this.province = params.name
                this.loadPost()
            });
        },
        loadPost() {
            this.getBikeInvert()
            this.getBikeRecovery()
        },

        getBikeRecovery() {
            this.$axios
                .get(this.$httpUrl + "/echart/bikeRecovery/" + this.province)
                .then((res) => res.data)
                .then((res) => {
                    this.draw4(res)
                    this.draw5(res)
                });
        }, draw5(data) {

            var chart = document.getElementById("container5");
            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chart)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chart).dispose();
            }

            var myChart = echarts.init(chart);
            var option = {
                tooltip: {
                    trigger: 'item',

                    formatter: function (params) {
                        return params.name + '<br/>' + params.value + "辆";
                    }
                },
                grid: {
                    top: '6%',
                    left: '8%',
                    right: '8%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    data: data.map(item => item.name),

                },
                yAxis: [
                    {
                        type: 'value',

                    },
                ],
                series: [
                    {
                        type: 'line',
                        symbol: 'none',
                        itemStyle: {
                            normal: {
                                color: '#4D62FF',
                                lineStyle: {
                                    color: '#4D62FF',
                                    width: 2,
                                },
                                areaStyle: {
                                    //区域填充样式
                                    color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                                        {
                                            offset: 0,
                                            color: 'rgba(77, 98, 255, 0.1)',
                                        },
                                        {
                                            offset: 1,
                                            color: 'rgba(77, 98, 255, 0.5)',
                                        },
                                    ]),
                                },
                            },
                        },

                        data: data.map(item => item.value),
                    },

                ],

            };

            myChart.setOption(option);
        },
        draw4(data) {
            var chart = document.getElementById("container4");
            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chart)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chart).dispose();
            }

            var myChart = echarts.init(chart);
            var option = {
                legend: {
                    orient: 'vertical',
                    icon: 'circle', //图例形状
                    padding: 0,
                    bottom: 'center',
                    right: 40,
                    itemWidth: 14, //小圆点宽度
                    itemHeight: 14, // 小圆点高度
                    itemGap: 21, // 图例每项之间的间隔。[ default: 10 ]横向布局时为水平间隔，纵向布局时为纵向间隔。
                    textStyle: {
                        fontSize: 14,
                        color: '#ffffff',
                    },
                },
                tooltip: {
                    show: true,
                    formatter: '{b}:{d}%',
                },
                series: [
                    {
                        name: '',
                        type: 'pie',
                        center: ['50%', '50%'], //圆心的位置
                        top: '2%', //单单指的饼图距离上面的距离，top越大饼图越小
                        left: '0%', //单单指的饼图距离左面的距离，会改变饼图的大小
                        radius: ['0%', '70%'], //环形图的本质就在这里 [内半径!=0，外半径] 外半径越大，圆越大
                        avoidLabelOverlap: false, //做同心圆用到
                        clockwise: false, //顺时针排列
                        startAngle: 160, //起始角度 影响不大
                        roseType: "area", //area|radius
                        label: {
                            show: true, //false不显示饼图上的标签
                            position: 'outside', //inside（在饼图上显示）,outside(默认就会出现引导线) center
                            formatter: '{b}:{c}',
                        },
                        //只有设置了label:show=ture;position=outside 设置labelLine才会有效
                        labelLine: {
                            show: true, //显示引导线
                            length: 30, //连接饼图第1段线条的长度 length length2 不写自适应
                            length2: 10, //链接饼图第2段线条长度
                            smooth: true, //是否光滑连接线
                        },
                        itemStyle: {
                            //每个扇形的设置
                            borderColor: 'rgba(0,0,0,.1)', //扇形边框颜色
                            borderWidth: 0, //扇形边框大小 要先给borderColor颜色 设置borderWidth才会有效果

                        },
                        data: data.sort((a, b) => b.value - a.value), //数组从大到小排序
                        emphasis: {
                            scale: true,
                            scaleSize: 10,

                            //启用鼠标放上去放大效果，这个挺好的
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)',
                            },
                        },
                    },
                ]
            }

            myChart.setOption(option);
        },
        getBikeInvert() {
            this.$axios
                .get(this.$httpUrl + "/echart/bikeInvert/" + this.province)
                .then((res) => res.data)
                .then((res) => {
                    this.draw2(res)
                });
        },
        draw2(data) {
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
                    name: '投放数量',
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
            }
            myChart.setOption(option);

        },
        getRandomColor() {
            // 生成随机的颜色代码
            return this.color[Math.floor(Math.random() * (this.color.length + 1))]
        },
        getBikeCity() {
            this.$axios
                .get(this.$httpUrl + "/echart/bikeCity")
                .then((res) => res.data)
                .then((res) => {
                    this.draw1(res)
                });
        },
        draw1(data) {
            var that = this; // 保存当前 Vue 实例的引用
            var chart = document.getElementById("container1");
            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chart)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chart).dispose();
            }

            var mychart = echarts.init(chart);
            var option = {
                tooltip: {
                    show: true
                },

                series: [
                    {
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
                                color: that.getRandomColor(),
                            },
                            emphasis: {
                                shadowBlur: 2,
                                shadowColor: '#333',
                            },
                        },
                        data: data,
                    },]
            };

            mychart.setOption(option);
        },

    },
    mounted() {
        this.loadPost()
        this.loadMap()
        this.getBikeCity()
    },


}
</script>
  
<style scoped>
.left-bottom,
.center-bottom,
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
  