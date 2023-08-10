import React from 'react';

export default function UserFeedCard({title='나 규호쓰 29살인디', content='내년엔 30이다.', nickname='규호쓰', date='230815', character='Character'}) {
  return (
    <div>
      <div>{title}</div>
      <div>{content}</div>
      <div>{nickname}</div>
      <div>{date}</div>
      <img src={character} alt="character" />
    </div>
  );
}

