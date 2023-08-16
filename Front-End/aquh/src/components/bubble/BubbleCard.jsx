import React, { useState, useEffect } from "react";
import classes from "./BubbleCard.module.css";
import Capsule from "../ui/Capsule";
import { bubbleList } from "../../utils/api/api.bubble_service";
import Button from "../ui/Button";
import { FaRegCalendarCheck } from "react-icons/fa";
import { Link } from "react-router-dom";
export default function BubbleCard({
  title = "방제목",
  content = "방내용",
  thumbnail = "https://picsum.photos/200/300",
  type = "방타입 false면 어쩌구",
  category = "카테고리",
  host = "호스트번호",
  openDate = "2023-08-12T18:33:56.36681",
  closeDate = "2023-08-12T18:33:56.34344",
  bubbleId,
  onJoin = () => {},
}) {
  const id = bubbleId;

  return (
    <div className={classes.card}>
      <img className={classes.cardImgTop} src={thumbnail} alt="Event" />

      {/* TODO: roomType */}
      {category === "스포츠-운동" && (
        <div className={classes.eventCategory1}>{category}</div>
      )}
      {category === "수공예-드로잉" && (
        <div className={classes.eventCategory2}>{category}</div>
      )}
      {category === "요리-베이킹" && (
        <div className={classes.eventCategory3}>{category}</div>
      )}
      {category === "문화-예술" && (
        <div className={classes.eventCategory4}>{category}</div>
      )}
      {category === "미용-뷰티" && (
        <div className={classes.eventCategory5}>{category}</div>
      )}
      {category === "홈-리빙" && (
        <div className={classes.eventCategory6}>{category}</div>
      )}
      {category === "자기개발" && (
        <div className={classes.eventCategory7}>{category}</div>
      )}
      {category === "기타" && (
        <div className={classes.eventCategory8}>{category}</div>
      )}
      <p className={classes.eventTitle}>{title}</p>
      <p className={classes.eventDate}>
        {" "}
        <FaRegCalendarCheck /> {openDate?.slice(0, 10)}
      </p>
      <button onClick={onJoin} className={classes.cardButton}>
        <Link
          to={`/bubble/${id}`}
          style={{ color: "white" }}
          className={classes.detailLink}
        >
          참여하기
        </Link>
      </button>
    </div>
  );
}
