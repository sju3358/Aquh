import React, { useState, useEffect } from 'react';
import classes from './EventCard.module.css'
import Capsule from '../ui/Capsule';
import { bubbleList } from '../api/api.bubble_service';

export default function EventCard({
  roomType = 0,
  eventTitle = "Untitled",
  eventContent = "Some content...",
  imagePath
}) {
  return (
    <div className={classes.card}>
      {imagePath && <img className={classes.cardImgTop} src={imagePath} alt="Event" />}
      <div className="card-header">
        {/* TODO: roomType */}
        {/* <Capsule /> */}
        <h2>{eventTitle}</h2>
      </div>
      <div className="card-body">
        <p>{eventContent}</p>
      </div>
      <div className="card-footer">
        <p>Event Date</p>
      </div>
    </div>
  )
}