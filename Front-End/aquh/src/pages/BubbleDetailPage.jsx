import React from 'react';
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { singleBubble } from "../utils/api/api.bubble_service";
import { Link } from 'react-router-dom';

export default function BubbleDetailPage() {

  const [bubble, setBubble] = useState({});
  let bubbleNumber = useParams();
  // TODO: put 'bubbleNumber' into the api
  

  useEffect(() => { 
    const fetchSingleBubble = async () => {
      try {
        const response = await singleBubble(bubbleNumber.id);
        const res = response.data.data
        console.log(res)
        setBubble(res)
      }
      catch(error){

        console.log(error)
      } 
    }
    fetchSingleBubble();
  }, [])
  
  const { bubbleTitle, bubbleContent, bubbleThumbnail, bubbleType, categoryName, hostMemberNumber, planOpenDate, planCloseDate } = bubble;
  return (
    <div>
      <div>썸네일 : bubbleThumbnail </div>
        <div>제목 : {bubbleTitle}</div>
        <div>내용 : {bubbleContent}</div>
        <button>화상채팅 join하기
        <Link to='/video/' style={{ textDecoration : "none" }}>참여하기</Link>
        </button>
    </div>
  );
}

