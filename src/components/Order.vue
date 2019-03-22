<template>
  <div class="wrap">
    <Table size="large" stripe :columns="columns1" :data="data1"
           @on-select="funOne" @on-select-cancel="funOneCancle"
           @on-select-all="funAll" @on-select-all-cancel="funAllCancle">
      <template slot-scope="{ row }" slot="post">

        <Button type="primary" @click="find(row.shippingCode)">快递定位</Button>
      </template>
    </Table>

    <Page :total="dateTotal" :page-size="pageSize" :current="current" show-total @on-change="changePage"/>


  </div>
</template>

<script>
  import {order} from "../api/api";

  export default {
    name: "Order",
    data () {
      return {
        // 选中项
        arr : [],
        // 统计已选个数
        total: 0,

        // 统计总价
        sum: 0,

        // 统计所有信息条数

        // 总页数
        dateTotal: 0,
        // 每页显示的条数
        pageSize: 10,
        // 当前页
        current: 1,

        columns1: [

          {
            type: 'selection',
            width: 60,
            align: 'center',
          },
          {
            title: '图片',
            key: 'picPath',
            align: 'center',
            render: (h, params) => {
              return h('img', {
                style: {//设置样式
                  width: '100px',
                  'height': '100px',
                  'border-radius': '5%',
                  'line-height': '80px'
                },
                attrs: {//设置属性
                  src: params.row.picPath
                }
              });
            }
          },
          {
            title: '商品名称',
            key: 'title'
          },
          {
            title: '单价',
            key: 'price'
          },
          {
            title: '数量',
            key: 'num'
          },
          {
            title: '总价',
            key: 'totalFee'
          },
          {
            title: '收货地址',
            key: 'receiverAddress',
            width: 150,
            align: 'center',
          },
          {
            title: '快递查询',
            key: 'post',
            width: 150,
            align: 'center',
            slot: "post"
          }
        ],
        data1: [],
      }
    },
    mounted() {
      console.log(this.$route.params.id);
      this.getOrders();
    },
    methods: {
      /*
      * 获取表格信息
      * selection：所有已选项
      * row: 刚选
      * */
      funOne(selection , row){
        this.summation(selection);
      },
      funOneCancle(selection , row){
        this.summation(selection);
      },
      funAll(selection , row){
        this.summation(selection);
      },
      funAllCancle(selection , row){
        this.summation(selection);
      },
      //  计算已选项之和
      summation(selection) {
        this.total = selection.length;

        let that = this;
        this.sum = 0;
        selection.forEach(function (item) {
          console.log(item)
          that.sum += item.price * item.amount;
        })
      },
      remove(index) {
        this.data.splice(index, 1);
      },
      changePage(index) {
        this.current = index;
        this.getOrders()
      },
      getOrders () {
        let params = {
          page: this.current - 1,
          length: this.pageSize,
          userId: this.$store.getters.getUser.id
        }
        order(params).then( res => {
          console.log("res", res);
          this.data1 = res.data.data.orders;
          this.dateTotal = res.data.data.total;
        })
      },
      find(id) {
        console.log(id);
        let routeUrl = this.$router.resolve({
          name: "Pay",
          params: {id:id}
        });
        window.open(routeUrl .href, '_blank');
      }
    },
    computed: {

    }
  }
</script>

<style scoped>
  .wrap {
    width: 1300px;
    margin: 10px auto;
    text-align: center;
  }
  .bg-grey {
    background: #dcdee2;
    margin-top: 30px;
    padding: 10px 0;
  }
  .span {
    display: inline-block;
    margin-right: 15px;
  }
</style>
