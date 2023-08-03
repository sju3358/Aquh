import React from 'react';
import BubbleCard from './BubbleCard';
import classes from './BubbleList.module.css';

// import some css

//TODO : 이벤트 카드 정렬 및 레이아웃만 담당
export default function BubbleList({ children }) {
  return (
    <div className="{classes.bubbleList}">{ children }</div>
    /* TODO: add list styles */
  );
}

