<template>
	<!--新增界面-->
	<el-row>
		<el-col :offset="3" :span="16">
			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<el-form-item label="商品名称" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="现价" prop="price">
					<el-input v-model="addForm.price" placeholder="请输入价格" style="width: 240px" ></el-input>
				</el-form-item>
				<el-form-item label="原价" prop="oldPrice">
					<el-input v-model="addForm.oldPrice" placeholder="请输入价格" style="width: 240px" ></el-input>
				</el-form-item>
				<el-form-item label="内部材质" prop="feature">
					<el-radio-group v-model="addForm.feature" >
						<el-radio class="radio" :label="0">实木</el-radio>
						<el-radio class="radio" :label="1">金属</el-radio>
						<el-radio class="radio" :label="2">板材</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="分类" prop="sort">
					<el-checkbox-group v-model="addForm.sort" >
						<el-checkbox label="布艺" ></el-checkbox>
						<el-checkbox label="皮艺" ></el-checkbox>
						<el-checkbox label="实木" ></el-checkbox>
						<el-checkbox label="功能" ></el-checkbox>
						<el-checkbox label="懒人" ></el-checkbox>
					</el-checkbox-group>
				</el-form-item>
				<el-form-item label="风格" prop="style">
					<el-checkbox-group v-model="addForm.style">
						<el-checkbox label="欧式" ></el-checkbox>
						<el-checkbox label="现代简约" ></el-checkbox>
						<el-checkbox label="东南亚" ></el-checkbox>
						<el-checkbox label="日韩风格" ></el-checkbox>
						<el-checkbox label="复古怀旧" ></el-checkbox>
					</el-checkbox-group>
				</el-form-item>

				<el-form-item label="图片" prop="img1">
					<el-upload
							class="upload-demo"
							drag
							action="/api/commodity/post/"
							:on-success="handleSuccess"
							:file-list="fileList"
							list-type="picture"
							limit="4"
							multiple>
						<i class="el-icon-upload"></i>
						<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
						<div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
					</el-upload>
				</el-form-item>

				<el-form-item label="参数" prop="parameter">
					<el-input type="textarea" v-model="addForm.parameter"></el-input>
				</el-form-item>
			</el-form>
			<div class="add-footer">
				<el-button @click="resetForm('addForm')">重置</el-button>
				<el-button type="primary" @click.native="addSubmit('addForm')" :loading="addLoading" >
					<span v-if="isAdd">上传商品</span><span v-else>修改商品</span>
				</el-button>
			</div>
		</el-col>
	</el-row>
</template>
<script>
	import { addCommodity } from '../../api/api';
	//import NProgress from 'nprogress'
	export default {

		data() {
			var checkPrice = function (rule, value, callback) {
				let reg = /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/;
				if (value === '' || value === undefined) {
					callback(new Error('请输入价格'));
				}else if(!reg.test(value)) {
					callback(new Error('请输入正确的价格'));
				} else {
					callback();
				}
			}
			var checkOldPrice = function (rule, value, callback) {
				let reg = /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/;
				if (value === '' || value === undefined) {
					callback();
				}else if(!reg.test(value)) {
					callback(new Error('请输入正确的价格'));
				} else {
					callback();
				}
			}

			return {
				// 上传文件的个数
				imgNum: 0,
				// 显示上传的图片
				fileList: [],
				// 判断是否是增加商品，true：增加， false：修改
				isAdd: true,

				addLoading: false,
				addFormRules: {
					name: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					],
					price: [
						{ validator: checkPrice, trigger: 'blur'}
					],
					oldPrice: [
						{ validator: checkOldPrice, trigger: 'blur'}
					],
					feature: [
						{ type: 'number', required: true, message: '请选择内部材质', trigger: 'blur' }
					],
					sort: [
						{ type: 'array', required: true, message: '请选择分类', trigger: 'blur' }
					],
					style: [
						{ type: 'array', required: true, message: '请选择风格', trigger: 'change' }
					],
					img1: [
						{ required: true, message: '请上传图片', trigger: 'blur'}
					],
					parameter: [
						{ required: true, message: '请输入参数', trigger: 'blur' }
					]

				},
				//新增界面数据
				addForm:  {
					name: '',
					price: '',
					oldPrice: '',
					feature: -1,
					sort: [],
					style:[],
					img1: '',
					img2: '',
					img3: '',
					img4: '',
					parameter: '',
				}
			}
		},
		methods: {
			//性别显示转换
			formatFeature: function (row, column) {
				return row.feature == 0 ? '实木' : row.sex == 1 ? '金属' : '板材';
			},
			// 单选框变化
			radioChange(value){
				console.log(value);
			},
			handleSuccess(response, file, fileList){
				console.log(response);
				if (this.imgNum == 4) {
					this.imgNum = 0;
				}
				// 获取图片地址
				switch (this.imgNum) {
					case 0: this.addForm.img1 =response.data.fileName;break;
					case 1: this.addForm.img2 =response.data.fileName;break;
					case 2: this.addForm.img3 =response.data.fileName;break;
					case 3: this.addForm.img4 =response.data.fileName;break;
					default: ;
				}
				this.imgNum ++;
				console.log("addform" , this.addForm);
			},
			// 新增
			addSubmit: function (addForm) {
				this.$refs[addForm].validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							let furniture = Object.assign({}, this.addForm);
							furniture.sort = furniture.sort.toString();
							furniture.style = furniture.style.toString();
							if (furniture.images != null) {
								delete furniture.images;
							}

							var that = this;
							addCommodity(furniture).then((res) => {
								res = res.data;
								if (res.message == "成功"){
									that.addLoading = false;
									that.$message({
										message: '提交成功',
										type: 'success'
									});
									that.$refs['addForm'].resetFields();
									this.fileList = [];
								} else {
									that.$message({
										message: '保存失败',
										type: 'fail'
									});
								}

							});
						});
					}
				});
			},
			// 重置
			resetForm: function (addForm) {
				this.$refs[addForm].resetFields();
				this.fileList = [];
			}
		},
		mounted() {
			this.isAdd = true;
			let content = this.$route.params.data;
			if (content != ":data")  {
				this.isAdd = false;
				let tep = {sort: [], style: [], feature: '',};
				content.sort.split(",").forEach((item, index) => {
					tep.sort.push(item);
				})
				content.style.split(",").forEach((item, index) => {
					tep.style.push(item);
				})
				tep.feature = parseInt(content.feature);

				this.addForm = Object.assign(content, tep);


				for (var item in this.addForm) {
					var i=1;
					if (item.indexOf("img") != -1) {
						this.fileList.push({name: "图片"+i, url: this.addForm[item]});
					};
					++ i;
				}
			}
		}
	};

</script>

<style scoped>
	.add-footer{
		text-align: center;
	}
</style>