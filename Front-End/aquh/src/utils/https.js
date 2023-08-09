import axios from "axios";

let instance = axios.create({
  baseURL: "https://i9b108.p.ssafy.io",
  headers: {
    "Content-Type": "application/json",
  },
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

// instance.interceptors.response.use(
//   (response) => {
//     const { config } = response;
//     const originalRequest = config;

//     if (response?.data?.code == 401) {
//       return instance
//         .post(`/api/v1/member/auth/refresh_token`, {
//           params: {},
//           headers: {
//             "Content-Type": "application/json",
//             "AUTH-TOKEN": localStorage.getItem("refresh_token"),
//           },
//         })
//         .then((res) => {
//           if (res.status === 200) {
           // 여기 뭔가 코드가 있었는데 지워진것 같습니다.
            
//               refreshToken: res.data.data.refresh_token,
//               accessToken: res.data.data.access_token,
//               memberNumber: res.data.data.member_number,
//               isSocialLogin: res.data.data.is_social_login,
//             });

//             originalRequest.headers.Authorization = `${res.data.data.access_token}`;
//             return axios(originalRequest);
//           }
//         })
//         .catch((err) => {
//           console.log(err);
//         });
//     }

//     return response;
//   },
//   (error) => {
//     throw error;
//   }
// );

export default instance;
