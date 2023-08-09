// import axios from 'axios';
import https from '../https';

// const accessToken = localStorage.getItem('access_token');

// const api = axios.create({
//     baseURL: 'https://i9b108.p.ssafy.io/api/v1/symbol',
//     headers: { 
//         'Content-Type' : 'application/json' }
// });


export const symbolList = (member_number) => 
    https.get(`api/v1/symbol/list/${member_number}`);

export const mySymbolList = (member_number) => 
    https.get(`api/v1/symbol/grant/${member_number}`);