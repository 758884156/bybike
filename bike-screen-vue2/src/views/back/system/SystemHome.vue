<template>
    <div>
        <div class="home">
            <el-row>
                <el-col :span="8">
                    <el-card shadow="hover" class="box-card centered-card">
                        <div>

                            用户数量:
                            <el-tag type="success">
                                {{ userCount }}
                            </el-tag>
                        </div>
                    </el-card></el-col>
                <el-col :span="8"><el-card shadow="hover" class="box-card centered-card blue">
                        <div>

                            管理员数量:
                            <el-tag type="success">
                                {{ adminCount }}
                            </el-tag>
                        </div>
                    </el-card></el-col>
                <el-col :span="8">
                    <el-card shadow="hover" class="box-card centered-card purple">
                        <div>

                            平台数量:
                            <el-tag type="success">
                                {{ platformCount }}
                            </el-tag>
                        </div>
                    </el-card></el-col>
            </el-row>
            <el-row>
                <el-col :span="8"> <el-card shadow="hover" class="box-card centered-card gold">
                        <div>

                            单车数量:
                            <el-tag disable-transitions>{{ bikeCount }}</el-tag>
                        </div>
                    </el-card></el-col>
                <el-col :span="8">
                    <el-card shadow="hover" class="box-card centered-card red">
                        <div>

                            订单金额:
                            <el-tag type="success">
                                {{ orderCount }}
                            </el-tag>
                        </div>
                    </el-card></el-col>
                <el-col :span="8"> <el-card shadow="hover" class="box-card centered-card green">
                        <div>

                            收益金额:
                            <el-tag type="success">
                                {{ incomeCount }}
                            </el-tag>
                        </div>
                    </el-card></el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-card shadow="hover" class="box-card centered-card blue">
                        <div id="container1" style="height: 320px; width: 500px"></div>
                        <div class="text">平台单车比例统计</div>
                    </el-card></el-col>
                <el-col :span="12">
                    <el-card shadow="hover" class="box-card centered-card pink">
                        <div id="container2" style="height: 320px; width: 500px"></div>
                        <div class="text">订单收益金额统计</div>
                    </el-card></el-col>
            </el-row>
        </div>

    </div>
</template>
<script>
import * as echarts from "echarts"
export default ({
    name: "SystemHome",
    data() {
        return {
            http: this.$httpUrl,
            user: JSON.parse(localStorage.getItem("CurUser")),
            userCount: 0,
            adminCount: 0,
            platformCount: 0,
            bikeCount: 0,
            orderCount: 0,
            incomeCount: 0,
            userId: 0
        }
    },
    methods: {
        getUserCount() {
            this.$axios.get(this.http + "/user/allcount").then((res) => res.data).then((res) => {
                this.userCount = res.data.userCount;
                this.adminCount = res.data.adminCount
            })
        },
        getBikeCount() {
            this.$axios.get(this.http + "/bike/count").then((res) => res.data).then((res) => {
                this.bikeCount = res
            })
        },
        getPlatformCount() {
            this.$axios.get(this.http + "/platform/count").then((res) => res.data).then((res) => {
                this.platformCount = res
            })
        },
        getOrderCount() {
            this.$axios.get(this.http + "/bikeorder/count/" + this.userId).then((res) => res.data).then((res) => {
                this.orderCount = res
            })
        },
        getIncomeCount() {
            this.$axios.get(this.http + "/income/count/" + this.userId).then((res) => res.data).then((res) => {
                this.incomeCount = res
            })
        },
        getBikeType() {
            this.$axios.get(this.http + "/echart/pie1").then((res) => res.data).then((res) => {
                this.draw1(res.data)
            })
        },
        getWeekMoney() {
            this.$axios.get(this.http + "/echart/pie2").then((res) => res.data).then((res) => {
                this.draw2(res.data)
            })
        },
        draw1(data) {

            var chartDom = document.getElementById("container1");
            if (echarts.getInstanceByDom(chartDom))
                echarts.getInstanceByDom(chartDom).dispose()
            var myChart = echarts.init(chartDom)
            var option = {
                title: {
                    text: "平台单车占比数量",
                    subtext: "平台单车比例",
                    left: "center"
                },
                tooltip: {
                    trigger: "item",

                }, legend: {
                    orient: "vertical",
                    left: "left"
                }, series: [
                    {
                        name: "单车数",
                        type: "pie",
                        radius: "50%",
                        data: data,
                        

                    }
                ]
            };
            myChart.setOption(option)
        },
        draw2(data) {
            var chartDom = document.getElementById("container2");
            // 判断是否已经初始化
            if (echarts.getInstanceByDom(chartDom)) {
                // 已初始化，销毁实例
                echarts.getInstanceByDom(chartDom).dispose();
            }
            var myChart = echarts.init(chartDom);
            var option = {
                title: {
                    text: "本周订单收益金额统计",

                    left: "center", // 设置标题水平居中
                },

                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        label: {
                            backgroundColor: '#6a7985'
                        }
                    }
                },
                legend: {
                    data: ['订单金额', '收益金额'],
                    orient: "vertical",
                    left: "right",
                },

                grid: {
                    left: '3%',
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
                        name: '订单金额',
                        type: 'line',
                        areaStyle: {},

                        data: data.map(item => item.value1)
                    },
                    {
                        name: '收益金额',
                        type: 'line',
                        areaStyle: {},

                        data: data.map(item => item.value2)
                    },

                ],

            };
            myChart.setOption(option);

        }
    },
    created() {
        if (this.user.role == 2) {
            this.userId = this.user.id
        }
        this.getUserCount()
        this.getBikeCount()
        this.getIncomeCount()
        this.getOrderCount()
        this.getPlatformCount()
        this.getBikeType()
        this.getWeekMoney()
    }
})
</script>
<style scoped>
.home {
    text-align: center;
    background-color: darkorange;
}

.box-card {
    height: auto;
    min-height: 50px;
    text-align: center;
    border-radius: 25px;
    margin: 10px;
}

.card-row {
    display: flex;
    justify-content: center;
}

.centered-card {
    display: flex;
    justify-content: center;
    align-items: center;
}

.red {
    background-color: rgb(231, 9, 9);
}

.green {
    background-color: rgb(152, 246, 11);
}

.purple {
    background-color: rgb(135, 53, 212);
}

.blue {
    background-color: rgb(10, 138, 207);
}

.orange {
    background-color: chocolate;
}

.grey {
    background-color: grey;
}

.pink {
    background-color: blanchedalmond;
}

.gold {
    background-color: goldenrod;
}
</style>