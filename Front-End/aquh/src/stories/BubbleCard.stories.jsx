import BubbleCard from '../bubble/BubbleCard';
import bubble_mock from '../api/api.bubble_mock';

export default {
  component: BubbleCard
};

export const Primary = {
  render: () =>
    <BubbleCard
      roomType={0}
      eventTitle="Some title"
      eventContent="Some content..." />
};

export const NullProperties = {
  render: () => <BubbleCard />
};