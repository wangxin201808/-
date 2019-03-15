<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.shippingCode" placeholder="快递单号查询"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="filters.receiverName" placeholder="收件人姓名查询"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="filters.receiverMobile" placeholder="收件人手机号查询"></el-input>
				</el-form-item>

				<el-form-item>
					<el-button type="primary" v-on:click="getOrders">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="orders" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="shippingCode" label="物流单号" width="150" sortable>
			</el-table-column>
			<el-table-column prop="shippingName" label="物流名称" width="150"  sortable>
			</el-table-column>
			<el-table-column prop="title" label="商品名称" width="150"  sortable>
			</el-table-column>
			<el-table-column prop="num" label="数量" width="100"  sortable>
			</el-table-column>
			<el-table-column prop="status" label="交易状态" width="150" :formatter="formatStatus" sortable>
			</el-table-column>
			<el-table-column prop="receiverName" label="收件人" width="120"  sortable>
			</el-table-column>
			<el-table-column prop="receiverMobile" label="手机号" width="150" sortable>
			</el-table-column>
			<el-table-column prop="receiverAddress" label="地址" min-width="180" sortable>
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


	</section>
</template>

<script>
	import util from '../../common/js/util'
	import { getOrderListPage, batchRemoveOrder, addUser } from '../../api/api';

	export default {
		data() {
			return {
				filters: {
					shippingCode: '',
					receiverName: '',
					receiverMobile: ''
				},
				orders: [],
				total: 0,
				page: 1,
				listLoading: false,
				sels: [],//列表选中列



			}
		},
		methods: {
			//状态显示转换
			formatStatus: function (row, column) {
				switch (row.status) {
					case 2: return '已付款';
					case 3: return '未发货';
					case 4: return '已发货';
					case 5: return '交易成功';
					default: return '未知';
				}
			},

			// 日期格式转换
			formatBirth: function (row, column) {
				return util.formatDate.format(row.birth, false)
			},
			// 页面跳转
			handleCurrentChange(val) {
				this.page = val;
				this.getOrders();
			},
			//获取订单列表
			getOrders() {
				let para = {
					page: this.page-1,
					length: 10,
					shippingCode: this.filters.shippingCode,
					receiverName: this.filters.receiverName,
					receiverMobile: this.filters.receiverMobile
				};
				this.listLoading = true;
				getOrderListPage(para).then((res) => {
					this.total = res.data.data.total;
					this.orders = res.data.data.orders;
					this.listLoading = false;
				});
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					let para = { ids: row.orderId };
					console.log(para.ids);
					batchRemoveOrder(para).then((res) => {
						this.listLoading = false;
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getOrders();
					});
				}).catch(() => {

				});
			},
			//显示编辑界面
			handleEdit: function (index, row) {
				console.log(row);
				this.editForm = Object.assign({}, row);
				this.$router.push({name:'订单修改', params:{data: this.orders}});
			},
			// 多选
			selsChange: function (sels) {
				console.log(sels);
				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
				var ids = this.sels.map(item => item.orderId).toString();
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;

					let para = { ids: ids };
					batchRemoveOrder(para).then((res) => {
						this.listLoading = false;

						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getOrders();
					});
				}).catch(() => {

				});
			}
		},
		mounted() {
			this.getOrders();
		}
	}

</script>

<style scoped>

</style>