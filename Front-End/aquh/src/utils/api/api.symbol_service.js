import axios from 'axios';

const api = axios.create({
    baseURL: 'https://i9b108.p.ssafy.io:8080/api/v1/symbol',
    headers: { 'Content-Type': 'application/json' }
});


export const symbolList = (member_number) => 
    api.get(`list/${member_number}`);

export const mySymbolList = (member_number) => 
    api.get(`grant/${member_number}`);