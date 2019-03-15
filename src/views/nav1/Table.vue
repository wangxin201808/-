<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="按照姓名查询"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="filters.phone" placeholder="按照手机号查询"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getUsers">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="name" label="姓名" width="120" sortable>
			</el-table-column>
			<el-table-column prop="sex" label="性别" width="100" :formatter="formatSex" sortable>
			</el-table-column>
			<el-table-column prop="birth" label="生日" width="120" :formatter="formatBirth" sortable>
			</el-table-column>
			<el-table-column prop="phone" label="手机号" width="150" sortable>
			</el-table-column>
			<el-table-column prop="email" label="邮箱" width="180" sortable>
			</el-table-column>
			<el-table-column prop="address" label="地址" min-width="180" sortable>
			</el-table-column>
			<el-table-column label="操作" width="150">
				<template scope="scope">
					<el-button type="info" size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total" style="float:right;">
			</el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="姓名" prop="name">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别" >
					<el-radio-group v-model="editForm.sex">
						<el-radio class="radio" :label="1">男</el-radio>
						<el-radio class="radio" :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="生日" prop="birth">
					<el-date-picker type="date" placeholder="选择日期" v-model="editForm.birth" @click=""></el-date-picker>
				</el-form-item>
				<el-form-item label="手机号" prop="phone">
					<el-input v-model="editForm.phone"></el-input>
				</el-form-item>
				<el-form-item label="邮箱" prop="email">
					<el-input v-model="editForm.email" ></el-input>
				</el-form-item>
				<el-form-item label="地址">
					<el-input type="textarea" v-model="editForm.address"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--新增界面-->
		<el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<el-form-item label="姓名" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别" >
					<el-radio-group v-model="addForm.sex">
						<el-radio class="radio" :label="1">男</el-radio>
						<el-radio class="radio" :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="生日" prop="birth">
					<el-date-picker type="date" placeholder="选择日期" v-model="addForm.birth"></el-date-picker>
				</el-form-item>
				<el-form-item label="手机号" prop="phone">
					<el-input v-model="addForm.phone"></el-input>
				</el-form-item>
				<el-form-item label="邮箱" prop="email">
					<el-input v-model="addForm.email" ></el-input>
				</el-form-item>
				<el-form-item label="地址">
					<el-input type="textarea" v-model="addForm.address"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
	import util from '../../common/js/util'
	import { getUserListPage, batchRemoveUser, addUser } from '../../api/api';

	export default {
		data() {
			var addCheckPhone = (rule, value, callback) => {
				if (value === '' || value === undefined) {
					callback(new Error('请输入手机号码'));
				} else if(!(/^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/.test(this.addForm.phone))){
					callback(new Error("手机号码有误，请重输入"));
				} else {
					callback()
				}
			};

			var editCheckPhone = (rule, value, callback) => {
				if (value === '' || value === undefined) {
					callback(new Error('请输入手机号码'));
				} else if(!(/^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/.test(this.editForm.phone))){
					callback(new Error("手机号码有误，请重输入"));
				} else {
					callback()
				}
			};
			return {
				filters: {
					name: '',
					phone: ''
				},
				users: [],
				total: 0,
				page: 1,
				listLoading: false,
				sels: [],//列表选中列

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					name: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					],
					birth: [
						{ type: 'date', required: true, message: '请输入生日', trigger: 'blur'}
					],
					phone: [
						{ validator: editCheckPhone, trigger: 'blur'}
					],
					email: [
						{ required: true, message: '请输入邮箱', trigger: 'blur'},
						{ type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
					]
				},
				//编辑界面数据
				editForm: {
					name: '',
					sex: -1,
					birth: '',
					phone: '',
					email: '',
					address: ''
				},

				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					name: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					],
					birth: [
						{ type: 'date', required: true, message: '请输入生日', trigger: 'blur'}
					],
					phone: [
						{ validator: addCheckPhone, trigger: 'blur'}
					],
					email: [
						{ required: true, message: '请输入邮箱', trigger: 'blur'},
						{ type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
					]

				},
				//新增界面数据
				addForm: {
					name: '',
					sex: -1,
					birth: '',
					phone: '',
					email: '',
					address: ''
				}

			}
		},
		methods: {
			//性别显示转换
			formatSex: function (row, column) {
				return row.sex == 1 ? '男' : row.sex == 0 ? '女' : '未知';
			},

			// 日期格式转换
			formatBirth: function (row, column) {
				return util.formatDate.format(row.birth, false)
			},

			handleCurrentChange(val) {
				this.page = val;
				this.getUsers();
			},
			//获取用户列表
			getUsers() {
				let para = {
					page: this.page-1,
					length: 10,
					name: this.filters.name,
					phone: this.filters.phone
				};
				this.listLoading = true;
				//NProgress.start();
				getUserListPage(para).then((res) => {
					console.log(res);
					this.total = res.data.total;
					this.users = res.data.users;
					this.listLoading = false;
					//NProgress.done();
				});
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { ids: row.id };
					console.log(para.id);
					batchRemoveUser(para).then((res) => {
						this.listLoading = false;
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getUsers();
					});
				}).catch(() => {

				});
			},
			//显示编辑界面
			handleEdit: function (index, row) {
				this.editFormVisible = true;
				row.sex = parseInt(row.sex);
				this.editForm = Object.assign({}, row);
				console.log(this.editForm);
			},
			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {
					name: '',
					sex: -1,
					age: '',
					birth: '',
					addr: ''
				};
			},
			//编辑
			editSubmit: function () {
				this.editForm.birth = Math.round(new Date(this.editForm.birth).getTime())
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.editForm);
							para.birth = util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							console.log("edit", para);
							addUser(para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getUsers();
							});
						});
					}
				});
			},
			//新增
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let user = Object.assign({}, this.addForm);
							user.birth = util.formatDate.format(new Date(user.birth), 'yyyy-MM-dd');
							console.log(user);
							addUser(user).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.getUsers();
							});
						});
					}
				});
			},
			selsChange: function (sels) {
				console.log(sels);
				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
				var ids = this.sels.map(item => item.id).toString();
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;

					let para = { ids: ids };
					console.log(JSON.stringify(para) );
					batchRemoveUser(para).then((res) => {
						this.listLoading = false;

						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getUsers();
					});
				}).catch(() => {

				});
			}
		},
		mounted() {
			this.getUsers();
		}
	}

</script>

<style scoped>

</style>