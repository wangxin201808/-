<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="商品名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getFurnitures">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="furnitures" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;" ref="furnitures">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="name" label="商品名称" width="150" sortable>
      </el-table-column>
      <el-table-column prop="oldPrice" label="原价" width="100" sortable>
      </el-table-column>
      <el-table-column prop="price" label="现价" width="100" sortable>
      </el-table-column>
      <el-table-column prop="parameter" label="参数" width="180" sortable>
      </el-table-column>
      <el-table-column prop="feature" label="内部材质" width="120" :formatter="formatFeature" sortable>
      </el-table-column>
      <el-table-column prop="sort" label="分类" min-width="100" sortable>
      </el-table-column>
      <el-table-column prop="style" label="风格" min-width="100" sortable>
      </el-table-column>
      <el-table-column prop="img1" label="图片1" min-width="250" align="center">
        <template slot-scope="scope">
            <img v-for="it in scope.row.images" :src="it" width="70" height="70" style="margin: 5px"/>

        </template>
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
      <el-button type="danger" @click="batchHot" :disabled="this.sels.length===0">设为热销商品</el-button>
      <el-button type="danger" @click="batchRecommend" :disabled="this.sels.length===0">设为默认推荐商品</el-button>
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total" style="float:right;">
      </el-pagination>
    </el-col>

  </section>
</template>

<script>
  import { getCommodityListPage, batchRemoveCommodity, batchSetRecommend } from '../../api/api';

  export default {
    data() {
      return {

        filters: {
          name: ''
        },
        furnitures: [],
        total: 0,
        page: 1,
        listLoading: false,
        sels: [],//列表选中列



      }
    },
    methods: {
      //内部材质显示转换
      formatFeature: function (row, column) {
        return row.feature == 0 ? '实木' : row.sex == 1 ? '金属' : '板材';
      },

      handleCurrentChange(val) {
        this.page = val;
        this.getFurnitures();
      },
      //获取商品列表
      getFurnitures() {
        let para = {
          page: this.page-1,
          length: 10,
          name: this.filters.name
        };
        this.listLoading = true;
        //NProgress.start();
        getCommodityListPage(para).then((res) => {
          this.total = res.data.data.total;
          var obj = res.data.data.furnitures;
          let img = [];
          for(var i = 0, len = obj.length; i < len; i ++ ) {
            img.push(obj[i].img1);
            img.push(obj[i].img2);
            img.push(obj[i].img3);
            img.push(obj[i].img4);
            obj[i].images = img;
            img = [];
          }
          this.furnitures = obj;
          this.listLoading = false;
          //NProgress.done();
        });
      },
      //删除
      handleDel: function (index, row) {
        this.$confirm('确认删除该记录吗?', '提示', {
          type: 'warning'
        }).then(() => {
          // this.listLoading = true;
          //NProgress.start();
          let para = { ids: row.id };
          console.log(para.ids);
          batchRemoveCommodity(para).then((res) => {
            this.listLoading = false;
            this.$message({
              message: '删除成功',
              type: 'success'
            });
            this.getFurnitures();
          });
        }).catch(() => {

        });
      },
      //显示编辑界面
      handleEdit: function (index, row) {
        this.editForm = Object.assign({}, row);
        this.$router.push({name:'商品上架', params: {data: this.editForm}});
      },
      //显示新增界面
      handleAdd: function () {
        this.$router.push({path: '/addcommodity/:data'})
      },
      // 多选数据
      selsChange: function (sels) {
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
          batchRemoveCommodity(para).then((res) => {
            this.listLoading = false;

            this.$message({
              message: '删除成功',
              type: 'success'
            });
            this.getFurnitures();
          });
        }).catch(() => {

        });
      },
      // 设置为热销商品
      batchHot: function() {
        var ids = this.sels.map(item => item.id).toString();
        this.$confirm('确认将选中记录设为热门商品吗？', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;

          let para = { ids: ids, type: 0 };
          batchSetRecommend(para).then((res) => {
            this.listLoading = false;

            this.$message({
              message: '设置成功',
              type: 'success'
            });
            // 清除选择
            this.$refs.furnitures.clearSelection();
          });
        }).catch(() => {

        });
      },
      // 设置为推荐商品
      batchRecommend: function () {
        var ids = this.sels.map(item => item.id).toString();
        this.$confirm('确认将选中记录设为默认推荐商品吗？', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;

          let para = { ids: ids, type: 1 };
          batchSetRecommend(para).then((res) => {
            this.listLoading = false;

            this.$message({
              message: '设置成功',
              type: 'success'
            });
            // 清除选择
            this.$refs.furnitures.clearSelection();
          });
        }).catch(() => {

        });
      }

    },
    mounted() {
      this.getFurnitures();

    }
  }

</script>

<style scoped>

</style>