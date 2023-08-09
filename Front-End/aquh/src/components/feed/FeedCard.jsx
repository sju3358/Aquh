import React, { useEffect, useState } from "react";
import classes from "./FeedCard.module.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import FeedPage from "../../pages/FeedPage";
import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";
import FeedModal from "./FeedModal";
import Modal from "react-modal";

function FeedCard({ level, title, content, createDate, inputImg, feedNumber }) {
  // 캐릭터 레벨에 따른 사진 보여주기
  // const charImg = () => {
  //   if (level === "1") {
  //     return <img src='./baby-image.png' alt='레벨1캐릭터' />;
  //   } else if (level === "2") {
  //     return <img src='./second-image.png' alt='레벨2캐릭터' />;
  //   } else if (level === "3") {
  //     return <img src='./third-image.png' alt='레벨3캐릭터' />;
  //   }
  // };

  const [modalOpen, setModalOpen] = useState(false);
  const [detailTitle, setDetailTitle] = useState();
  const [detailContent, setDetailContent] = useState();
  const [detailImg, setDetailImg] = useState();
  const [detailNickname, setDetailNickname] = useState();
  const [detailCreateDate, setDetailCreateDate] = useState();
  const [detailMemberNumber, setDetailMemberNumber] = useState();

  // 글 상세보기 modal 오픈, 글 불러오기
  const feedDetail = () => {
    axios
      .get(`https://i9b108.p.ssafy.io/api/v1/feed/${feedNumber}`, {
        headers: { "AUTH-TOKEN": localStorage.getItem("access_token") },
      })
      .then((res) => {
        console.log(res);
        setDetailTitle(res.data.title);
        setDetailContent(res.data.content);
        setDetailImg(res.data.img);
        setDetailNickname(res.data.memberNickName);
        setDetailCreateDate(res.data.createDate);
        setDetailMemberNumber(res.data.feedCreatorNumber);
      })
      .then(setModalOpen(true))
      .catch((err) => {
        console.log("상세피드 오픈 error", err);
      });
  };
  return (
    <div className={classes.FeedCard} onClick={feedDetail}>
      {/* TODO : 카드를 클릭하면 모달창으로 상세페이지 설정 */}

      {/* {charImg()} */}
      <h3 className={classes.feedTitle}>제목 : {title}</h3>
      <p className={classes.creator_nickname}>닉네임 :</p>
      <p className={classes.feedContent}>내용: {content}</p>
      {/* TODO : 내용 일부만(3줄) 보이게 수정->css */}
      <p className={classes.feedWriteTime}>작성 시간 : {createDate}</p>
      {/* TODO : 생성일 0분전으로 바꾸는 로직 */}
      {inputImg && <img src={`${inputImg}`} alt='피드 이미지' />}
      {/* TODO : 이미지 null값일때 아예 안보이게 수정필요 */}
      <FeedModal modalOpen={modalOpen} setModalOpen={setModalOpen} />
    </div>
  );
}
export default FeedCard;
