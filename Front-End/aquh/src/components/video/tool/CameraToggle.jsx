import classes from "../Chatting.module.css";
import { BsFillCameraVideoFill, BsCameraVideoOff } from "react-icons/bs";

export default function CameraToggle({ cameraState = true }) {
  if (cameraState) {
    return <BsFillCameraVideoFill className={classes.icon} />;
  }
  return <BsCameraVideoOff className={classes.icon} />;
}
