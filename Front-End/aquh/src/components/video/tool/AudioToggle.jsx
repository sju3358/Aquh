import classes from "../Chatting.module.css";
import { AiFillAudio, AiOutlineAudioMuted } from "react-icons/ai";

export default function AudioToggle({ audioState = true }) {
  if (audioState) {
    return <AiFillAudio />;
  }
  return <AiOutlineAudioMuted />;
}
