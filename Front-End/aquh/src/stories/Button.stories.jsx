import Button from "../components/ui/Button";

export default {
  title: 'UI/BubbleList',
  component: Button
};


export const Default = {
  render: () =>
    <Button>Click Me</Button>
};

export const Regular = {
    render: () =>
      <Button variant="regular">Click Me</Button>
};

export const Prominent = {
    render: () =>
      <Button variant="prominent">Click Me</Button>
};