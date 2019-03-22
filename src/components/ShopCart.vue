<template>
    <div class="wrap">
      <Table size="large" stripe :columns="columns1" :data="data1"
             @on-select="funOne" @on-select-cancel="funOneCancle"
             @on-select-all="funAll" @on-select-all-cancel="funAllCancle" @on-current-change="currentChange">
        <template slot-scope="{ row, index }" slot="count">
          <el-input-number v-model="row.num" @change="handleChange(row)" :min="1" label="描述文字" size="small"></el-input-number>
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="error" size="small" @click="remove(row)">删除</Button>
        </template>
      </Table>

      <Page :total="dataCount" :page-size="pageSize" show-total @on-change="changePage"/>

      <Row class="bg-grey">
        <!--<Col span="2"class="test"><Button type="error" @click="removeAll">删除已选项</Button></Col>-->
        <Col span="4" :offset="20">
          <span class="span">选中数{{total}}， 总价：{{sum}}</span>
          <Button type="warning" size="large" @click="payMoney" :disabled="isShow">结算</Button>
        </Col>
      </Row>

    </div>
</template>

<script>
  import {getCarList, delCar, pay} from '../api/api'
  import Utils from '../common/js/util'
    export default {
        name: "ShopCart",
      data () {
        return {
          // 选中项
          arr : [],
          // 统计已选个数
          total: 0,

          // 统计总价
          sum: 0,

          // 统计商品名称
          names: [],
          // 是否显示结算，true 不显示， false 显示
          isShow: true,

          // 统计所有信息条数
          dataCount: 0,
          pageSize: 10,

          columns1: [

            {
              type: 'selection',
              width: 60,
              align: 'center',
            },
            {
              title: '图片',
              key: 'img',
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
                    src: params.row.img
                  }
                });
              }
            },
            {
              title: '商品名称',
              key: 'name',
              align: "center"
            },
            {
              title: '单价',
              key: 'price',
              align: "center",
              "sortable": true
            },
            {
              title: '数量',
              key: 'num',
              align: "center",
              slot: 'count'
            },
            {
              title: '操作',
              key: 'action',
              width: 150,
              align: 'center',
              slot: 'action'
            }
          ],
          data1: [],
          dataSource: []
        }
      },
      mounted() {
        if (this.$store.getters.getUser.id == '') {
          this.$Message.error({
            content: '您还没有登录，请您登录',
            duration: 5
          })
          this.$router.push({name: 'Login'})
        }
        this.getList();
      },
      methods: {
          /*
          * 获取表格信息
          * selection：所有已选项
          * row: 刚选
          * */
        currentChange(currentRow, oldCurrentRow) {
          console.log(currentRow, oldCurrentRow);
        },
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

          let temp = [];
          let that = this;
          this.sum = 0;
          selection.forEach(function (item) {
            console.log(item)
            temp.push(item.id)
            that.sum += item.price * item.num;
            that.names.push(item.name);
          })
          // 总资金大于0可以结算
          if (this.sum > 0) {
            this.isShow = false;
          }
          this.arr = temp;
          console.log(this.arr.toString());
        },
        remove(row) {
          console.log("删除", row);
          let params = {'id': row.id, 'uId': this.$store.getters.getUser.id}
          delCar(params).then( res => {
            console.log(res);
            this.getList();
          })
        },
        changePage(index) {
          console.log(this.data1);
          let start = (index - 1)* this.pageSize;
          let end = index * this.pageSize;
          this.data1 = this.dataSource.slice(start, end);
        },

        handleChange(row) {
          // 更新当前值
          this.dataSource.forEach(item => {
            if (item.id == row.id) {
              item.num = row.num;
            }
          });
          // 计算属性
          // this.summation();
        },

        // 获取购物车数据
        getList() {
          let params = {'uId': this.$store.getters.getUser.id}
          getCarList(params).then( res => {
            let data = res.data.data

            console.log("shopcarres", data);
            // 暂存得到的数据
            let temp = [];
            for (var i = 0; i < data.length; i++) {
              console.log(`data[${i}]`, data[i]);
              let obj = {};

              obj.num = data[i].num;
              obj.id = data[i].infoById.id;
              obj.name = data[i].infoById.name;
              obj.img = data[i].infoById.img1;
              obj.price = data[i].infoById.price;
              temp.push(obj);
            }
            this.dataSource = temp;
            console.log("dataSource", this.dataSource);
            // 计算总条数
            this.dataCount = this.dataSource.length;
            // 分页
            this.changePage(1);
          })
        },
        payMoney (totalMoney) {
          // this.$router.push({name:'Pay'});
         let params = {
           'itemId': this.arr.toString(),
           'userId': this.$store.getters.getUser.id,
           'WIDout_trade_no': Utils.getTime(),
            'WIDtotal_amount': this.sum,
            // 'WIDtotal_amount': '0.01',
           'WIDsubject': '共计1种商品',
           'WIDbody': this.names.toString()
           // 'WIDbody': '0.01'
         }
         pay(params).then(res => {
           console.log("********************")
           this.form = res.data;
           var strings = this.form.split("\<s");
           this.form = strings[0];
           const div = document.createElement('div')
           div.innerHTML = this.form //此处form就是后台返回接收到的数据
           document.body.appendChild(div);
           document.forms[0].submit();
         })
        }
      },
      computed: {

      },
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
