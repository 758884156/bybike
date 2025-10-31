<template>
    <div>
        <div style="margin-bottom: 5px;text-align: center;">
            <el-select v-model="bike" placeholder="请选择单车" style="margin-left: 5px">
                <el-option v-for="item in bikeData" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
            </el-select>
            <el-select v-model="province" placeholder="请选择省份" style="margin-left: 5px">
                <el-option v-for="item in provinceData" :key="item.id" :label="item.name" :value="item.name">
                </el-option>
            </el-select>
            <el-date-picker v-model="createTime" type="date" placeholder="选择日期">
            </el-date-picker>
            <el-button icon="el-icon-search" type="primary" style="margin-left: 5px" @click="loadPost()" circle></el-button>
            <el-button icon="el-icon-refresh" type="success" style="margin-left: 5px" @click="resetParam()"
                circle></el-button>

            <el-button type="danger" slot="reference" :disabled="delBtlStatu" @click="delHandle(null)"
                style="margin-left: 5px" v-if="user.role == 0">批量删除</el-button>
        </div>
        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" border style="width: 100%" stripe
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"> </el-table-column>

            <el-table-column prop="bike" label="共享单车" width="200" :formatter="formatBike">
            </el-table-column>

            <el-table-column prop="province" label="回收省份">
            </el-table-column>
            <el-table-column prop="city" label="回收城市">
            </el-table-column>
            <el-table-column prop="address" label="回收地址">
            </el-table-column>
            <el-table-column prop="count" label="回收数量" width="200">
                <template slot-scope="scope">
                    <el-tag type="primary">{{ scope.row.count }}辆</el-tag> </template>
            </el-table-column>
            <el-table-column prop="createTime" label="回收时间" width="200" :formatter="formatDate">
            </el-table-column>

            <el-table-column fixed="right" label="操作" prop="operate">
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
        <el-dialog title="回收表单" :visible.sync="centerDialogVisible" width="800px" center>
            <el-form ref="form" :rules="rules" :model="form" label-width="100px">
                <el-form-item label="共享单车" prop="bike">
                    <el-select v-model="form.bike" placeholder="请选择单车" style="width: 500px;">
                        <el-option v-for="item in bikeData" :key="item.id" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="回收数量" prop="count">
                    <el-input-number v-model="form.count" style="width:500px;" :step="1"></el-input-number>辆

                </el-form-item>
                <el-form-item label="回收时间" prop="createTime">
                    <el-date-picker v-model="form.createTime" style="width: 500px;" value-format="yyyy-MM-dd HH:mm:ss"
                        type="date" placeholder="选择日期"></el-date-picker>
                </el-form-item>
                <el-form-item label="回收地址" prop="address">

                    <el-input @click.native="mapVisiable = true" style="width: 500px;" v-model="form.address"
                        placeholder="请选择回收地址"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog width="75%" title="坐标查询" :visible.sync="mapVisiable" append-to-body>
            <AMap :address="form" :mapVisiable="mapVisiable" @closeMap="closeMap"></AMap>

        </el-dialog>
    </div>
</template>
<script>
export default {
    name: "RecoveryManager",
    data() {
        return {
            user: JSON.parse(localStorage.getItem("CurUser")),
            pageSize: 5,
            pageNum: 1,
            total: 0,
            tableData: [],
            bikeData: [],
            provinceData: [],
            bike: "",
            createTime: "",
            province: "",
            form: {
                id: "",
                bike: "",
                count: "",
                createTime: "",
                province: "",
                city: "",
                lon: "",
                lat: "",
                address: ""
            },
            rules: {

            },
            mapVisiable: false,
            delBtlStatu: true,
            multipleSelection: [],
            centerDialogVisible: false,
        }
    },
    methods: {
        getBike() {
            this.$axios
                .get(this.$httpUrl + "/bike/list")
                .then((res) => res.data)
                .then((res) => {
                    this.bikeData = res;
                });
        },
        getProvince() {
            this.$axios
                .get(this.$httpUrl + "/recovery/provinceList")
                .then((res) => res.data)
                .then((res) => {
                    this.provinceData = res;
                });
        },
        formatBike(row) {
            let temp = this.bikeData.find((item) => {
                return item.id == row.bike;
            });
            return temp && temp.name;
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
        closeMap(data) {
            this.form.address = data.address
            this.form.province = data.province
            this.form.City = data.city
            this.form.lat = data.lat
            this.form.lon = data.lon
            this.mapVisiable = false
        },
        resetParam() {
            this.bike = "";
            this.createTime = ""
            this.province = ""
        },
        resetForm() {
            this.form = {
                id: "",
                bike: "",
                count: "",
                createTime: "",
                province: "",
                city: "",
                lon: "",
                lat: "",
                address: ""
            }
        },
        add() {
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                this.resetForm();
            });
        },
        mod(row) {
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                //赋值到表单
                this.$axios
                    .get(this.$httpUrl + "/recovery/info/" + row.id)
                    .then((res) => {
                        this.form = res.data.data;
                    });
            });
        },
        async save() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    if (this.form.id) {
                        this.doMod();
                    } else this.doSave();
                } else {
                    this.$message({
                        message: "请填写正确数据",
                        type: "error",
                    });
                    return false;
                }
            });
        },
        doSave() {
            this.$axios
                .post(this.$httpUrl + "/recovery/save", this.form)
                .then((res) => res.data)
                .then((res) => {
                    console.log(res);
                    if (res.code == 200) {
                        this.$message({
                            message: "操作成功！",
                            type: "success",
                        });
                        this.centerDialogVisible = false;
                        this.getProvince()
                        this.loadPost();
                        this.resetForm();
                    } else {
                        this.$message({
                            message: res.msg,
                            type: "error",
                        });
                    }
                });
        },
        doMod() {
            this.$axios
                .post(this.$httpUrl + "/recovery/update", this.form)
                .then((res) => res.data)
                .then((res) => {
                    console.log(res);
                    if (res.code == 200) {
                        this.$message({
                            message: "操作成功！",
                            type: "success",
                        });
                        this.centerDialogVisible = false;
                        this.getProvince()
                        this.loadPost();
                        this.resetForm();
                    } else {
                        this.$message({
                            message: res.msg,
                            type: "error",
                        });
                    }
                });
        },
        selectedDay() {
            if (this.createTime) {
                var date = new Date(this.createTime);
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                m = m < 10 ? "0" + m : m;
                var d = date.getDate();
                d = d < 10 ? "0" + d : d;
                this.createTime = y + "-" + m + "-" + d;
            } else {
                this.createTime = "";
            }
        },
        loadPost() {
            this.selectedDay()
            this.$axios
                .post(this.$httpUrl + "/recovery/listPage", {
                    pageSize: this.pageSize,
                    pageNum: this.pageNum,
                    param: {
                        bike: this.bike + "",
                        province: this.province+'',
                        createTime: this.createTime,
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
            this.$confirm("将删除回收一切有关数据， 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.$axios
                        .post(this.$httpUrl + "/recovery/deleteBatch", ids, {
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
    }, created() {
        this.getBike()
        this.getProvince()
        this.loadPost()
    }
}
</script>
<style>

.el-pagination {
    text-align: center;
    margin-top: 22px;
}
</style>