import React, { useEffect, useState } from "react";
import classes from "./FeedCard.module.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import FeedPage from "../../pages/FeedPage";

function FeedCard({
  charImg,
  title,
  content,
  createDate,
  inputImg,
  creator_nickname,
  feedCreatorNumber,
}) {
  console.log("feedCard:", inputImg);

<<<<<<< HEAD
//   const showEditButton(e)=> {
// if (member_number===feedCreatorNumber) {
  
// }
//   }
=======
  //   const showEditButton(e)=> {
  // if (member_number===feedCreatorNumber) {

  // }
  //   }
>>>>>>> 81284d64e86a5ab0f1e3cc6b312acc3d2764c1dc

  return (
    <div className={classes.FeedCard}>
      {/* <img
        className='feedCardImg'
        src={`/aquh${imgPath}.png`}
        alt='User 캐릭터'
      /> */}
      <img
        src={`https://aquh.s3.ap-northeast-2.amazonaws.com/symbol/${charImg}`}
        alt='캐릭터이미지'
      />
      <h3 className={classes.feedTitle}>제목 : {title}</h3>
      <p className={classes.creator_nickname}>닉네임 :</p>
      <p className={classes.feedContent}>내용: {content}</p>
      <p className={classes.feedWriteTime}>작성 시간 : {createDate}</p>
      {/* TODO : 생성일 0분전으로 바꾸는 로직 */}
      {inputImg && (
        <img
          src={`https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/${inputImg}`}
          alt='피드 이미지'
        />
      )}
      {/* {showEditButton && <button on>수정하기</button>} */}
      {/* TODO : 작성자와 로그인유저가 같을 경우에만 수정하기 버튼 보이기 */}
    </div>
  );
}
export default FeedCard;
