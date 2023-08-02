import React, { useState, useEffect } from 'react';
import classes from './BubbleCard.module.css'
import Capsule from '../ui/Capsule';
import { bubbleList } from '../api/api.bubble_service';
import Button from '../ui/Button';
// import { Button } from 'antd';

export default function BubbleCard({
  roomType = 0,
  eventTitle = "Untitled",
  eventDate = "08월 27일(목) 17시 53분",
  imagePath = "../public/drawing.png",
  onJoin = () => {}
}) {

  return (
    <div className={classes.card}>
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
    </div>
    
  )
}