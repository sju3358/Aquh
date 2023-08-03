import React from 'react';
import classes from './Button.module.css';


export default function Button({
  onClick = () => { },
  variant = "regular",
  children = ""
  // The children prop is able to be a string too
  // Now let's use it like this in the story
  // TODO: add style prop for differnt use cases
}) {

  let variantClass;
  switch (variant) {
    case "regular":
      variantClass = classes.regular;
      break;
    case "prominent":
      variantClass = classes.prominent;
      break;
    // TODO: other variants...
  }
  return (
    <button className={`${classes.base} ${variantClass}`} onClick={onClick}>{children}</button>
  );
}

// I tried that but it doesn't work because it isn't assigned right there
// but actually... that's better
// but my question is, what if i have to use this button more than twice?
// like 4-5 diffrent buttons with diffrent styles
// then should i use switch? Yes, let's use swtich then
// Do you want me to leave these comments here for you to see later?  > yes please
// So right now this button takes a label prop right?
// okay..? label? 
// ahhh yes 
// If you look at the button element it takes the label in between the tags
// We can make our button component do the same thing
// wait where? 
// For example, if I use a button element (not our componenet) i write:
// <button>Click Me</button>
// yes 
// Right now, this componenet takes a label prop, so I would write:
// <Button label="Click Me" />
// We can make it so that we can write:
// <Button>Click Me</Button>
// right 

// So now I apply both the base class and which ever variant class is selected
// Now we define the CSS for the variant classes
//okay 