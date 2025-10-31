<template>
    <div>
        <div style="margin-bottom: 5px;text-align: center;">
            <el-select v-model="bike" placeholder="请选择单车" style="margin-left: 5px">
                <el-option v-for="item in bikeData" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
            </el-select>
            <el-select v-model="userId" placeholder="请选择用户" style="margin-left: 5px" v-if="user.role != 2">
                <el-option v-for="item in userData" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
            </el-select>
            <el-select v-model="bikeorder" placeholder="请选择订单" style="margin-left: 5px">
                <el-option v-for="item in orderData" :key="item.id" :label="item.orderCode" :value="item.id">
                </el-option>
            </el-select>
            <el-button icon="el-icon-search" type="primary" style="margin-left: 5px" @click="loadPost()" circle></el-button>
            <el-button icon="el-icon-refresh" type="success" style="margin-left: 5px" @click="resetParam()"
                circle></el-button>
            <el-button type="danger" slot="reference" :disabled="delBtlStatu" @click="delHandle(null)"
                style="margin-left: 5px" v-if="user.role == 0">批量删除</el-button>
        </div>
        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" border style="width: 100%" stripe
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"> </el-table-column>

            <el-table-column prop="bikeorder" label="订单编号" width="200" :formatter="formatOrder">
            </el-table-column>

            <el-table-column prop="user" label="下单用户" :formatter="formatUser">
            </el-table-column>
            <el-table-column prop="bike" label="使用单车" :formatter="formatBike">
            </el-table-column>
            <el-table-column prop="createTime" label="收益时间" width="200" :formatter="formatDate">
            </el-table-column>
            <el-table-column prop="money" label="收益金额" width="100">
                <template slot-scope="scope">
                    <el-tag type="success">{{ scope.row.money }}元</el-tag> </template>
            </el-table-column>
            
            <el-table-column fixed="right" label="操作" width="150" prop="operate">
                <template slot-scope="scope">
                    

                    <el-button type="danger" @click="delHandle(scope.row.id)" size="small" v-if="user.role == 0">
                        <i class="el-icon-delete"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
            :page-sizes="[5, 10, 50, 100, 1000]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
    </div>
</template>
    
<script>
export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: "TypeManager",
    data() {
        return {
            user: JSON.parse(localStorage.getItem("CurUser")),
            pageSize: 6,
            pageNum: 1,
            total: 0,
            tableData: [],
            userId: "",
            bike: "",
            bikeorder:"",
            orderData:[],
            bikeData: [],
            userData: [],
            
            delBtlStatu: true,
            multipleSelection: [],
        };
    },
    methods: {
        loadBike() {
            this.$axios
                .get(this.$httpUrl + "/bike/list")
                .then((res) => res.data)
                .then((res) => {
                    this.bikeData = res;
                });
        },
        loadOrder() {
            this.$axios
                .get(this.$httpUrl + "/bikeorder/list")
                .then((res) => res.data)
                .then((res) => {
                    this.orderData = res;
                });
        },
        loadUser() {
            this.$axios
                .post(this.$httpUrl + "/user/listPage", {
                    pageSize: 999999,
                    pageNum: 1,
                    param: {
                        role: 2 + "",
                    },
                })
                .then((res) => res.data)
                .then((res) => {
                    console.log(res);
                    if (res.code == 200) {
                        this.userData = res.data;

                    } else {
                        this.$message({
                            message: "数据查询失败",
                            type: "warning",
                        });
                    }
                });
        },
        resetParam() {
            this.userId = "";
            this.bike = ""
            this.status = ""
        },
        formatBike(row) {
            let temp = this.bikeData.find((item) => {
                return item.id == row.bike;
            });
            return temp && temp.name;
        },
        formatUser(row) {
            let temp = this.userData.find((item) => {
                return item.id == row.user;
            });
            return temp && temp.name;
        },
        formatOrder(row) {
            let temp = this.orderData.find((item) => {
                return item.id == row.bikeorder;
            });
            return temp && temp.orderCode;
        },
        formatDate(row, column) {
            let data = row[column.property];
            if (data == null) {
                return null;
            }
            let dt = new Date(data);
            return (
                dt.getFullYear() +
                "-" +
                (dt.getMonth() + 1) +
                "-" +
                dt.getDate() +
                " " +
                dt.getHours() +
                ":" +
                dt.getMinutes() +
                ":" +
                dt.getSeconds()
            );
        },
      
        loadPost() {
            if(this.user.rolw==2)this.userId=this.user.id
            this.$axios
                .post(this.$httpUrl + "/income/listPage", {
                    pageSize: this.pageSize,
                    pageNum: this.pageNum,
                    param: {
                        user: this.userId + "",
                        bike: this.bike + "",
                        status: this.status + "",
                        bikeorder:this.bikeorder+''
                    },
                })
                .then((res) => res.data)
                .then((res) => {
                    console.log(res);
                    if (res.code == 200) {
                        this.tableData = res.data;
                        this.total = res.total;
                    } else {
                        this.$message({
                            message: "数据查询失败",
                            type: "warning",
                        });
                    }
                });
        },

        delHandle(id) {
            console.log(id);
            var ids = [];
            if (id) {
                ids.push(id);
            } else {
                this.multipleSelection.forEach((row) => {
                    ids.push(row.id);
                });
            }
            console.log(ids);
            this.$confirm("将删除订单一切有关数据， 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.$axios
                        .post(this.$httpUrl + "/income/deleteBatch", ids, {
                            headers: {
                                "Content-Type": "application/json;charset=UTF-8",
                            },
                        })
                        .then((res) => {
                            console.log(res);

                            this.$message({
                                showClose: true,
                                message: "恭喜你，操作成功",
                                type: "success",
                            });
                            this.loadPost();
                        })
                        .catch((err) => {
                            console.error(err);
                            this.$message({
                                showClose: true,
                                message: "操作失败，请重试",
                                type: "error",
                            });
                        });
                })
                .catch(() => {
                    this.$message({
                        showClose: true,
                        message: "已取消操作",
                        type: "info",
                    });
                });
        },
        toggleSelection(rows) {
            if (rows) {
                rows.forEach((row) => {
                    this.$refs.multipleTable.toggleRowSelection(row);
                });
            } else {
                this.$refs.multipleTable.clearSelection();
            }
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
            this.delBtlStatu = val.length == 0;
        },

        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.pageNum = 1;
            this.pageSize = val;
            this.loadPost();
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.pageNum = val;
            this.loadPost();
        },
    },
    mounted() {
        this.loadOrder()
        this.loadBike()
        this.loadUser()
        this.loadPost();
    },
};
</script>
    
<style>
.el-pagination {
    text-align: center;
    margin-top: 22px;
}
</style>