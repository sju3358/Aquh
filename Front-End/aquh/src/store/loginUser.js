import { atom } from "recoil"

export const loginUser = atom({
  key: "loginUser",
  default: {
    access_token: "",
    refresh_token: "",
    member_number : -1,
    is_social_login : false,
  },
});

export default loginUser;