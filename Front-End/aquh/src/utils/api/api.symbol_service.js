import axios from 'axios';

const api = axios.create({
    baseURL: 'http://i9b108.p.ssafy.io:8080/api/v1/symbol',
    headers: { 'Content-Type': 'application/json' }
});


export const symbolList = () => 
    api.get('list');