import { atom } from "recoil";

export const memberTypeState = atom({
  key: "member_type",
  default: "",
});

export const memberNicknameState = atom({
  key: "member_nickname",
  default: "",
});
export const memberIntroState = atom({
  key: "member_intro",
  default: "",
});

export const memberEmailState = atom({
  key: "member_email",
  default: "",
});

export default {
  memberTypeState,
  memberNicknameState,
  memberIntroState,
  memberEmailState,
};
