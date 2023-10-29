import BubbleCard from '../components/bubble/BubbleCard';

export default {
  title: 'Bubble/BubbleCard',
  component: BubbleCard
};

export const Primary = {
  render: () =>
    <BubbleCard
      roomType={0}
      eventTitle="Some title"
      eventContent="Some content..."
      onJoin={() => console.log("Join bubble...")} />
};
// TODO: use Storybook action for testing instead of console.log

export const NullProperties = {
  render: () => <BubbleCard />
};