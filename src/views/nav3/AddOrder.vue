<template>
    <!--新增界面-->
    <el-row v-if="isEidt">
        <el-col :offset="3" :span="16" >
            <el-form :model="eidtFrom" label-width="80px" :rules="eidtFromRules" ref="eidtFrom">
                <el-form-item label="物流单号" prop="shippingCode" >
                    <el-input v-model="eidtFrom.shippingCode" placeholder="物流单号" auto-complete="off" class="w-240"></el-input>
                </el-form-item>
                <el-form-item label="物流名称" prop="shippingName"  >
                    <el-dropdown split-button type="primary"  @command="handleClick">
                        {{ kudi }}
                        <el-dropdown-menu slot="dropdown" >
                            <el-dropdown-item command="顺丰">顺丰</el-dropdown-item>
                            <el-dropdown-item command="中通">中通</el-dropdown-item>
                            <el-dropdown-item command="圆通">圆通</el-dropdown-item>
                            <el-dropdown-item command="申通">申通</el-dropdown-item>
                            <el-dropdown-item command="韵达">韵达</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-form-item>
                <el-form-item label="商品名称" prop="title" width="150"  >
                    <el-input v-model="eidtFrom.title" style="width: 240px" class="w-240" ></el-input>
                </el-form-item>
                <el-form-item label="数量" prop="num" width="100"  >
                    <el-input-number v-model="eidtFrom.num" @change="handleNumChange" :min="1" label="描述文字"></el-input-number>
                </el-form-item>
                <el-form-item label="交易状态" prop="status" width="150"  >
                    <el-radio-group v-model="eidtFrom.status" @change="statusChange">
                        <el-radio class="radio" :label="3">未发货</el-radio>
                        <el-radio class="radio" :label="4">已发货</el-radio>
                        <el-radio class="radio" :label="5">交易成功</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="收件人" prop="receiverName" width="120"  >
                    <el-input v-model="eidtFrom.receiverName" class="w-240" ></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="receiverMobile" width="150" >
                    <el-input v-model="eidtFrom.receiverMobile" class="w-240" ></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="receiverAddress" min-width="180" >
                    <el-input type="textarea" v-model="eidtFrom.receiverAddress"  ></el-input>
                </el-form-item>
            </el-form>
            <div class="add-footer">
                <el-button @click="resetForm('eidtFrom')">重置</el-button>
                <el-button type="primary" @click.native="eidtSubmit('eidtFrom')" :loading="addLoading" >修改订单</el-button>
            </div>
        </el-col>
    </el-row>
    <div class="nothing-div" v-else>暂无修改的商品</div>

</template>
<script>
    import { editOrder } from '../../api/api';
    export default {

        data() {

            return {
                // 下拉按钮显示
                kudi: "请选择快递公司",
                // 判断是否是修改订单，true：修改， false：不修改
                isEidt: true,

                addLoading: false,
                eidtFromRules: {
                    shippingCode: [
                        { required: true, message: '请输入物流单号', trigger: 'blur' }
                    ],
                    shippingName: [
                        { required: true, message: '请输入物流名称', trigger: 'blur' }
                    ],
                    receiverName: [
                        { required: true, message: '请输入收件人姓名', trigger: 'blur' }
                    ],
                    receiverMobile: [
                        { required: true, message: '请输入收件人手机号', trigger: 'change' }
                    ],
                    receiverAddress: [
                        { required: true, message: '请输入收件地址', trigger: 'blur'}
                    ]

                },
                //新增界面数据
                eidtFrom: {}
            }
        },
        methods: {
            //性别显示转换
            formatFeature: function (row, column) {
                return row.feature == 0 ? '实木' : row.sex == 1 ? '金属' : '板材';
            },
            // 订单状态转换
            handleNumChange(val) {
              console.log(val);
              this.eidtFrom.num = val;
            },
            // 物流转换
            handleClick(command){
                this.kudi = command;
            },
            // 单选框变化
            statusChange(value){
                console.log(value);
                this.eidtFrom.status = value;
            },
            // 修改
            eidtSubmit: function (eidtFrom) {
                this.eidtFrom.shippingName = this.kudi;
                this.$refs[eidtFrom].validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            console.log("*****************");
                            this.addLoading = true;
                            let furniture = this.eidtFrom;

                            console.log("furniture", furniture);

                            var that = this;
                            editOrder(furniture).then((res) => {
                                res = res.data;
                                if (res.message == "成功"){
                                    that.addLoading = false;
                                    //NProgress.done();
                                    that.$message({
                                        message: '提交成功',
                                        type: 'success'
                                    });
                                    this.kudi = "请选择快递公司"
                                    that.$refs['eidtFrom'].resetFields();
                                    this.$router.push({name: '订单查询'})
                                } else {
                                    that.$message({
                                        message: '保存失败',
                                        type: 'fail'
                                    });
                                }

                            });
                        }).catch(()=>{});

                    }
                });
            },
            // 重置
            resetForm: function (eidtFrom) {
                this.$refs[eidtFrom].resetFields();
            }
        },
        mounted() {
            this.isEidt = false;
            if (this.$route.params.data != ":data")  {
                this.isEidt = true;
                this.eidtFrom = this.$route.params.data[0]
                this.kudi = this.eidtFrom.shippingName
            }
        }
    };

</script>

<style scoped>
    .w-240{
        width: 240px;
    }
    .add-footer{
        text-align: center;
    }

    .nothing-div{
        width: 170px;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(0,-50%);
        -webkit-transform: translate(0,-50%);
        -moz-transform: translate(0,-50%);
        color: rgba(0, 0, 0, 0.28);
        font-size: xx-large;
        font-weight: bolder;
        font-style:italic;
    }
</style>