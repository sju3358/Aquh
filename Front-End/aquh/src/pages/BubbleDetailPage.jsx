import React from "react";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { singleBubble } from "../utils/api/api.bubble_service";
import { Link } from "react-router-dom";
import OpenViduChatting from "../components/video/Chatting";
import { useRecoilValue } from "recoil";
import { memberNicknameState } from "../store/loginUserInfoState";
import { memberNumberState } from "../store/loginUserState";

export default function BubbleDetailPage() {
  const [bubble, setBubble] = useState({});
  const roomNumber = useParams().id;
  // TODO: put 'bubbleNumber' into the api
  const memberNickname = useRecoilValue(memberNicknameState);
  const memberNumber = useRecoilValue(memberNumberState);

  useEffect(() => {
    const fetchSingleBubble = async () => {
      try {
        const response = await singleBubble(roomNumber);
        const res = response.data.data;
        console.log(res);
        setBubble(res);
      } catch (error) {
        console.log(error);
      }
    };
    fetchSingleBubble();
  }, []);

  const {
    bubbleTitle,
    bubbleContent,
    bubbleThumbnail,
    bubbleType,
    categoryName,
    hostMemberNumber,
    planOpenDate,
    planCloseDate,
  } = bubble;
  return (
    <div>
        <OpenViduChatting
          mySessionId={roomNumber}
          userNickname={memberNickname}
          hostNumber={hostMemberNumber}
          memberNumber={memberNumber}
          bubbleTitle={bubbleTitle}
          bubbleThumbnail={bubbleThumbnail}
          bubbleContent={bubbleContent}
          bubbleType={bubbleType}
          planOpenDate={planOpenDate}
          planCloseDate={planCloseDate}
        />
        {/* <Link to={`/video/${roomNumber}`} style={{ textDecoration: "none" }}>
          참여하기
        </Link> */}
    </div>
  );
}
