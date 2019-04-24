import axios from 'axios';
import qs from 'qs'
let base = '/api';

/* login api */
export const requestLogin = params => { return axios.post(`${base}/login`, qs.stringify(params)); };

export const getValidator = params => { return axios.get(`${base}/sendsms`, {params: params}); }

export const loginByPhone = params => { return axios.post(`${base}/loginbyphone`, qs.stringify(params));  }

export const testPhone = params => { return axios.post(`${base}/testphone`, qs.stringify(params));  }

export const register = params => { return axios.post(`${base}/register`, qs.stringify(params));  }

/* userInfo api */
export const getUserInfo = params =>{ return axios.post(`${base}/user/getinfo`, qs.stringify(params)); }

export const setUserInfo = params =>{ return axios.post(`${base}/user/add`, qs.stringify(params)); }

export const resetPassword = params =>{ return axios.post(`${base}/resetpwd`, qs.stringify(params)); }


/* furnitures api */
export const getRecommend = params => { return axios.get(`${base}/commodity/getrecommend`);}

export const getDetail = params => { return axios.get(`${base}/commodity/getdetail`, {params: params})}
/* hot api */
export const hot = () => {return axios.get(`${base}/commodity/gethot`)}
/* new api */
export const news = () => {return axios.get(`${base}/commodity/getnews`)}
/* search api */
export const search = params => { return axios.get(`${base}/commodity/search`, {params: params});}

/* shopcar api */
export const joinCar = params => {return axios.post(`${base}/car/join`, qs.stringify(params)); }

export const getCarList = params => { return axios.get(`${base}/car/pagelist`, {params: params})}

export const delCar = params => { return axios.post(`${base}/car/delete`, qs.stringify(params))}

/* pay api*/
export const pay = params => { return axios.post(`${base}/pay`, qs.stringify(params));}

export const getTradeNo = () => { return axios.get(`${base}/gettradeno`);}

/* order api */
export const order = params => { return axios.get(`${base}/order/listpage`, {params: params})}

/* 快递 api */
export const getKuaidi = params => { return axios.get(`${base}/order/address`, {params: params, headers:{
    'Content-Type': 'application/x-www-from-urlencoded; charset=UTF-8'
  },}) }

/* 评论 api */
export const getPinglun = params => {return axios.get(`${base}/pinglun/getInfo`, {params: params})}

export const savePinglun = params => {return axios.post(`${base}/pinglun/save`, qs.stringify(params))}

