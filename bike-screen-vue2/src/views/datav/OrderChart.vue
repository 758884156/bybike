<template>
    <div>
        <div class="host-body">
            <div class="body-box">
                <div class="left">
                    <div class="left-top">
                        <dv-border-box-13>
                            <HeaderTitle title="单车订单统计" />
                            <div style="width: 100%;height: 160px;" id="container1">

                            </div>
                        </dv-border-box-13>
                    </div>
                    <div class="left-center">
                        <dv-border-box-13>
                            <HeaderTitle title="单车订单趋势" />
                            <div style="width: 100%;height:160px;" id="container2">

                            </div>
                        </dv-border-box-13>
                    </div>
                    <div class="left-bottom">
                        <dv-border-box-13>
                            <HeaderTitle title="订单数量统计" />
                            <div style="width: 100%;height:160px;" id="container3">

                            </div>
                        </dv-border-box-13>
                    </div>
                </div>
                <div class="center">
                    <div style="text-align: center;">

                        <span class="center-header-size">共享单车可视化</span>
                    </div>
                    <div class="center-top">
                        <dv-border-box-13>
                            <HeaderTitle title="单车订单收益" />
                            <div style="width: 100%;height: 370px;text-align: center;">
                                <el-radio-group v-model="time" style="margin-left: 50px;" @change="loadPost">
                                    <el-radio-button v-for="item in timeData" :key="item.id" :label="item.id"
                                        :value="item.id">{{ item.value }}</el-radio-button>
                                </el-radio-group>
                                <div style="width: 100%;height:320px;" id="container4">

                                </div>
                            </div>
                        </dv-border-box-13>
                    </div>
                    <div class="center-bottom">
                        <dv-border-box-13>
                            <HeaderTitle title="单车订单详情" />
                            <div style="width: 100%;height:160px;">
                                <dv-scroll-board style="width: 95%; height: 120px; padding: 0 20px;text-align: center; "
                                    :config="bikeOrderConfig" />
                            </div>
                        </dv-border-box-13>
                    </div>
                </div>
                <div class="right">
                    <div class="right-top">
                        <dv-border-box-13>
                            <HeaderTitle title="单车收益统计" />
                            <div style="width: 100%;height:160px;" id="container6">
                            </div>
                        </dv-border-box-13>
                    </div>
                    <div class="right-center">
                        <dv-border-box-13>
                            <HeaderTitle title="单车收益趋势" />
                            <div style="width: 100%;height:160px;" id="container7"></div>
                        </dv-border-box-13>

                    </div>
                    <div class="right-bottom">
                        <dv-border-box-13>
                            <HeaderTitle title="单车订单转化比" />
                            <div style="width: 100%;height:160px;" id="container8"></div>
                        </dv-border-box-13>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>
  
<script>

import * as echarts from "echarts";
export default {

    // eslint-disable-next-line vue/multi-word-component-names
    name: "Index",
    data() {
        return {
            bikeOrderConfig: {},
            color: ['#fd7f0e', '#04f9fa', '#16bd87', '#1aa3ff', '#ff633c', '#ffc722'],
            time: 1,
            bike: 0,
            timeData: [
                {
                    id: 0,
                    value: "全部",
                },
                {
                    id: 1,
                    value: "年",
                },
                {
                    id: 2,
                    value: "月",
                },
                {
                    id: 3,
                    value: "周",
                },
            ],
            bikeData: [{
                id: 0,
                name: "全部"
            }],
            recordData: [
                {
                    id: 0,
                    value: "订单额",
                },
                {
                    id: 1,
                    value: "订单量",
                },
                {
                    id: 2,
                    value: "收益额",
                },

            ],
        }
    },
    methods: {
        loadOrderIncome() {
            this.$axios
                .post(this.$httpUrl + "/echart/orderIncomeChart", {
                    param: {
                        time: this.time + "",
                    },
                })
                .then((res) => res.data)
                .then((res) => {
                    if (res.code == 200) {
                        this.draw4(res.data)
                    } else {
                        alert("获取数据失败");
                    }
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
                    data: ['订单额', '收益额'],
                    textStyle: {
                        color: 'yellow',  // 设置图例文字颜色
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
                        name: '订单额',
                        type: 'line',
                        label: {
                            show: true,
                            formatter: '{c} 元'  // {c} 表示数据值，显示为数值后跟上单位 元
                        },
                        data: data.map(item => item.value1)
                    },
                    {
                        name: '收益额',
                        type: 'line',
                        label: {
                            show: true,
                            formatter: '{c} 元'  // 添加单位 分钟
                        },
                        data: data.map(item => item.value2)
                    },

                ]
            };

            myChart.setOption(option);
        },
        getBikeOrderIncome() {
            this.$axios
                .get(this.$httpUrl + "/echart/bikeOrderIncomeChart", {
                }).then((res) => res.data)
                .then((res) => {
                    this.draw8(res)
                });
        },
        draw8(data) {

            var chart = document.getElementById("container8");
            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chart)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chart).dispose();
            }
            var myChart = echarts.init(chart);

            var option = {
                grid: {
                    top: '15%',
                    left: '3%',
                    right: '3%',
                    bottom: '20%',
                    containLabel: true,
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        crossStyle: {
                            color: '#999'
                        }
                    }
                },
                legend: {
                    orient: 'vertical',
                    left: 'center',
                    bottom: 'bottom',
                    padding: [10, 0, 0, 0],
                    data: ['单车订单数', '订单收益数'],

                },
                xAxis: [
                    {
                        type: 'category',

                        data: data.map(item => item.name),
                    }
                ],
                yAxis: [
                    {
                        type: 'value',

                        interval: 20,
                        axisLabel: {
                            formatter: '{value} ',
                            textStyle: {
                                //坐标轴颜色
                                color: 'rgba(36, 173, 254, 1)',
                                fontSize: "1rem"
                            }
                        },
                        //坐标轴线样式
                        splitLine: {
                            show: true,
                            lineStyle: {
                                type: 'solid', //solid实线;dashed虚线
                                color: 'rgba(36, 173, 254, 0.2)'
                            }
                        },
                    }, {
                        type: 'value',

                        interval: 20,
                        axisLabel: {
                            formatter: '{value} ',
                            textStyle: {
                                //坐标轴颜色
                                color: 'rgba(36, 173, 254, 1)',
                                fontSize: "1rem"
                            }
                        },
                        //坐标轴线样式
                        splitLine: {
                            show: true,
                            lineStyle: {
                                type: 'solid', //solid实线;dashed虚线
                                color: 'rgba(36, 173, 254, 0.2)'
                            }
                        },
                    },
                ],
                series: [
                    {
                        name: "订单数",
                        data: data.map(item => item.value1),
                        type: 'line',
                        smooth: true, //true曲线; false折线
                        itemStyle: {
                            normal: {
                                color: '#ffc600', //改变折线点的颜色
                                lineStyle: {
                                    color: '#ffc600', //改变折线颜色
                                    type: 'solid'
                                }
                            }
                        },
                        areaStyle: {
                            //折线图颜色半透明
                            color: {
                                type: 'linear',
                                colorStops: [{
                                    offset: 0, color: 'rgba(255,198,0, 0.5)' // 0% 处的颜色
                                }, {
                                    offset: 1, color: 'rgba(255,198,0, 0.1)' // 100% 处的颜色
                                }],

                            }
                        }
                    }, {
                        name: "收益订单数",
                        data: data.map(item => item.value2),
                        type: 'line',
                        smooth: true, //true曲线; false折线
                        itemStyle: {
                            normal: {
                                color: '#ffc600', //改变折线点的颜色
                                lineStyle: {
                                    color: '#ffc600', //改变折线颜色
                                    type: 'solid'
                                }
                            }
                        },
                        areaStyle: {
                            //折线图颜色半透明
                            color: {
                                type: 'linear',
                                colorStops: [{
                                    offset: 0, color: 'rgba(255,198,0, 0.5)' // 0% 处的颜色
                                }, {
                                    offset: 1, color: 'rgba(255,198,0, 0.1)' // 100% 处的颜色
                                }],

                            }
                        }
                    },
                ]
            };

            myChart.setOption(option);
        },
        loadBikeOrderList() {
            this.$axios
                .post(this.$httpUrl + "/echart/bikeOrderList", {
                    param: {
                        time: this.time + "",
                    },
                })
                .then((res) => res.data)
                .then((res) => {
                    this.bikeOrderConfig = {
                        header: ['订单编号', '单车平台', '订单状态', '订单金额', '下单日期'],
                        data: res.map(item => ([
                            item.orderCode,
                            // item.bike,
                            // item.status,
                            // item.money,
                            // item.createTime
                            // this.formatUser(item.user),
                            this.formatBike(item.bike),
                            this.formatStatus(item.status),
                            this.formatMomey(item.money),
                            this.formatDate(item.createTime)
                        ]
                        )),
                        headerBGC: 'rgba(41, 105, 177, 0.3)', // 表头背景色
                        oddRowBGC: 'transparent', // 奇数行背景色
                        evenRowBGC: 'transparent', // 偶数行背景色
                        carousel: "single",
                        columnWidth: [180, 120, 120, 120, 140],
                        align: ['center', 'center', 'center', 'center', 'center'],

                    }

                });
        },
        getBike() {
            this.$axios
                .get(this.$httpUrl + "/bike/list")
                .then((res) => res.data)
                .then((res) => {
                    this.bikeData.push(...res);
                });
        },
        formatBike(bike) {
            let temp = this.bikeData.find((item) => {
                return item.id == bike;
            });
            return temp && "&nbsp;&nbsp;&nbsp;&nbsp;" + temp.name;
        },
        formatDate(data) {

            let dt = new Date(data);
            return ('&nbsp;&nbsp;' +
                dt.getFullYear() +
                "年" +
                (dt.getMonth() + 1) +
                "月" +
                dt.getDate() +
                "日"
            );
        },
        formatMomey(money) {
            if (money < 30) {
                return "<span style='color:grey'>&nbsp;&nbsp;" + money + "元</span>"
            }
            else if (money < 100) {
                return "<span style='color:blue'>&nbsp;&nbsp;" + money + "元</span>"
            }
            else if (money < 1000) {
                return "<span style='color:green'>&nbsp;&nbsp;" + money + "元</span>"
            } else {
                return "<span style='color:red'>&nbsp;&nbsp;" + money + "￥</span>"
            }
        },
        formatStatus(status) {
            if (status == 0) {
                return "<span style='color:blue'>&nbsp;&nbsp;待结算</span>"
            } else if (status == 1) {

                return "<span style='color:orange'>&nbsp;&nbsp;待支付</span>"

            } else {
                return "<span style='color:green'>&nbsp;&nbsp;已完成</span>"
            }
        },
        loadOrderCount() {
            this.$axios
                .post(this.$httpUrl + "/echart/orderChart", {
                    param: {
                        bike: this.bike + "",
                        record: "1",
                        time: this.time + "",
                    },
                })
                .then((res) => res.data)
                .then((res) => {
                    if (res.code == 200) {
                        this.draw3(res.data)

                    } else {
                        alert("获取数据失败");
                    }
                });
        },
        draw3(data) {
            console.log(data)
            var that = this
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
                    },

                    formatter: function (params) {
                        console.log(params)
                        return params.name + '<br/>' + params.value + "单";
                    }
                },
                grid: {
                    top: '6%',
                    left: '8%',
                    right: '8%',
                    bottom: '3%',
                    containLabel: true
                },
                yAxis: {
                    type: 'value',  // 注意这里的类型是 'value'
                },
                // 将原先的 yAxis 改为 xAxis，类型改为 category
                xAxis: {
                    type: 'category',
                    data: data.map(item => item.name)
                },
                series: [
                    {
                        name: '订单数',
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
                    }
                ]
            }
            myChart.setOption(option);

        },
        getRandomColor() {
            // 生成随机的颜色代码
            return this.color[Math.floor(Math.random() * (this.color.length + 1))]
        },
        loadIncomeMoney() {
            this.$axios
                .post(this.$httpUrl + "/echart/orderChart", {
                    param: {
                        bike: this.bike + "",
                        record: "2",
                        time: this.time + "",
                    },
                })
                .then((res) => res.data)
                .then((res) => {
                    if (res.code == 200) {
                        this.draw7(res.data)

                    } else {
                        alert("获取数据失败");
                    }
                });
        },
        draw7(data) {
            var chart = document.getElementById("container7");
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
                        console.log(params)
                        return params.name + '<br/>' + params.value + "元";
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
                    axisLabel: {
                        textStyle: {
                            color: '#D6E1FF',
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#9EA3B4',
                            opacity: 0.3,
                        }
                    },
                },
                yAxis: [
                    {
                        type: 'value',
                        splitNumber: 5,
                        splitLine: {
                            lineStyle: {
                                color: '#9EA3B4',
                                opacity: 0.3,
                            },
                        },
                        axisLabel: {
                            textStyle: {
                                color: '#D6E1FF',
                            },
                        },
                    },
                ],
                series: [
                    {
                        type: 'line',
                        itemStyle: {
                            normal: {
                                color: '#4D62FF',
                                lineStyle: {
                                    color: '#4D62FF',
                                    width: 4,
                                },
                                areaStyle: {
                                    color: new echarts.graphic.RadialGradient(0.3, 0.5, 0.7, [
                                        {
                                            offset: 0,
                                            color: '#df0e0e',
                                        },
                                        {
                                            offset: 1,
                                            color: '#0072ff',
                                        },
                                    ])
                                }
                                ,
                            }
                        }, data: data.map(item => item.value)
                    }
                ]
            }
            myChart.setOption(option);

        },
        loadOrderMoney() {
            this.$axios
                .post(this.$httpUrl + "/echart/orderChart", {
                    param: {
                        bike: this.bike + "",
                        record: "0",
                        time: this.time + "",
                    },
                })
                .then((res) => res.data)
                .then((res) => {
                    if (res.code == 200) {
                        this.draw2(res.data)

                    } else {
                        alert("获取数据失败");
                    }
                });
        },
        draw2(data) {
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
                        console.log(params)
                        return params.name + '<br/>' + params.value + "元";
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
                        splitNumber: 5,
                    },
                ],
                series: [
                    {
                        type: 'line',
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
                            }
                        }, data: data.map(item => item.value)
                    }
                ]
            }
            myChart.setOption(option);

        },
        getBikeOrder() {
            this.$axios
                .get(this.$httpUrl + "/echart/bikeOrder")
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
            var myChart = echarts.init(chart);

            var option = {
                color: that.color,
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b}：{c} 元',
                },
                legend: {
                    bottom: '10%',
                    itemWidth: 10,
                    itemHeight: 10,
                    textStyle: {
                        color: '#fff',
                        fontSize: '12',
                    },
                },
                series: [
                    {
                        name: '单车订单',
                        type: 'pie',
                        center: ['50%', '40%'],
                        radius: ['35%', '50%'],
                        label: {
                            normal: {
                                formatter: params => {
                                    return params.name + ":" + params.value + "元"
                                },

                            },
                        },
                        data: data,

                    },
                ],
            };

            myChart.setOption(option);
        },
        getBikeIncome() {
            this.$axios
                .get(this.$httpUrl + "/echart/bikeIncome")
                .then((res) => res.data)
                .then((res) => {
                    this.draw6(res)
                });
        },
        draw6(data) {
            var chart = document.getElementById("container6");
            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chart)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chart).dispose();
            }
            var myChart = echarts.init(chart);

            var option = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{b}<br/>{c}元 ",

                },
                legend: {
                    bottom: '10%',
                    itemWidth: 10,
                    itemHeight: 10,
                    textStyle: {
                        color: '#fff',
                        fontSize: '12',
                    },
                },
                label: {
                    show: true,
                    position: 'outside',
                    formatter: params => {
                        return params.name + ":" + params.value + "元"
                    },


                },
                series: [{
                    type: 'pie',
                    center: ['45%', '40%'],
                    radius: ['30%', '50%'],
                    data: data,
                }]
            };

            myChart.setOption(option);
        },
        loadPost() {
            this.loadOrderMoney()
            this.loadIncomeMoney()
            this.loadOrderCount()
            this.loadOrderIncome()
            this.loadBikeOrderList()
        },
    },
    mounted() {
        this.getBike()
        this.loadPost()
        this.getBikeOrder()
        this.getBikeIncome()

        this.getBikeOrderIncome()
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
  