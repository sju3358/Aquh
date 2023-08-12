import React from 'react';
import { useEffect, useState } from 'react';
import { bubbleCategory } from '../../utils/api/api.bubble_service';

export default function BubbleCategory({name="수공예", onSelect, selectedCategory}) {

  // const isSelected = name === selectedCategory;

  return (
    <button onClick={() => onSelect(name)}>
      {name}
    </button>
  );
}

