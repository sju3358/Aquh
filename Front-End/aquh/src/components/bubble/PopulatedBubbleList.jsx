import { useState, useEffect } from "react";
import BubbleList from "./BubbleList";
import BubbleCard from "./BubbleCard"
import { bubbleList } from "../../utils/api/api.bubble_service";
import bubble_mock from "../../utils/api/api.bubble_mock";

export default function PopulatedBubbleList() {
    const [events, setEvents] = useState([]);

    useEffect(() => {
        const res = bubbleList()
            .then((response) => {
                console.log(response);
                setEvents(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
        console.log(res);
    }, []);

    //TODO: unique id value should be the key
    const bubbleCards = events.map((event) => (
        <BubbleCard
            key={event.room_id}
            roomType={event.room_type}
            eventTitle={event.room_title}
            eventDate={event.room_date}
            imagePath={event.room_thumbnail}
            //TODO : imagePath 바꾸기
            // imagePath={event.room_thumbnail}
            onJoin={() => {
                /* actual ly join room */
            }}
        // label = "↗참여하기"
        />
    ));

    return (
        <BubbleList>{bubbleCards}</BubbleList>
    )
}