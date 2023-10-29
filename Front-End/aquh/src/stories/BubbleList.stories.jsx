import BubbleList from '../components/bubble/BubbleList';
import BubbleCard from '../components/bubble/BubbleCard';

export default {
  title: 'Bubble/BubbleList',
  component: BubbleList
};

export const Primary = {
    render: () =>
        <BubbleList>
            <BubbleCard
                roomType={0}
                eventTitle="Event 1"
                eventContent="Some content..." />
            <BubbleCard
                roomType={0}
                eventTitle="Event 2"
                eventContent="Some content..." />
            <BubbleCard
                    roomType={0}
                    eventTitle="Event 3"
                    eventContent="Some content..." />
        </BubbleList>
};