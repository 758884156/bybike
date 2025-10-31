<template>
    <div>
        <div style="margin-bottom: 5px;text-align: center;">
            <el-input v-model="title" placeholder="请输入公告标题" suffix-icon="el-icon-search" style="width: 200px"
                @keyup.enter.native="loadPost"></el-input>
            <el-date-picker v-model="createTime" type="date" placeholder="选择日期">
            </el-date-picker>


            <el-button type="primary" style="margin-left: 5px" @click="loadPost">
                <i class="el-icon-search"></i></el-button>
            <el-button type="success" @click="resetParam">
                <i class="el-icon-refresh"></i></el-button>
            <el-button type="primary" style="margin-left: 5px" @click="add">
                <i class="el-icon-plus">发布公告</i></el-button>
            <el-popconfirm title="这是确定批量删除吗？" @confirm="delHandle(null)" v-if="user.role == 0">
                <el-button type="danger" slot="reference" :disabled="delBtlStatu">批量删除</el-button>
            </el-popconfirm>
        </div>
        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" border stripe
            @selection-change="handleSelectionChange" :header-cell-style="{ background: '#f2f5fc', color: '#555555' }">
            <el-table-column type="selection" width="55"> </el-table-column>

            <el-table-column prop="title" label="公告标题" width="100">
            </el-table-column>
            <el-table-column prop="content" label="通知内容" width="500">
            </el-table-column>
            <el-table-column prop="createTime" label="发布时间" width="200" :formatter="formatDate">
            </el-table-column>
            <el-table-column prop="operate" label="操作" fixed="right">
                <template slot-scope="scope">
                    <el-button type="success" @click="mod(scope.row)" size="small">更新公告</el-button>

                    <el-button type="danger" @click="delHandle(scope.row.id)" size="small" v-if="user.role == 0">
                        <i class="el-icon-delete"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
            :page-sizes="[5, 10, 20, 30]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>

        <el-dialog title="公告通知" :visible.sync="centerDialogVisible" width="600px" center>
            <el-form ref="form" :rules="rules" :model="form" label-width="150px">
                <el-form-item label="公告标题" prop="title">

                    <el-input v-model="form.title" style="width: 400px;"></el-input>

                </el-form-item>
                <el-form-item label="通知内容" prop="content">

                    <el-input type="textarea" :rows="4" style="width: 400px;" maxlength="252" show-word-limit
                        v-model="form.content">
                    </el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>
<script>

export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: "Notice",
    data() {
        return {
            //用户信息
            user: JSON.parse(localStorage.getItem("CurUser")),
            //用户权限信息

            tableData: [],

            pageSize: 10,
            pageNum: 1,
            total: 0,
            title: "",

            centerDialogVisible: false,
createTime:null,
            multipleSelection: [],
            delBtlStatu: true,
            multipleSelect: [],
            form: {
                id: "",
                title: "",
                content: "",
            },
            rules: {

                title: [
                    { required: true, message: "请输入公告通知标题", trigger: "blur" },
                    { min: 1, max: 20, message: "20字以内", trigger: "blur" },
                ],
                content: [
                    { required: true, message: "请输入公告通知内容", trigger: "blur" },
                    { min: 10, max: 252, message: "长度为10到252字", trigger: "blur" },
                ],
            },
        };
    },

    methods: {

        resetForm() {
            this.form = {
                id: "",
                title: "",
                content: "",
            }
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

        mod(row) {
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                //赋值到表单
                this.$axios
                    .get(this.$httpUrl + "/notice/info/" + row.id)
                    .then((res) => {
                        this.form = res.data.data;
                    });
            });
        },
        add() {
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                this.resetForm();
            });
        },
        doSave() {
            this.$axios
                .post(this.$httpUrl + "/notice/save", this.form)
                .then((res) => res.data)
                .then((res) => {
                    if (res.code == 200) {
                        this.$message({
                            message: "操作成功！",
                            type: "success",
                        });
                        this.centerDialogVisible = false;
                        this.loadPost();
                        this.resetForm();
                    } else {
                        this.$message({
                            message: "操作失败！",
                            type: "error",
                        });
                    }
                });
        },

        doMod() {
            this.$axios
                .post(this.$httpUrl + "/notice/update", this.form)
                .then((res) => res.data)
                .then((res) => {
                    if (res.code == 200) {
                        this.$message({
                            message: "操作成功！",
                            type: "success",
                        });
                        this.centerDialogVisible = false;
                        this.loadPost();
                        this.resetForm();
                    } else {
                        this.$message({
                            message: "操作失败！",
                            type: "error",
                        });
                    }
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
        handleSizeChange(val) {
            this.pageNum = 1;
            this.pageSize = val;
            this.loadPost();
        },
        handleCurrentChange(val) {
            this.pageNum = val;
            this.loadPost();
        },

        resetParam() {
            this.title = "";
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
            this.$confirm("将删除公告通知一切有关数据， 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.$axios
                        .post(this.$httpUrl + "/notice/deleteBatch", ids, {
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
            this.selectedDay();

            this.$axios
                .post(this.$httpUrl + "/notice/listPage", {
                    pageSize: this.pageSize,
                    pageNum: this.pageNum,
                    param: {
                        title: this.title,
                        createTime:this.createTime
                    },
                })
                .then((res) => res.data)
                .then((res) => {
                    if (res.code == 200) {
                        this.tableData = res.data;
                        this.total = res.total;
                    } else {
                        alert("获取数据失败");
                    }
                });
        },
    },
    beforeMount() {
        this.loadPost();
    },
};
</script>
  
<style scoped></style>