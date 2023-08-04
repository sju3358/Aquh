import React, { useState, useEffect } from 'react';
import classes from './BubbleCard.module.css'
import Capsule from '../ui/Capsule';
import { bubbleList } from '../../utils/api/api.bubble_service';
import Button from '../ui/Button';
import { FaRegCalendarCheck } from 'react-icons/fa';

export default function BubbleCard({
  roomType = 0,
  eventTitle = "Untitled",
  eventDate = "Some Date..",
  imagePath,
  onJoin = () => {}
}) {

  // So just a few minor changes here
  return (
    <div className={classes.card}>
      {imagePath && <img className={classes.cardImgTop} src={imagePath} alt="Event" />}
      {/* TODO: roomType */}
        {/* <Capsule /> */}
        <p className={classes.eventTitle}>{eventTitle}</p>
        <p className={classes.eventDate}> <FaRegCalendarCheck /> {eventDate}</p>
      <Button onClick={onJoin} variant="regular" className="card-button">참여하기</Button>
    </div>
    
  )
}