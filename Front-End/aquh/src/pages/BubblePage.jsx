import React, { useState, useEffect } from 'react';
import BubbleList from '../components/bubble/BubbleList';
import BubbleCard from '../components/bubble/BubbleCard';
import { bubbleList } from '../utils/api/api.bubble_service';
//TODO : 실제 api 받아오면 bubble_mock 지우기 
import { bubble_mock } from '../utils/api/api.bubble_mock';
import classes from './BubblePage.module.css';

export default function BubblePage() {

  const [events, setEvents] = useState([]);
  
  useEffect(() => {
    const res = bubbleList()
    .then(response => {
        console.log(response)
        setEvents(response.data)
      })
      .catch(error => {
        console.log(error)
      })
    console.log(res)
  }, []);

  //TODO: unique id value should be the key
  const eventCards = events.map(event =>
    <BubbleCard
      roomType={event.room_type}
      eventTitle={event.room_title}
      eventDate={event.room_date}
      imagePath={event.room_thumbnail}
      //TODO : imagePath 바꾸기 
      // imagePath={event.room_thumbnail}
      onJoin={() => { /* actual ly join room */ }}
      // label = "↗참여하기"
      />
  );
  
  return (
    <div className={classes.container}>
    <p className={classes.latestMent}><img src="../../droplet-white.png" alt="droplet" className={classes.droplet} />현재 참여중인 버블이예요</p>
    <div className={classes.latestChat}>
      
      <BubbleList>
        { eventCards }
      </BubbleList>
      </div>
      <p className={classes.latestMent}><img src="../../droplet-white.png" alt="droplet" className={classes.droplet} />Aquh에서 새로운 버블들을 찾아보세요</p>
   
      
      <div className={classes.categories}>
      <div className={classes.category}>전체</div> 
      <div className={classes.category}>버블링</div>
      <div className={classes.category}>버블톡</div>
      </div>
 
     
      <div className={classes.oldChat}>
      <BubbleList>
        { eventCards }
      </BubbleList>
      <BubbleList>
        { eventCards }
      </BubbleList>
      <BubbleList>
        { eventCards }
      </BubbleList>
      <BubbleList>
        { eventCards }
      </BubbleList>
      </div>
    
    </div>
  );
}

