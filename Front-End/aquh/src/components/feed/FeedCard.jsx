import React from "react";
// import "./FeedCard.css";
import classes from "./FeedCard.module.css";

function FeedCard({ imgPath, title, content }) {
  return (
    <div className={classes.FeedCard}>
      <img
        className={classes.feedCardImg}
        src={`/aquh${imgPath}.png`}
        alt='User 캐릭터'
      />
      <h3>제목 : {title}</h3>
      <p>내용: {content}</p>
    </div>
  );
}

export default FeedCard;
