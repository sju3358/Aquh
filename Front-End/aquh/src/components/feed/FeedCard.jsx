import React from "react";
// import "./FeedCard.css";
import classes from "./FeedCard.module.css";

function FeedCard({ imgPath, title, content }) {
  return (
    <div className={classes.FeedCard}>
      <div className={classes.userInfo}>
        <img
          className={classes.feedCardImg}
          src={`/aquh${imgPath}.png`}
          alt='User 캐릭터'
        />
        {/* TODO : 사용자의 캐릭터 받아오기 */}
        <p className={classes.userName}>나는 어린물방울</p>
      </div>
      <p className={classes.feedTitle}> {title}</p>
      <br />
      <p className={classes.feedContnet}> {content}</p>
    </div>
  );
}

export default FeedCard;
