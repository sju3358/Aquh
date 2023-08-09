import { atom } from "recoil";
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist({
  key: 'loginUser',
  storage: localStorage,
});

export const memberNumberState = atom({
  key: "memberNumber",
  default: -1,
  effects_UNSTABLE : [persistAtom],
});

export const isSocialLoginState = atom({
  key: "isSocialLogin",
  default: false,
  effects_UNSTABLE : [persistAtom],
});

export default {
  memberNumberState,
  isSocialLoginState,
};
