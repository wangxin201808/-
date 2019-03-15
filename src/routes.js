import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Table from './views/nav1/Table.vue'
import Commodity from './views/nav2/Commodity.vue'
import AddCommodity from './views/nav2/AddCommodity.vue'
import Order from './views/nav3/Order.vue'
import AddOrder from './views/nav3/AddOrder.vue'

let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-address-card',//图标样式class
        leaf: true,//只有一个节点
        children: [
            { path: '/table', component: Table, name: '用户管理' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '商品管理',
        iconCls: 'fa  fa-cubes',
        children: [
            { path: '/commodity', component: Commodity, name: '商品查询' },
            { path: '/addcommodity/:data', component: AddCommodity, name: '商品上架' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '订单管理',
        iconCls: 'fa fa-shopping-cart',
        leaf: false,
        children: [
            { path: '/orders', component: Order, name: '订单查询' },
            { path: '/orders/:data', component: AddOrder, name: '订单修改' }
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;