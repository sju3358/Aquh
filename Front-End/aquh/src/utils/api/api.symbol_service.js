import axios from 'axios';

const api = axios.create({
    baseURL: 'https://i9b108.p.ssafy.io/api/v1/symbol',
    headers: { 'Content-Type': 'application/json' }
});


export const symbolList = () => 
    api.get('list');