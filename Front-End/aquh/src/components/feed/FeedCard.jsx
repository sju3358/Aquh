import React from "react";
import "./FeedCard.css";

function FeedCard({ imgPath, title, content }) {
  return (
    <div className='FeedCard'>
      <img
        className='feedCardImg'
        src={`/aquh${imgPath}.png`}
        alt='User 캐릭터'
      />
      <h3>제목 : {title}</h3>
      <p>내용: {content}</p>
    </div>
  );
}

export default FeedCard;
