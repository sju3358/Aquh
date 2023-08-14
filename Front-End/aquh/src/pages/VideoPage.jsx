// 0807 김재원 작성
// react를 매우 못하므로 추후수정 필요
import Video from "../components/video/Chatting"
import classes from "./VideoPage.module.css";

export default function VideoPage() {

  return (
    <div className={classes.container}>
      <p className={classes.video}>
        <Video />
      </p>
    </div>
  );
}
