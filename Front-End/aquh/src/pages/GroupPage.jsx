import React, { useState, useEffect } from 'react';
import EventList from '../group/EventList';
import EventCard from '../group/EventCard';
import { bubbleList } from '../api/api.bubble_service';

export default function GroupPage() {

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
    <EventCard
      roomType={event.room_type}
      eventTitle={event.room_title}
      eventContent={event.room_content}
      imagePath={event.room_thumbnail} />
  );
  
  return (
    <div>
      <EventList>
        { eventCards }
      </EventList>
    </div>
  );
}

