import React, { useEffect, useState } from "react";
import classes from "./FeedCard.module.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function FeedCard({ charImg, title, content, createDate, inputImgName }) {
  console.log(inputImgName);
  return (
    <div className={classes.FeedCard}>
      {/* <img
        className='feedCardImg'
        src={`/aquh${imgPath}.png`}
        alt='User 캐릭터'
      /> */}
      <img src={charImg} alt='' />
      <h3 className={classes.feedTitle}>제목 : {title}</h3>
      <p className={classes.feedContent}>내용: {content}</p>
      <p className={classes.feedWriteTime}>작성 시간 : {createDate}</p>
      {/* TODO : 생성일 0분전으로 바꾸는 로직 */}
      {inputImgName && (
        <img src={`data:image/jpeg;base64,${inputImgName}`} alt='피드 이미지' />
      )}
    </div>
  );
}
export default FeedCard;
