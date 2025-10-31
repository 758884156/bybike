<template>
    <div>
        <div class="header"
            style="height: 60px; background-color:rgb(6, 91, 209); color: white; display: flex; justify-content: center; align-items: center;">
            <h2 style="margin: 0;">可视化大屏项目登录页面</h2>
        </div>
        <div class="login-container">
            <div class="login-box">
                <div class="avatar-container">
                    <div class="avatar-border">
                        <div>
                            <el-avatar :size="100" :src="form.avatar
                                ? 'http://localhost:8888/upload/avatar/' + form.avatar
                                : 'http://localhost:8888/upload/avatar/' + 'avatar'
                                "></el-avatar>
                        </div>
                    </div>
                </div>
                <el-form :model="form" ref="form" :rules="rules" label-width="100px" style="margin-top: 50px">
                    <el-form-item label="用户名" prop="name">
                        <el-input v-model="form.name" aria-placeholder="用户名/邮箱" @keyup.enter.native="selectUser"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="form.password" type="password" show-password></el-input>
                    </el-form-item>

                    <el-form-item style="margin-top: 20px">
                        <el-button type="primary" class="login-button" @click="Login" style="width: 300px">登录</el-button>
                    </el-form-item>
                    <el-form-item style="margin-top: 20px">
                        <el-button type="success" class="login-button" @click="Register" style="width: 300px">注册</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>
   
<script>
export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: "Login",
    data() {
        return {
            form: {
                name: "admin",
                password: "admin",
                avatar: null,
            },
            rules: {
                name: [{ required: true, message: "请输入账号", trigger: "blur" }],
                password: [{ required: true, message: "请输密码", trigger: "blur" }],
            },
        }
    },
    methods: {
        Register() {
            this.$router.push("/Register");
        },
        selectUser() {
            if (this.form.name != "") {
                this.$axios
                    .get(this.$httpUrl + "/user/getAvatar/" + this.form.name)
                    .then((res) => res.data)
                    .then((res) => {
                        console.log(res.data);
                        if (res.code == 200) {
                            this.form.avatar = res.data;
                        }
                    });
            }

        },
        Login() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    if (this.form.avatar == "")
                        this.selectUser()
                    this.$axios
                        .post(this.$httpUrl + "/user/login", this.form)
                        .then((res) => res.data)
                        .then((res) => {
                            if (res.code == 200) {
                                this.$store.commit("setUser", JSON.stringify(res.data.user));

                                this.$store.commit("setMenu", JSON.stringify(res.data.menu));
                                this.$message({
                                    message: "恭喜您,登录成功！欢迎回家",
                                    type: "success",
                                    onClose: () => {
                                        this.$router.push("/");
                                    },
                                });
                            }
                        });
                } else {
                    this.$message({
                        message: "校验失败",
                        type: "error",
                    });
                    return false;
                }
            });
        },
    }
}
</script>
   
<style scoped>
.login-container {
    align-items: center;
    justify-content: center;
    height: 100vh;
    display: flex;
    background-size: 100% 100%;
    background-image: url(http://localhost:8888/upload/file/bj);
}

.login-box {
    width: 400px;
    border-radius: 6px;
    padding: 30px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    position: relative;
    backdrop-filter: blur(10px);
    text-align: center;
}

.avatar-container {
    position: absolute;
    top: -50px;
    left: 50%;
    transform: translateX(-50%);
}

.avatar-border {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    border: 2px solid #ccc;
    position: relative;
    z-index: 1;
}

.avatar {
    width: 100px;
    height: 100px;
    border-radius: 47px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 2;
}

.login-button {
    width: 80%;
    margin: 0 auto;
    display: block;
}
</style>