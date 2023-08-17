import classes from "./FeedCard.module.css";
import { useNavigate } from "react-router-dom";
import FeedPage from "../../pages/FeedPage";
import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";
import FeedModal from "./FeedModal";
// import FeedModal from "./FeedModalPage";
import Modal from "react-modal";
import https from "../../utils/https";

import React, { useState } from "react";
import emptyHeart from "../../assets/emptyHeart.png";
import fullHeart from "../../assets/fullHeart.png";

function FeedCard({
  feedTitle,
  feedContent,
  feedCreateDate,
  feedImage,
  feedNumber,
  setModalOpen,
  userNickName,
  feedLevel,
}) {
  const [liked, setLiked] = useState(false);
  const toggleLike = () => {
    setLiked(!liked);
  };

  // 글 상세보기 modal 오픈, 글 불러오기
  const openModal = () => {
    localStorage.setItem("feedNumber", feedNumber);
    setModalOpen(true);
  };

  // =========================================================
  return (
    <div className={classes.FeedCard} onClick={openModal}>
      {/* TODO : 카드를 클릭하면 모달창으로 상세페이지 설정 */}

      {/* {charImg()} */}
      <p className={classes.creator_nickname}>{userNickName} </p>
      <h3 className={classes.feedTitle}>{feedTitle}</h3>
      <p className={classes.feedCreateDate}>작성 날짜 : {feedCreateDate}</p>
      {/* TODO : 생성일 0분전으로 바꾸는 로직 */}
      <img src={`../../pfp${feedLevel}.png`} className={classes.profileImg} />
      <p className={classes.feedContent}> {feedContent}</p>
      <button className={classes.likeButton} onClick={toggleLike}>
        <img
          src={liked ? fullHeart : emptyHeart}
          alt={liked ? "full_heart" : "empty_heart"}
        />
      </button>

      {/* <FaRegCalendarCheck /> */}
      {/* TODO : 내용 일부만(3줄) 보이게 수정->css */}

      {feedImage && (
        <img
          className={classes.feedCardImg}
          src={`${feedImage}`}
          alt="img_null"
        />
      )}
    </div>
  );
}
export default FeedCard;
