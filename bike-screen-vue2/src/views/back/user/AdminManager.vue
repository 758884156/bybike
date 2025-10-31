<template>
    <div>
        <div style="margin-bottom: 5px;text-align: center;">
            <el-input placeholder="请输入管理员名字" prefix-icon="el-icon-search" v-model="name" style="width: 200px;"
                @click.enter.native="loadPost()">
            </el-input>
            <el-input placeholder="请输入管理员手机号" prefix-icon="el-icon-search" v-model="telephone" style="width: 200px;"
                @click.enter.native="loadPost()">
            </el-input>
            <el-select v-model="sex" placeholder="请选择性别" style="width: 200px;">
                <el-option v-for="item in sexData" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
            </el-select>
            <el-select v-model="status" placeholder="请选择管理员状态" style="width: 200px;">
                <el-option v-for="item in statusData" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
            </el-select>
            <el-button icon="el-icon-search" type="primary" style="margin-left: 5px" @click="loadPost()" circle></el-button>
            <el-button icon="el-icon-refresh" type="success" style="margin-left: 5px" @click="resetParam()"
                circle></el-button>
            <el-button type="primary" style="margin-left: 5px" @click="add">新增</el-button>
            <el-button type="danger" :disabled="delBtlStatu" @click="delHandle(null)" style="margin-left: 5px"
                v-if="user.role == 0">批量删除</el-button>
        </div>
        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" border style="width: 100%;" stripe
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="avatar" label="管理员头像" width="100">
                <template slot-scope="scope">
                    <el-avatar size="small" :src="http + '/upload/avatar/' + scope.row.avatar"></el-avatar>
                 
                </template>
            </el-table-column>
            <el-table-column prop="name" label="管理员昵称" width="120"></el-table-column>
            <el-table-column prop="sex" label="管理员性别" width="100">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.sex == 1">男</el-tag>
                    <el-tag type="danger" v-else>女</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="telephone" label="电话号码" width="150">
            </el-table-column>
            <el-table-column prop="age" label="管理员年龄" width="100">
            </el-table-column>
            <el-table-column prop="status" label="管理员状态" width="100">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.status == 1">正常</el-tag>
                    <el-tag type="danger" v-else>注销</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="注册时间" width="200" :formatter="formatDate"></el-table-column>
            <el-table-column prop="updateTime" label="修改时间" width="200" :formatter="formatDate">
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="370px" prop="operate">
                <template slot-scope="scope">
                    <el-button type="success" @click="mod(scope.row)" size="small" style="margin-right: 5px;">
                        <i class="el-icon-edit"></i>
                    </el-button>
                    <el-switch :value="scope.row.status == 1" active-color="#13ce66" inactive-color="#ff4949"
                        style="margin-right: 5px;" active-text="正常" inactive-text="注销"
                        @change="modStatus(scope.row.id)"></el-switch>
                    <el-button type="danger" @click="delHandle(scope.row.id)" size="small" style="margin-right: 5px;"
                        v-if="user.role == 0">
                        <i class="el-icon-delete"></i></el-button>
                    <el-button type="success" @click="repass(scope.row.id, scope.row.name)" size="small"
                        style="margin-left: 5px">
                        <i class="el-icon-refresh"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
            :page-size="pageSize" :page-sizes="[5, 20, 50, 100, 1000]" background
            layout="total,sizes,prev, pager, next,jumper" :total="total">
        </el-pagination>
        <el-dialog title="管理员增改表单" :visible.sync="centerDialogVisible" width="50%" center>
            <el-form :model="form" :rules="rules" ref="form" label-width="150px" style="width: 800px;margin: 10px auto;"
                enctype="multipart/form-data">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="管理员昵称" prop="name">
                            <el-input v-model="form.name" style="width: 200px;"></el-input>
                        </el-form-item>
                        <el-form-item label="账号密码" prop="password">
                            <el-input v-model="form.password" style="width: 200px;" type="password"></el-input>
                        </el-form-item>
                        <el-form-item label="手机号码" prop="telephone">
                            <el-input v-model="form.telephone" style="width: 200px;"></el-input>
                        </el-form-item>
                        <el-form-item label="管理员年龄" prop="age">
                            <el-input v-model="form.age" :min="18" :max="50" style="width: 200px;"></el-input>
                        </el-form-item>
                        <el-form-item label="管理员性别" prop="sex">

                            <el-select v-model="form.sex" placeholder="请选择性别" style="width: 200px;">
                                <el-option v-for="item in sexData" :key="item.id" :label="item.name" :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="管理员头像" prop="avatar">
                            <el-upload action="#" list-type="picture-card" :on-preview="handlePictureCardPreview"
                                :auto-upload="false" :on-exceed="handleExceed" accept="image/*" :on-remove="handleRemove"
                                :on-change="handleChanges"  :file-list="fileList"
                                :limit="1">
                                <i class="el-icon-plus"></i>
                            </el-upload>
                            <el-dialog :visible.sync="dialogVisible">
                                <img src="dialogImageUrl" width="100%" alt="">
                            </el-dialog>
                        </el-form-item></el-col>
                </el-row>
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
    name: "AdminManager",
    data() {
        const validateAge = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("请输入年龄"));
            } else {
                if (value < 18 || value > 50) {
                    callback(new Error("年龄在18-50之间"));
                }
                callback();
            }
        };
        return {
            user: JSON.parse(localStorage.getItem("CurUser")),
            http: this.$httpUrl,
            pageSize: 5,
            pageNum: 1,
            total: 0,
            tableData: [],
            name: "",
            sex: "",
            telephone: "",
            status: 1,
            statusData: [
                {
                    id: 1,
                    name: "正常",
                },
                {
                    id: 0,
                    name: "注销",
                },
            ],
            sexData: [
                {
                    id: 0,
                    name: "女",
                },
                {
                    id: 1,
                    name: "男",
                },
            ],
            form: {
                id: "",
                name: "",
                telephone: "",
                password: "",
                status: 1,
                sex: 1,
                role: 1,
                age: 18,
                avatar: null,
            },
            rules: {
                name: [
                    { required: true, message: "请输入管理员姓名", trigger: "blur" },
                    {
                        min: 2,
                        max: 16,
                        message: "长度在 2 到 16 个字符",
                        trigger: "blur",
                    },
                ],
                password: [
                    { required: true, message: "请输入管理员密码", trigger: "blur" },
                    {
                        min: 6,
                        max: 16,
                        message: "长度在 6 到 16 个字符",
                        trigger: "blur",
                    },
                ],
                age: [
                    { required: true, message: "请输入管理员年龄", trigger: "blur" },
                    {
                        type: "number",
                        validator: validateAge,
                        trigger: "blur",
                    },
                ],
                sex: [{ required: true, message: "请选择管理员性别", trigger: "blur" }],
                avatar: [{ required: true, message: "请上传头像", trigger: "blur" }],
                telephone: [
                    { required: true, message: "请输入管理员手机号", trigger: "blur" },
                    {
                        min: 11,
                        max: 11,
                        message: "清输入11位手机号",
                        trigger: "blur",
                    },
                    {
                        pattern:
                            /^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\d{8}$/,
                        message: "手机号格式不正确",
                    },
                ],
            },
            dialogImageUrl: '',
            dialogVisible: false,
            picList: [],
            fileList: [],
            delBtlStatu: true,
            multipleSelection: [],
            centerDialogVisible: false,
        }
    },
    methods: {
        // 重置搜索栏
        resetParam() {
            if (this.user.role == 0) this.status = "";
            else this.status = 1;
            this.name = "";
            this.telephone = "";
            this.sex = "";
        },
        // 重置表单
        resetForm() {
            this.form = {
                id: "",
                name: "",
                telephone: "",
                password: "",
                status: 1,
                sex: 1,
                role: 1,
                age: 18,
                avatar: null,
            }
            this.fileList = []
            this.picList = []
            this.dialogImageUrl = ''
            this.dialogVisible = false;
            this.emptyUpload()
        },
        // 时间格式化
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
        // 新增
        add() {
            // 打开对话框
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                // 重置表单
                this.resetForm();
            });
        },
        // 修改
        mod(row) {
            this.$nextTick(() => {
                //赋值到表单
                this.$axios.get(this.http + "/user/info/" + row.id).then((res) => {
                    this.form = res.data.data;
                    // 对于图片修改表单处理
                    if (this.form.avatar != "") {
                        this.fileList = [];
                        this.fileList.push({
                            url: this.http + "/upload/avatar/" + this.form.avatar,
                        });
                    }
                    this.centerDialogVisible = true;

                });
            });
        },
        // 保存提交表单
        save() {
            // 校验表单
            this.$refs.form.validate((valid) => {
                if (valid) {
                    // 有id，说明是修改
                    if (this.form.id) {
                        // 判断图片也没有完成上传
                        if (
                            this.fileList[0].url !==
                            "http://localhost:8888/upload/avatar/" + this.form.avatar
                        ) {
                            this.submitUpload(this.fileList[0]);
                            this.doMod(); // 完成上传后执行 doMod 函数

                        } else {
                            this.doMod();
                        }
                    } else {
                        if (this.fileList.length) {
                            this.submitUpload(this.fileList[0]);
                            this.doSave(); // 完成上传后执行 doSave 函数
                        } else {
                            this.doSave();
                        }
                    }

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
            // 请求后端新增管理员接口
            this.$axios
                .post(this.http + "/user/save", this.form)
                .then((res) => res.data)
                .then((res) => {
                    // code为200说明成功了
                    if (res.code == 200) {
                        // 提示
                        this.$message({
                            message: "操作成功！",
                            type: "success",
                        });
                        // 关闭dialog对话框
                        this.centerDialogVisible = false;
                        // 重新查询表格数据
                        this.loadPost();
                        // 重置表单
                        this.resetForm();
                    } else {
                        // 失败提示
                        this.$message({
                            message: res.msg,
                            type: "error",
                        });
                    }
                });
        },
        doMod() {
            this.$axios
                .post(this.http + "/user/update", this.form)
                .then((res) => res.data)
                .then((res) => {
                    console.log(res);
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
                            message: res.msg,
                            type: "error",
                        });
                    }
                });
        },
        submitUpload(file) {
            const config = {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            };

            let fd = new FormData();
            fd.append("file", file.raw);
            fd.append("fileName", file.name);
            this.$axios
                .post(this.http + "/upload/avatar", fd, config)
                .then((res) => {
                    res = res.data
                    if (res.code === 200) {
                        this.form.avatar = res.msg;
                        this.$message({
                            message: "上传成功！",
                            type: "success",
                        });
                    } else {
                        this.$message({
                            message: "未上传成功！",
                            type: "error",
                        });
                    }
                });
        },
        handleSuccess(response) {
            console.log(response);
            this.$message({
                message: "上传成功",
                type: "success",
            });
            return true;
        },
        handleRemove(file, fileList) {
            console.log(fileList)
            this.fileList = [];
            for (const i in this.picList) {
                if (this.picList[i].key === file.uid) {
                    this.picList.splice(i, 1)
                }
            }
        },
        handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url
            this.dialogVisible = true
        },
        /**
         * 清空上传组件
         */
        emptyUpload() {
            const Img = this.$refs.upload
            if (Img) {
                if (Img.length) {
                    Img.forEach(item => {
                        item.clearFiles()
                    })
                } else {
                    this.$refs.upload.clearFiles()
                }
            }
        },
        handleChanges(files) {
            // console.log(files);
            this.fileList.push(files)
            this.submitUpload(files);
        },
        handleExceed(files) {
            console.log(files);
            this.$message.error("只能上传一张头像!");
        },
        modStatus(id) {
            this.$axios
                .post(this.http + "/user/modStatus/" + id)
                .then((res) => res.data)
                .then((res) => {
                    if (res.code == 200) {
                        this.loadPost();
                        this.$message({
                            message: "修改成功",
                            type: "success",
                        });
                    } else {
                        this.$message({
                            message: "操作失败！",
                            type: "error",
                        });
                    }
                });
        },
        repass(id, username) {
            this.$confirm("将重置管理员【" + username + "】的密码, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.$axios
                        .post(this.http + "/user/repass/" + id)
                        .then((res) => {
                            console.log(res);
                            this.$message({
                                showClose: true,
                                message: "恭喜你，操作成功",
                                type: "success",
                            });
                            this.loadPost()
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
        delHandle(id) {
            var ids = [];
            if (id) {
                // 单删情况
                ids.push(id);
            } else {
                // 批量删
                this.multipleSelection.forEach((row) => {
                    ids.push(row.id);
                });
            }
            this.$confirm("将删除管理员一切有关数据， 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.$axios
                        .post(this.http + "/user/deleteBatch", ids, {
                            headers: {
                                "Content-Type": "application/json;charset=UTF-8",
                            },
                        })
                        .then((res) => {
                            console.log(res);

                            this.$message({
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
        // 清空批量删除
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
        loadPost() {
            this.$axios.post(this.http + "/user/listPage", {
                pageSize: this.pageSize,
                pageNum: this.pageNum,
                param: {
                    role: 1 + "",
                    name: this.name,
                    status: this.status + "",
                    sex: this.sex + "",
                    telephone: this.telephone
                }
            }).then((res) => res.data).then((res) => {
                if (res.code == 200 && res.total != 0) {
                    this.tableData = res.data
                    this.total = res.total
                } else {
                    this.$message({
                        message: "数据查询失败",
                        type: 'warning'
                    })
                }
            })
        }
    },
    created() {
        this.loadPost()
    }
}
</script>
<style scoped>
.el-pagination {
    text-align: center;
    margin-top: 22px;
}
</style>