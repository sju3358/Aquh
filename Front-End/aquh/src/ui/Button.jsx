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
