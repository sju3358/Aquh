import React from 'react';
import classes from './Button.module.css';

export default function Button({
  label = "",
  onClick = () => { },
  // TODO: add style prop for differnt use cases
}) {
  return (
    <button onClick={onClick}>{label}</button>
  );
}
// TODO: add toggle button