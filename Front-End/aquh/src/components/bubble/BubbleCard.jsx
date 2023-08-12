import React, { useState, useEffect } from "react";
import classes from "./BubbleCard.module.css";
import Capsule from "../ui/Capsule";
import { bubbleList } from "../../utils/api/api.bubble_service";
import Button from "../ui/Button";
import { FaRegCalendarCheck } from "react-icons/fa";
import { Link } from 'react-router-dom';

export default function BubbleCard({
  title = '방제목',
  content= '방내용',
  thumbnail = 'https://picsum.photos/200/300',
  type = '방타입',
  category = '카테고리',
  host = '호스트번호',
  openDate = '2023-08-12T18:33:56.36681',
  closeDate = '2023-08-12T18:33:56.34344',
  onJoin = () => {}
}) 

{
  // So just a few minor changes here
  return (
    <div className={classes.card}>
      {thumbnail && (
        <img className={classes.cardImgTop} src={thumbnail} alt='Event' />
      )}
      {/* TODO: roomType */}
      {/* <Capsule /> */}
      <p className={classes.eventTitle}>{title}</p>
      <p className={classes.eventDate}>
        {" "}
        <FaRegCalendarCheck /> {openDate.slice(0, 10)} ~{" "} {closeDate.slice(0, 10)}
      </p>
      <div>{type}</div>
      <Button onClick={onJoin} variant='regular' className='card-button'>
        {/* 참여하기 */}
        {/* 0807 김재원 수정 */}
        <Link to='/video' style={{ textDecoration : "none" }}>참여하기</Link>
      </Button>
    </div>
  );
}
