<template>
    <div>
        <div class="box">
            <el-card class="card">
                <el-row class="card-row">
                    <el-col :span="8">
                        <el-card shadow="hover" class="box-card centered-card">
                            <div>
                                用户角色
                                <el-tag :type="user.role == 0 ? 'success' : user.role == 1 ? 'primary' : 'info'">
                                    {{ user.role == 0 ? '超级管理员' : user.role == 1 ? '管理员' : '用户' }}
                                </el-tag>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" class="box-card centered-card red">
                            <div>
                                注册时间
                                <el-tag type="success">
                                    {{ formatDate(user.createTime) }}
                                </el-tag>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" class="box-card centered-card blue">
                            <div>
                                账号状态
                                <el-tag :type="user.status == 1 ? 'success' : 'danger'">
                                    {{ user.status == 1 ? "正常" : "注销" }}
                                </el-tag>
                            </div>
                        </el-card></el-col>
                </el-row>

            </el-card>
            <el-card class="form">
                <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="margin-top:50px;">
                    <el-form-item label="用户昵称" prop="name">
                        <el-input v-model="form.name" style="width: 800px;" @input="form.update = 1"></el-input>
                    </el-form-item>

                    <el-form-item label="手机号码" prop="telephone">
                        <el-input v-model="form.telephone" style="width: 800px;" @input="form.update = 1"></el-input>
                    </el-form-item>
                    <el-form-item label="用户年龄" prop="age">
                        <el-input-number v-model="form.age" :min="18" :max="50" style="width: 800px;"
                            @input="form.update = 1"></el-input-number>
                    </el-form-item>
                    <el-form-item label="用户性别" prop="sex">
                        <el-select v-model="form.sex" placeholder="请选择性别" style="width: 800px;" @change="form.update = 1">
                            <el-option v-for="item in sexData" :key="item.id" :label="item.name" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" v-if="form.update == 1" @click="doMod"
                            style="width: 800px;">保存修改</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </div>
    </div>
</template>
<script>
import EventBus from "../../../assets/EventBus.js";
export default {
    name: "SystemSetting",
    data() {
        return {
            user: JSON.parse(localStorage.getItem("CurUser")),
            form: {
                name: '',
                age: 18,
                sex: 1,
                telephone: '',
                role: "",
                update: 0
            },
            rules: {
                name: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' }
                ],

                age: [
                    { required: true, message: '请输入年龄', trigger: 'change' },
               
                ],

                sex: [
                    { required: true, message: '请选择性别', trigger: 'change' }
                ],

                telephone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
                ],

            },
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
        }
    },
    methods: {
        formatDate(data) {
            if (data == null) {
                return null;
            }
            let dt = new Date(data);
            return (
                dt.getFullYear() +
                "年" +
                (dt.getMonth() + 1) +
                "月" +
                dt.getDate() +
                "日" +
                dt.getHours() +
                "时" +
                dt.getMinutes() +
                "分" +
                dt.getSeconds() +
                "秒"
            );
        },
        getUserById() {
            this.$axios
                .get(this.$httpUrl + "/user/info/" + this.user.id)
                .then((res) => {
                    this.user = res.data.data;
                });

        },
        init() {
            this.getUserById()
            this.form = this.user
        },
        doMod() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    this.$axios
                        .post(this.$httpUrl + "/user/update", this.form)
                        .then((res) => res.data)
                        .then((res) => {
                            console.log(res);
                            if (res.code == 200) {
                                this.$message({
                                    message: "操作成功！",
                                    type: "success",
                                });
                                this.form.update = 0
                                this.getUserById()
                                EventBus.$emit("update-user");
                            } else {
                                this.$message({
                                    message: res.msg,
                                    type: "error",
                                });
                            }
                        });

                } else {
                    this.$message({
                        message: "请填写正确数据",
                        type: "error",
                    });
                    return false;
                }
            });
        },

    }, mounted() {
        this.init()
        // 使用箭头函数确保this指向当前组件实例
        this.timer = setInterval(() => {
            this.getUserById();
        }, 1000);
    },
    beforeDestroy() {
        // 清除定时器
        if (this.timer) {
            clearInterval(this.timer);
        }
    },
}
</script>
<style scoped>
.box {
    text-align: center;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.card {
    background-color: blanchedalmond;
    width: 100%;
}

.form {
    background-color: burlywood;
    width: 100%;
}
</style>