import React, { useEffect, useState } from "react";
import classes from "./FeedCard.module.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import FeedPage from "../../pages/FeedPage";
import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";
import FeedModal from "./FeedModal";

function FeedCard({
  level,
  title,
  content,
  createDate,
  inputImg,
  feedCreatorNumber,
}) {
  // 글 상세보기 페이지 open
  const [modalOpen, setModalOpen] = useState(false);

  // 캐릭터 레벨에 따른 사진 보여주기
  const charImg = () => {
    if (level === "1") {
      <img src='./baby-image.png' alt='레벨1캐릭터' />;
    } else if (level === "2") {
      <img src='./second-image.png' alt='레벨2캐릭터' />;
    } else if (level === "3") {
      <img src='./third-image.png' alt='레벨3캐릭터' />;
    }
  };

  return (
    <div className={classes.FeedCard}>
      {/* TODO : 카드를 클릭하면 모달창으로 상세페이지 설정 */}
      {charImg}
      <h3 className={classes.feedTitle}>제목 : {title}</h3>
      <p className={classes.creator_nickname}>닉네임 :</p>
      <p className={classes.feedContent}>내용: {content}</p>
      <p className={classes.feedWriteTime}>작성 시간 : {createDate}</p>
      {/* TODO : 생성일 0분전으로 바꾸는 로직 */}
      {inputImg && (
        // TODO : 이미지 null값일때는 아예 안보이게 수정
        <img
          src={`https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/${inputImg}`}
          alt=''
        />
      )}
    </div>
  );
}
export default FeedCard;
