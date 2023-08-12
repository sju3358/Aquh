import React from 'react';
import BubbleForm from '../components/bubble/BubbleForm';

export default function BubbleDetailPage() {
  const createBubble = (form) => {
    console.log(form);
  }
  return (
    <div>
      <BubbleForm onSubmit={(form) => {createBubble(form)}}/>
    </div>
  );
}

