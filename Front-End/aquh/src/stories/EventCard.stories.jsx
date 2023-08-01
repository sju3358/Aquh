import EventCard from '../group/EventCard';
import bubble_mock from '../api/api.bubble_mock';

export default {
  component: EventCard
};

export const Primary = {
  render: () =>
    <EventCard
      roomType={0}
      eventTitle="Some title"
      eventContent="Some content..." />
};

export const NullProperties = {
  render: () => <EventCard />
};