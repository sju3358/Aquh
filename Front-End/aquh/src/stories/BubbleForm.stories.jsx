import BubbleForm from '../components/bubble/BubbleForm';

export default {
  title: 'Bubble/BubbleForm',
  component: BubbleForm
};

export const Primary = {
  render: () =>
    <BubbleForm />
};
// TODO: use Storybook action for testing instead of console.log

export const NullProperties = {
  render: () => <BubbleForm />
};