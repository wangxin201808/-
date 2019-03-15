<template>
    <div class="wrap">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80" >
        <FormItem label="姓名" prop="name">
          <Input v-model="formValidate.name" placeholder="请输入您的姓名"></Input>
        </FormItem>
        <FormItem label="手机号" prop="phone">
          <Input v-model="formValidate.phone" placeholder="请输入您的电子邮件"></Input>
        </FormItem>
        <FormItem label="密码" prop="secret">
          <Input v-model="formValidate.secret" placeholder="请输入您的密码"></Input>
        </FormItem>
        <FormItem label="邮箱" prop="email">
          <Input v-model="formValidate.email" placeholder="请输入您的电子邮件"></Input>
        </FormItem>
        <FormItem label="出生日期">
          <Row>
            <Col span="4">
              <FormItem prop="birth">
                <DatePicker type="date" placeholder="请选择" v-model="formValidate.birth"></DatePicker>
              </FormItem>
            </Col>
          </Row>
        </FormItem>
        <FormItem label="性别" prop="sex">
          <Row>
            <Col span="4">
              <RadioGroup v-model="formValidate.sex" @on-change="sexChange">
                <Radio label="1">男</Radio>
                <Radio label="0">女</Radio>
              </RadioGroup>
            </Col>
          </Row>

        </FormItem>
        <FormItem label="爱好样式" prop="hobby">
          <Row>
            <Col span="8">
              <CheckboxGroup v-model="formValidate.hobby" @on-change="hobbyChange">
                <Checkbox label="运动"></Checkbox>
                <Checkbox label="居家"></Checkbox>
                <Checkbox label="沉稳"></Checkbox>
                <Checkbox label="年轻"></Checkbox>
              </CheckboxGroup>
            </Col>
          </Row>

        </FormItem>
        <FormItem label="家庭地址" prop="address">
          <Input v-model="formValidate.address" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入详细的家庭地址"></Input>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="handleSubmit">提交</Button>
          <Button @click="handleReset" style="margin-left: 8px">重置</Button>
        </FormItem>
      </Form>
    </div>
</template>

<script>
  import {getUserInfo, setUserInfo} from "../api/api";
  import Util from '../common/js/util'
  export default {
        name: "profile",
      data () {
        return {
          hobby: [],

          formValidate: {
            name: '',
            phone: '',
            secret: '',
            email: '',
            sex: -1,
            hobby: [],
            birth: '',
            address: ''
          },
          ruleValidate: {
            name: [
              {required: true, type: 'string', message: '账号不能为空', trigger: 'blur' }
            ],
            phone: [
              {required: true, message: '手机号不能为空', trigger: 'blur' },
              { type: 'regexp', pattern: "/^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/", message: '邮箱不符合规范', trigger: 'blur' }
            ],
            secret: [
              {required: true, type: 'string', message: '密码不能为空', trigger: 'blur' }
            ],
            email: [
              { type: 'email', message: '邮箱不符合规范', trigger: 'blur' }
            ],
            sex: [
              { required: true, message: '请选择性别', trigger: 'change' }
            ],
            hobby: [
              { required: true, type: 'array', min: 1, message: '请至少选择一个爱好', trigger: 'blur' }
            ],
            birth: [
              { required: true, type: 'date', message: '请选择出生日期', trigger: 'change' }
            ],
            address: [
              { required: true, message: '请输入家庭地址', trigger: 'blur' },
            ]
          }
        }
      },
      methods: {
        sexChange (val) {
          console.log(this.formValidate.sex);
        },

        hobbyChange (val) {
          console.log(this.formValidate.hobby)
        },

        deepCopy(arr) {
          var obj = arr instanceof Array ? [] : {}
          for (var item in arr) {
            if(item == "birth"){
              obj[item] = arr.birth;
            }else if (typeof arr[item] === "object") {
              obj[item] = this.deepCopy(arr[item]);
            } else {
              obj[item] = arr[item];
            }
          }
          return obj;
        },


        handleSubmit () {
          this.$refs.formValidate.validate((valid) => {
            if (valid) {
              // let id = this.$store.getters.getUser.id;
              let params = this.deepCopy(this.formValidate);
              params.birth = Util.formatDate.format(params.birth);


              params.hobby = params.hobby.join(',');
              console.log(params);

              setUserInfo(params).then((res) => {
                if (res.data.status == 200) {
                  this.$Message.success("信息保存成功");
                  this.$router.push({name: 'MyMain'});
                }
              })


            } else {
              this.$Message.error('Fail!');
            }
          })
        },
        handleReset (name) {
          this.$refs[name].resetFields();
        },
        // 获取用户信息
        getUsers () {

        }
      },
      mounted() {
        let params = {id: this.$store.getters.getUser.id}
        getUserInfo(params).then((res) => {
          console.log(res);
          if (res.data.status == 200) {
            let data = res.data.data;
            if (data.sex == null) {
              data.sex = -1;
            }
            if (data.hobby == null) {
              data.hobby = [];
            } else {
              data.hobby = data.hobby.split(',');
            }
            this.formValidate = Object.assign(this.formValidate, res.data.data) ;
            console.log("form",this.formValidate);
          } else {
            this.$Message.error("用户信息获取失败");
          }
        })
      }
    }
</script>

<style scoped>
  .wrap {
    width: 800px;
    margin: 50px auto;
    padding: 20px 10px;
    border-radius: 10px;
    background-color: #dcdee2;
    text-align: center;
  }
</style>
