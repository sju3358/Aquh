import axios from 'axios';

const accessToken = localStorage.getItem('access_token');

const api = axios.create({
    baseURL: 'https://i9b108.p.ssafy.io/api/v1/symbol',
    headers: { 'Content-Type' : 'application/json'
        ,'AUTH-TOKEN': accessToken }
});


export const symbolList = (member_number) => 
    api.get(`list/${member_number}`);

export const mySymbolList = (member_number) => 
    api.get(`grant/${member_number}`);