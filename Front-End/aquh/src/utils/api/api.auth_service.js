
import https from '../https';

// const api = axios.create({
//     baseURL: 'https://i9b108.p.ssafy.io/api/v1/member',
//     headers: { 'Content-Type': 'application/json' }
// });
// export default api;

// export const login = ({ email, password }) =>
//     api.post('auth', { member_email: email, member_password: password });

// export const loginGoogle = ({ code }) =>
//     api.post('auth/google', { code });

// export const loginNaver = ({ code, state }) =>
//     api.post('auth/naver', { code, state });

// // TODO: get documentation for this and adjust accordingly.
// export const refreshToken = () =>
//     api.post('auth/refresh');

// TODO: get documentation for this and adjust accordingly.
export const logout = () =>
    https.delete(`/api/v1/member/auth`)

export const fetchSingleUser = () => 
    https.get(`/api/v1/member/record`)

export const fetchFeedListByUser = () => 
    https.get(`/api/v1/feed`)