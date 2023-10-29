import React from 'react';

export default function BubbleCategory({name="수공예", onSelect, selectedCategory}) {

  // const isSelected = name === selectedCategory;

  return (
    <button onClick={() => onSelect(name)}>
      {name}
    </button>
  );
}

