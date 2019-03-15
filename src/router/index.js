
import MyMain from "../components/MyMain"
import Search from "../components/Search"
import Detail from "../components/detail"
import ShopCart from "../components/ShopCart"
import Login from "../components/login"
import Register from "../components/register"
import Profile from "../components/profile"
import Order from "../components/Order"
import Pay from "../components/Pay"


let routes = [
    {
      path: "/",
      name: 'MyMain',
      component: MyMain
    },
    {
      path: "/search/:id",
      name: "Search",
      component: Search,
      children: [
      ]
    },
    {
      path: '/detail/:id',
      name: "Detail",
      component: Detail
    },
    {
      path: '/shopcart',
      name: "ShopCart",
      component: ShopCart
    },
    {
      path: '/login',
      name: "Login",
      component: Login
    },
    {
      path: '/register',
      name: "Register",
      component: Register
    },
    {
      path: '/profile',
      name: "Profile",
      component: Profile
    },
    {
      path: '/order',
      name: "Order",
      component: Order
    },
    {
      path: '/pay/:id',
      name: "Pay",
      component: Pay
    }
  ];

export default routes;
