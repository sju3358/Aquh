import axios from "axios";

import loginUser from '../store/loginUser';
import { useRecoilValue } from "recoil";

let instance = axios.create({
  baseURL: "http://i9b108.p.ssafy.io",
  headers: "'Content-Type': 'application/json'",
});

instance.interceptors.request.use((config) => {
  const loginData = useRecoilValue(loginUser);
  config.headers["AUTH-TOKEN"] = loginData.access_token;
  return config;
});
export default instance;
