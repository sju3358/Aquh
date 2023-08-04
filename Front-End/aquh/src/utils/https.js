import axios from "axios";

let instance = axios.create({
  baseURL: "http://i9b108.p.ssafy.io:8080",
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
//    (response) => {
//     const { config } = response;
//     const originalRequest = config;

//     if (response?.data?.code == 401) {
//       const [loginData, setLoginData] = useRecoilState(loginUser);

//       return  instance.post(`/api/v1/member/auth/refresh_token`,{
//         params: {},
//         headers : {
//           'Content-Type': 'application/json',
//           'AUTH-TOKEN' : loginData.refreshToken,
//         }
//       })
//       .then( (res) => {
//           if (res.status === 200) {

//             setLoginData({
//               refreshToken: res.data.data.refresh_token,
//               accessToken : res.data.data.access_token,
//               memberNumber : res.data.data.member_number,
//               isSocialLogin : res.data.data.is_social_login,
//             });

//             originalRequest.headers.Authorization = `${res.data.data.access_token}`;
//             return axios(originalRequest);
//           }

//         }).catch((err) => {
//           console.log(err)
//         })
//     }

//     return response;
//   },
//    (error) => {

//     console.log(error, '^^***')
//     throw error

//   });

export default instance;
