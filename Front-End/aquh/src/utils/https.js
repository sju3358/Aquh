import axios from "axios";
import { errorSelector } from "recoil";

let instance = axios.create({
  baseURL: "https://i9b108.p.ssafy.io",
});

instance.interceptors.request.use(
  (config) => {
    config.headers["AUTH-TOKEN"] = localStorage.getItem("access_token");
    return config;
  },
  (error) => {
    console.log(error);
    return Promise.reject(error);
  }
);

instance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    const {
      config,
      response: { status },
    } = error;

    const originalRequest = config;

    try {
      if (status === 401) {
        axios
          .post(
            "https://i9b108.p.ssafy.io/api/v1/member/auth/refresh_token",
            {},
            {
              headers: {
                "AUTH-TOKEN": localStorage.getItem("refresh_token"),
              },
            }
          )
          .then((response) => {
            const newAccessToken = response.data.data.access_token;
            const newRefreshToken = response.data.data.refresh_token;

            localStorage.setItem("access_token", newAccessToken);
            localStorage.setItem("refresh_token", newRefreshToken);

            originalRequest.headers = {
              "Content-Type": "application/json",
              "AUTH-TOKEN": newAccessToken,
            };
            return axios(originalRequest);
          })
          .catch((error) => {
            return Promise.reject(error);
          });
      }
    } catch (error) {
      new Error(error);
    }
    return Promise.reject(error);
  }
);

export default instance;
