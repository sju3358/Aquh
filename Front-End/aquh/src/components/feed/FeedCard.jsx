import React from "react";
import "./FeedCard.css";

function FeedCard() {
  return (
    <div className='FeedCard'>
      <img src='' alt='User 캐릭터' />
      <h3>작성자가 작성한 제목</h3>
      <p>작성자가 작성한 글 내용</p>
    </div>
  );
}

export default FeedCard;
