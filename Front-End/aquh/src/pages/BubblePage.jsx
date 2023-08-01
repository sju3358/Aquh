import React, { useState, useEffect } from 'react';
import BubbleList from '../bubble/BubbleList';
import BubbleCard from '../bubble/BubbleCard';
import { bubbleList } from '../api/api.bubble_service';

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
      eventContent={event.room_content}
      imagePath={event.room_thumbnail} />
  );
  
  return (
    <div>
      <BubbleList>
        { eventCards }
      </BubbleList>
    </div>
  );
}

