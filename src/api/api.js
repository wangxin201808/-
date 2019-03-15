import axios from 'axios';
import qs from 'qs'
let base = '/api';


export const requestLogin = params => { return axios.post(`${base}/login`, qs.stringify(params)); };

/*    user api      */
export const getUserListPage = params => { return axios.get(`${base}/user/listpage`, { params: params }); };

export const addUser = params => { return axios.post(`${base}/user/add`, qs.stringify(params)); };

export const batchRemoveUser = params => { return axios.post(`${base}/user/batchremove`, qs.stringify(params)); };


/*    Commodity api      */
export const getCommodityListPage = params => { return axios.get(`${base}/commodity/listpage`, { params: params }); };

export const addCommodity = params => { return axios.post(`${base}/commodity/add`, qs.stringify(params)); };

export const batchRemoveCommodity = params => { return axios.post(`${base}/commodity/batchremove`, qs.stringify(params)); };

export const batchSetRecommend = params => { return axios.post(`${base}/commodity/setrecommend`, qs.stringify(params)); };

/*    order api      */
export const getOrderListPage = params => { return axios.get(`${base}/order/listpage`, { params: params }); };

export const editOrder = params => { return axios.post(`${base}/order/edit`, qs.stringify(params )); };

export const batchRemoveOrder = params => { return axios.post(`${base}/order/batchremove`, qs.stringify(params));};

