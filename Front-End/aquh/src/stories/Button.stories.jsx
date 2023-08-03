import Button from '../ui/Button';

export default {
  component: Button
};

// This first story is the default story for the component
// when we don't specify any props

// Now check the Storybook
// Did you see it?
// it got error
// Yes because I changed the story name. Just click it on the sidebar agaian and it will work
// yes I can see that now!
// Now let's change one more thing to make this button better

// Now check the Storybook
// i did
// Did you see the label?
// yes
// nei!
// hmmmmmm...okay but i don't think i understand 100%, mb i could ask you later tonight or tmr is it okay? 
// Sure
// Let's use the variant prop in the EventCard now
// okay 

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