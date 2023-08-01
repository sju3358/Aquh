import EventList from '../group/EventList';
import EventCard from '../group/EventCard';
import bubble_mock from '../api/api.bubble_mock';

export default {
  component: EventList
};

export const Primary = {
    render: () =>
        <EventList>
            <EventCard
                roomType={0}
                eventTitle="Event 1"
                eventContent="Some content..." />
            <EventCard
                roomType={0}
                eventTitle="Event 2"
                eventContent="Some content..." />
            <EventCard
                    roomType={0}
                    eventTitle="Event 3"
                    eventContent="Some content..." />
        </EventList>
};