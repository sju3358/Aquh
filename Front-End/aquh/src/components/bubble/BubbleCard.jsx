import React, { useState, useEffect } from 'react';
import classes from './BubbleCard.module.css'
import Capsule from '../ui/Capsule';
import { bubbleList } from '../../utils/api/api.bubble_service';
import Button from '../ui/Button';
<<<<<<< HEAD:Front-End/aquh/src/bubble/BubbleCard.jsx
// import { Button } from 'antd';
=======
import { FaRegCalendarCheck } from 'react-icons/fa';
>>>>>>> a6c68084669281354a8e17d830e1b7549efb2020:Front-End/aquh/src/components/bubble/BubbleCard.jsx

export default function BubbleCard({
  roomType = 0,
  eventTitle = "Untitled",
<<<<<<< HEAD:Front-End/aquh/src/bubble/BubbleCard.jsx
  eventDate = "08월 27일(목) 17시 53분",
  imagePath = "../public/drawing.png",
=======
  eventDate = "Some Date..",
  imagePath,
>>>>>>> a6c68084669281354a8e17d830e1b7549efb2020:Front-End/aquh/src/components/bubble/BubbleCard.jsx
  onJoin = () => {}
}) {

  // So just a few minor changes here
  return (
    <div className={classes.card}>
<<<<<<< HEAD:Front-End/aquh/src/bubble/BubbleCard.jsx
      {imagePath && <img className={classes.cardImgTop} src=
      "./assets/drawing.png" alt="Event" />}
      <div className="card-header">
        {/* TODO: roomType */}
        {/* <Capsule /> */}
        <h2>{eventTitle}</h2>
      </div>
      <div className="card-body">
        <p>{eventDate}</p>
      </div>
      <div className="card-footer">
      </div>
      <Button onClick={onJoin}> Join </Button>
=======
      {imagePath && <img className={classes.cardImgTop} src={imagePath} alt="Event" />}
      {/* TODO: roomType */}
        {/* <Capsule /> */}
        <p className={classes.eventTitle}>{eventTitle}</p>
        <p className={classes.eventDate}> <FaRegCalendarCheck /> {eventDate}</p>
      <Button onClick={onJoin} variant="regular" className="card-button">참여하기</Button>
>>>>>>> a6c68084669281354a8e17d830e1b7549efb2020:Front-End/aquh/src/components/bubble/BubbleCard.jsx
    </div>
    
  )
}