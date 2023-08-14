import { atom } from "recoil";
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist({
  key: 'bubbleState',
  storage: localStorage,
});

export const bubbleNumberState = atom({
  key: "bubble_number",
  default: "",
  effects_UNSTABLE: [persistAtom],
});
