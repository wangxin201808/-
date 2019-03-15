<template>
  <div class="wrap">
    <Row>
      <!-- 侧边栏 -->
      <Col span="4" :offset="-8" class="code-row-bg">
        <Menu  active-name="0-1" :open-names="['0']" >
          <Submenu name="0">
            <template slot="title">
              <Icon type="ios-paper" />
              {{sofa.name[0]}}
            </template>
            <MenuItem name="0-1">

              <Input clearable v-model="minValue" placeholder="最低价格" style="width: 100px; margin-bottom: 5px" />
              <Input clearable v-model="maxValue" placeholder="最高价格" style="width: 100px; margin-bottom: 5px" />
              <br/><Button type="primary" @click="getSearch">确定</Button>
            </MenuItem>
          </Submenu>
          <Submenu name="1">
            <template slot="title">
              <Icon type="ios-paper" />
              {{sofa.name[1]}}
            </template>
            <MenuItem v-for="(item,index) in sofa.style" :name="'1-'+index" :key="index" @click.native="changeStyle(item)">{{item}}</MenuItem>
          </Submenu>
          <Submenu name="2">
            <template slot="title">
              <Icon type="ios-paper" />
              {{sofa.name[2]}}
            </template>
            <MenuItem v-for="(item,index) in sofa.sort" :name="item" :key="index" @click.native="changeSort(item)">{{item}}</MenuItem>
          </Submenu>
          <Submenu name="3">
            <template slot="title">
              <Icon type="ios-paper" />
              {{sofa.name[3]}}
            </template>
            <MenuItem v-for="(item,index) in sofa.feature" :name="item" :key="index" @click.native="changeFeature(index)">{{item}}</MenuItem>
          </Submenu>
        </Menu>
      </Col>
      <!-- 内容栏 -->
      <Col span="19">
        <!-- 面包屑 -->
        <Row>
          <Col span="10" justify="start">
            <Breadcrumb>
              <BreadcrumbItem v-if=" style != '' " @click.native="setNull('style')">{{ this.style }}</BreadcrumbItem>
              <BreadcrumbItem v-if=" sort != '' " @click.native="setNull('sort')">{{ this.sort }}</BreadcrumbItem>
              <BreadcrumbItem v-if=" feature != '' || feature === 0" @click.native="setNull('feature')">{{ this.sofa.feature[feature] }}</BreadcrumbItem>
              <BreadcrumbItem v-if=" minValue != '' " @click.native="setNull('minValue')">最低价：{{ this.minValue }}</BreadcrumbItem>
              <BreadcrumbItem v-if=" maxValue != '' " @click.native="setNull('maxValue')">最高价：{{ this.maxValue }}</BreadcrumbItem>
            </Breadcrumb>
          </Col>
        </Row>
        <!-- 商品显示 -->
        <span v-if="values.length != 0">
          <Row  type="flex" justify="space-around" class="code-row-bg" v-for="(rows,index) in 3" :key="index" >
            <Col class="cccc" span="7" v-for="(item,index1) in 3" :key="index1" v-if="values[index * 3 + index1 + currentPage*9]">
              <div v-on:click="showDetail(index * 3 + index1 + currentPage*9)" >

                <img :src="values[index * 3 + index1 + currentPage*9].img1" alt="" width="350" height="304">
                <div><span class="first">￥ {{values[index * 3 + index1 + currentPage*9].price}}</span><span class="second">￥ {{values[index * 3 + index1 + currentPage*9].oldPrice}}</span></div>
                <div>{{values[index * 3 + index1 + currentPage*9].name }}</div>
              </div>
            </Col>
          </Row>
        </span>
        <Row v-else>
          <Col>
            <span class="span">对不起，您需求我们正在备货中，请看看其他商品吧</span>
          </Col>
        </Row>
      </Col>
    </Row>
    <Row>
      <Col span="8" :offset="10">
        <Page :total="total" :page-size="pageSize" @on-change="getPage"/>
      </Col>
    </Row>
  </div>
</template>

<script>
  import {search} from '../api/api'
    export default {
        name: "Search",
      data() {
          return {
            // 内部材质
            feature: '',
            // 分类
            sort: '',
            // 风格
            style: '',
            // 最低价
            minValue: '',
            // 最高价
            maxValue: '',
            // 导航栏
            sofa: {
              name:['价格区间',"风 格",'分 类','内部材质'],
              style: ['欧式','现代简约','东南亚','日韩风格','复古怀旧'],
              sort: ['布艺', '皮艺', '实木', '功能', '懒人'],
              feature: ['实木','金属','板材']
            },
            // 展示的数据
            values: [],
            // 每页显示的条数
            pageSize: 9,
            // 总条数
            total: 0,
            // 当前页
            currentPage: 0
          }
      },
      methods: {
        changeStyle(index) {
          this.style = index;
          console.log("style", this.style);
          this.getSearch();
        },
        changeSort(index) {
          this.sort = index;
          console.log("sort", this.sort);
          this.getSearch();
        },
        changeFeature(index) {
          this.feature = index;
          console.log("feature", this.feature);
          this.getSearch();
        },
        getPage (page) {
          console.log(page);
          this.currentPage = parseInt(page-1);
        },

        showDetail(index) {
          this.$router.push({name: 'Detail', params: {id: this.values[index].id} })
        },
        getSearch() {
          let name = this.$route.params.id
          let indexOf = name.indexOf("桌");
          if (indexOf != -1) {
             name = "桌"
          }
          let indexOf1 = name.indexOf("椅");
          if (indexOf1 != -1) {
            name = "椅"
          }
          let params = {
            'name': name,
            'feature': this.feature,
            'sort':this.sort,
            'style': this.style,
            'lowPrice': this.minValue,
            'highPrice': this.maxValue
          }
          search(params).then( res => {
            console.log(res);
            this.values = res.data.data;
            this.total = this.values.length;
          })
        },
        setNull (params) {
          console.log("setnull", this[params]);
          this[params] = '';
          this.getSearch();
        }
      },
      watch: {
        $route(){
          let search = this.$route.params.id;
          console.log("search12121212", this.$route.params.id);
          this.getSearch();
        },
      //   maxValue() {
      //     this.getSearch();
      //   }
      },
      mounted() {
        console.log("search", this.$route.params.id);
        this.getSearch();
      },
    }
</script>

<style scoped>
  .wrap {
    text-align: center;
    margin-bottom: 20px;
  }
  .code-row-bg{
    margin: 10px 0;
  }
  .cccc {
    background: #e8eaec;
    border-radius: 5px;
    overflow: hidden;
  }

  .first{
    font-size: larger;
    font-weight: bolder;
  }
  .second {
    text-decoration: line-through;
    font-size: small;
  }
  .span {
    color: #c5c8ce;
    font-size: xx-large;
    display: inline-block;
    margin-top: 150px;
  }
</style>
