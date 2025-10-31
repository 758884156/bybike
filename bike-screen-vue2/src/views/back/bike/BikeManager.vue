<template>
    <div>
        <div style="margin-bottom: 5px;text-align: center;">
            <el-input placeholder="请输入单车名称" prefix-icon="el-icon-search" v-model="name"
                style="width: 200px; margin-left: 5px" @keyup.enter.native="loadPost()">
            </el-input>
            <el-select v-model="platform" placeholder="请选择单车平台" style="margin-left: 5px">
                <el-option v-for="item in platformData" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
            </el-select>
            <el-button icon="el-icon-search" type="primary" style="margin-left: 5px" @click="loadPost()" circle></el-button>
            <el-button icon="el-icon-refresh" type="success" style="margin-left: 5px" @click="resetParam()"
                circle></el-button>
            <el-button type="primary" style="margin-left: 5px" @click="add" v-if="user.role != 2">新增</el-button>
            <el-button type="danger" slot="reference" :disabled="delBtlStatu" @click="delHandle(null)"
                style="margin-left: 5px" v-if="user.role == 0">批量删除</el-button>
        </div>
        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" border style="width: 100%" stripe
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column prop="name" label="单车名称" width="150">
            </el-table-column>
            <el-table-column prop="image" label="单车展示" width="150">
                <template slot-scope="scope">
                    <img :src="http + '/upload/image/' + scope.row.image" min-width="70" height="70" />
                    <!-- {{http + '/upload/image/' + scope.row.image  }} -->
                </template>
            </el-table-column>
            <el-table-column prop="platfrom" label="单车平台" width="100" :formatter="formatPlatform">
            </el-table-column>
            <el-table-column prop="count" label="单车数量" width="200">
                <template slot-scope="scope">
                    <el-tag type="primary">{{ scope.row.count }}辆</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="firstDistance" label="起步时长" width="100">
                <template slot-scope="scope">
                    <el-tag type="success">{{ scope.row.firstDistance }}分钟</el-tag> </template>
            </el-table-column>
            <el-table-column prop="firstPrice" label="起步价" width="100">
                <template slot-scope="scope">
                    <el-tag type="success">{{ scope.row.firstPrice }}元</el-tag> </template>
            </el-table-column>
            <el-table-column prop="price" label="计费价" width="100">
                <template slot-scope="scope">
                    <el-tag type="success">{{ scope.row.price }}元/分钟</el-tag>
                </template>
            </el-table-column> <el-table-column prop="createTime" label="创建时间" width="200" :formatter="formatDate">
            </el-table-column><el-table-column prop="remark" label="单车介绍" width="300">
            </el-table-column>
            <el-table-column prop="updateTime" label="修改时间" width="200" :formatter="formatDate">
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="270px" prop="operate">
                <template slot-scope="scope">
                    <el-button type="success" @click="mod(scope.row)" size="small" v-if="user.role != 2">编辑</el-button>
                    <el-button type="danger" @click="delHandle(scope.row.id)" size="small" v-if="user.role == 0">
                        <i class="el-icon-delete"></i></el-button>
                    <el-button type="success" @click="invert(scope.row)" size="small" style="margin-left: 5px"
                        v-if="user.role != 2">
                        投放</el-button>
                        <el-button type="primary" @click="order(scope.row)" size="small" style="margin-left: 5px" v-if="user.role == 2">
            下单使用</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
            :page-size="pageSize" :page-sizes="[5, 20, 50, 100, 1000]" background
            layout="total,sizes,prev, pager, next,jumper" :total="total">
        </el-pagination>
        <el-dialog title="单车增改表单" :visible.sync="centerDialogVisible" width="50%" center>
            <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="width: 800px; margin: 10px auto"
                enctype="multipart/form-data">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="单车名称" prop="name">
                            <el-input v-model="form.name" style="width:300px;"></el-input>
                        </el-form-item>
                        <el-form-item label="单车平台" prop="platform">
                            <el-select v-model="form.platform" style="width:300px;" placeholder="请选择单车平台">
                                <el-option v-for="item in platformData" :key="item.id" :label="item.name"
                                    :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>


                        <el-form-item label="起步价" prop="firstPrice">

                            <el-input-number v-model="form.firstPrice" style="width:250px;" :precision="2"
                                :step="0.01"></el-input-number>
                            元
                        </el-form-item>
                        <el-form-item label="起步时长" prop="firstDistance">

                            <el-input-number v-model="form.firstDistance" style="width:250px;"
                                :step="1"></el-input-number>分钟

                        </el-form-item>
                        <el-form-item label="计时价" prop="price">

                            <el-input-number v-model="form.price" :precision="2" style="width:250px;"
                                :step="0.01"></el-input-number>元/分钟

                        </el-form-item> <el-form-item label="单车介绍" prop="remark">

                            <el-input type="textarea" style="width:300px;" rows="5" v-model="form.remark"></el-input>

                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item label="单车展示" prop="image">
                            <el-upload action="#" list-type="picture-card" :on-preview="handlePictureCardPreview"
                                :auto-upload="false" :on-exceed="handleExceed" accept="image/*" :on-remove="handleRemove"
                                :on-change="handleChanges" :file-list="fileList" :limit="1">
                                <i class="el-icon-plus"></i>
                            </el-upload>
                            <el-dialog :visible.sync="dialogVisible">
                                <img width="100%" :src="dialogImageUrl" alt="">
                            </el-dialog>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog title="投放单车" :visible.sync="invertDialogVisible" width="800px" center>
            <el-form ref="invertform" :rules="invertrules" :model="invertform" label-width="100px">
                <el-form-item label="共享单车" prop="bike">
                    <el-select v-model="invertform.bike" placeholder="请选择单车" disabled style="width: 500px;">
                        <el-option v-for="item in bikeData" :key="item.id" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="投放数量" prop="count">
                    <el-input-number v-model="invertform.count" style="width:500px;" :step="1"></el-input-number>辆

                </el-form-item>
                <el-form-item label="投放时间" prop="createTime">
                    <el-date-picker v-model="invertform.createTime" style="width: 500px;" value-format="yyyy-MM-dd HH:mm:ss"
                        type="date" placeholder="选择日期"></el-date-picker>
                </el-form-item>
                <el-form-item label="投放地址" prop="address">

                    <el-input @click.native="mapVisiable = true" style="width: 500px;" v-model="invertform.address"
                        placeholder="请选择投放地址"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="invertDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="doInvert">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog width="75%" title="坐标查询" :visible.sync="mapVisiable" append-to-body>
            <AMap :address="invertform" :mapVisiable="mapVisiable" @closeMap="closeMap"></AMap>

        </el-dialog>
    </div>
</template>
<script>
export default {
    name: 'BikeManager',
    data() {
        return {
            http: this.$httpUrl,
            user: JSON.parse(localStorage.getItem("CurUser")),
            pageSize: 5,
            pageNum: 1,
            total: 0,
            tableData: [],
            bikeData: [],
            platformData: [],
            name: "",
            platform: "",

            form: {
                id: "",
                name: "",
                platform: "",
                remark: "",
                firstDistance: 10,
                firstPrice: 10,
                price: 1,
                image: null,
            },
            rules: {
                name: [{ required: true, message: "请输入单车名称", trigger: "blur" }],
                platform: [{ required: true, message: "请选择单车平台", trigger: "blur" }],
                image: [{ required: true, message: "请上传单车图片", trigger: "blur" }],
            },

            dialogImageUrl: '',
            dialogVisible: false,
            picList: [],
            fileList: [],
            delBtlStatu: true,
            multipleSelection: [],
            centerDialogVisible: false,

            invertform: {
                id: "",
                bike: "",
                count: 100000,
                createTime: "",
                province: "",
                city: "",
                lon: "",
                lat: "",
                address: ""
            },
            invertrules: {
                bike: [{ required: true, message: "请选择共享单车", trigger: "blur" }],
                count: [{ required: true, message: "请输入投放数量", trigger: "blur" }],
                createTime: [{ required: true, message: "请选择投放时间", trigger: "blur" }],
                address: [{ required: true, message: "请选择投放地址", trigger: "blur" }],
            },

            invertDialogVisible: false,
            mapVisiable: false,
        }
    }, methods: {
        order(row){
      let orderform={
        user:this.user.id,
        bike:row.id
      }
      this.$confirm("确定下单使用该单车， 确认后开始计费?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "success",
      })
        .then(() => {
          this.$axios
            .post(this.$httpUrl + "/bikeorder/save", orderform
            )
            .then((res) => {
              console.log(res)
              res=res.data
              if(res.code==200){
                this.$message({
                message: "恭喜你，下单成功",
                type: "success",
              });
              this.loadPost();
              }
              else{
                this.$message({
                message: res.msg,
                type: "error",
              });
              }
            })
            .catch((err) => {
              console.error(err);
              this.$message({
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



        closeMap(formData) {
            console.log('子组件 map 中的 form 表单地址数据:', formData);
            // 关闭地图对话框，并将 mapVisible 设为 false
            this.invertform.address = formData.address;
            this.invertform.city = formData.city;
            this.invertform.province = formData.province
            this.invertform.lon = formData.lon
            this.invertform.lat = formData.lat
            this.mapVisiable = false
        },
        invert(row) {
            this.invertform.bike = row.id
            this.invertDialogVisible = true
        },
        doInvert() {
            this.$refs.invertform.validate((valid) => {
                if (valid) {
                    this.$axios
                        .post(this.$httpUrl + "/invert/save", this.invertform)
                        .then((res) => res.data)
                        .then((res) => {
                            console.log(res);
                            if (res.code == 200) {
                                this.$message({
                                    message: "操作成功！",
                                    type: "success",
                                });
                                this.invertDialogVisible = false;

                                this.loadPost();
                                this.resetForm();
                            } else {
                                this.$message({
                                    message: res.msg,
                                    type: "error",
                                });
                            }
                        });
                }
            });

        },

        resetParam() {
            this.name = "";
            this.platform = "";
        },

        resetForm() {
            this.form = {
                id: "",
                name: "",
                platform: "",
                remark: "",
                firstDistance: 10,
                firstPrice: 10,
                price: 1,
                image: null,
            }

            this.fileList = []
            this.picList = []
            this.dialogImageUrl = ''
            this.dialogVisible = false;
            this.emptyUpload()
        },

        loadBike() {
            this.$axios
                .get(this.http + "/bike/list")
                .then((res) => res.data)
                .then((res) => {
                    this.bikeData = res;
                });
        },
        loadPlatform() {
            this.$axios
                .get(this.http + "/platform/list")
                .then((res) => res.data)
                .then((res) => {
                    this.platformData = res;
                });
        },
        formatPlatform(row) {
            let temp = this.platformData.find((item) => {
                return item.id == row.platform;
            })
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
        add() {
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                this.resetForm();
            });
        },
        mod(row) {
            this.$nextTick(() => {
                //赋值到表单
                this.$axios.get(this.http + "/bike/info/" + row.id).then((res) => {
                    this.form = res.data.data;

                    if (this.form.image != "") {
                        this.fileList = [];
                        this.fileList.push({
                            url: "http://localhost:8888/upload/image/" + this.form.image,
                        });
                    }
                    this.centerDialogVisible = true;
                });
            });
        },
        save() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    if (this.form.id) {
                        if (
                            this.fileList[0].url !==
                            "http://localhost:8888/upload/image/" + this.form.image
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
            this.$axios
                .post(this.http + "/bike/save", this.form)
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
        doMod() {
            this.$axios
                .post(this.http + "/bike/update", this.form)
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
                .post(this.http + "/upload/image", fd, config)
                .then((res) => {
                    res = res.data
                    if (res.code === 200) {
                        this.form.image = res.msg;
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
        }, handleRemove(file, fileList) {
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
            const mainImg = this.$refs.upload
            if (mainImg) {
                if (mainImg.length) {
                    mainImg.forEach(item => {
                        item.clearFiles()
                    })
                } else {
                    this.$refs.upload.clearFiles()
                }
            }
        },
        handleChanges(files) {
            this.fileList.push(files)
            this.submitUpload(files);
        },
        handleExceed(files) {
            console.log(files);
            this.$message.error("只能上传一张图片!");
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
            this.$confirm("将删除单车一切有关数据， 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.$axios
                        .post(this.http + "/bike/deleteBatch", ids, {
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
        }, loadPost() {
            this.$axios
                .post(this.http + "/bike/listPage", {
                    pageSize: this.pageSize,
                    pageNum: this.pageNum,
                    param: {
                        name: this.name,
                        platform: this.platform + ""
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

    }, created() {
        this.loadBike();
        this.loadPlatform();
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