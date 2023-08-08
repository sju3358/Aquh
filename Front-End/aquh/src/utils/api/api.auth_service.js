import axios from 'axios';

const api = axios.create({
    baseURL: 'https://localhost:8080/api/v1/member',
    headers: { 'Content-Type': 'application/json' }
});
export default api;

export const login = ({ email, password }) =>
    api.post('auth', { member_email: email, member_password: password });

export const loginGoogle = ({ code }) =>
    api.post('auth/google', { code });

export const loginNaver = ({ code, state }) =>
    api.post('auth/naver', { code, state });

// TODO: get documentation for this and adjust accordingly.
export const refreshToken = () =>
    api.post('auth/refresh');

// TODO: get documentation for this and adjust accordingly.
export const logout = (memberNumber) =>
    api.delete(`auth/${memberNumber}`);