<template>
  <Form ref="formInline" :model="formInline" :rules="ruleInline" class="form">
    <FormItem prop="password1"  >
      <Input type="password" v-model="formInline.password1" placeholder="请输入密码" >
        <Icon type="ios-lock-outline" slot="prepend" ></Icon>
      </Input>
    </FormItem>

    <FormItem prop="password2">
      <Input type="password" v-model="formInline.password2" placeholder="请确认密码" >
        <Icon type="ios-lock-outline" slot="prepend" ></Icon>
      </Input>
    </FormItem>
  </Form>
</template>

<script>
  import {register} from '../api/api'
    export default {
      name: "secretRegister",
      data() {
        return {
          formInline: {
            password1: '',
            password2: ''
          },
          ruleInline: {
            password1: [
              {required: true, message: '请输入密码', trigger: 'blur'},
              {type: 'string', min: 6, message: '密码长度不能低于6位', trigger: 'blur'}
            ],
            password2: [
              {required: true, message: '请输入密码.', trigger: 'blur'},
              {type: 'string', min: 6, message: '密码长度不能低于6位', trigger: 'blur'}
            ]
          },
        }
      },
      methods: {


        handleSubmit() {

          this.$refs.formInline.validate((valid) => {

            if (valid && this.formInline.password1 === this.formInline.password2) {
              let params = {phone: this.$store.getters.getUser.phone, secret: this.formInline.password1}
              register(params).then(res => {
                if (res.data.status == 200) {
                  this.$emit("sendCode", 300);
                  this.$store.dispatch('asyncUpdateUserPhone', params.phone);
                  this.$store.dispatch('asyncUpdateUserID', res.data.data)
                } else {
                  this.$Message.error("密码设置失败");
                }
              })

            } else {
              this.$Message.error('Fail!');
            }
          })
        },
      }
    }
</script>

<style scoped>

</style>
