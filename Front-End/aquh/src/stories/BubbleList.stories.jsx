import BubbleList from '../bubble/BubbleList';
import BubbleCard from '../bubble/BubbleCard';
import bubble_mock from '../api/api.bubble_mock';

export default {
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