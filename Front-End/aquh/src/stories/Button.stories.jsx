import Button from '../ui/Button';

export default {
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