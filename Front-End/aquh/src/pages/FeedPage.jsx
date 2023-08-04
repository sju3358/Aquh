import React, { useState, setState } from "react";
import { Component } from "react";
import classes from "./FeedPage.module.css";
import FeedWrite from "../components/feed/FeedWrite";
import FeedCard from "../components/feed/FeedCard";
// import classes from "./FeedPage.module.css";
import https from "../utils/https";
function FeedPage() {
  // Dummy Data

  const testList = [
    {
      imgPath: 1,
      title: "오늘은 뜨개질 첫 날",
      content: "뜨개질 너무 재밌어서 요즘 이거밖에 안해요!!",
    },
    {
      imgPath: 2,
      title: "베이킹이 취미인데 좋아요",
      content:
        "베이킹 영상 광고없이 실습하니까 좋아요. 취미라서 이제 초보라 많이 봐야하는데 광고 나오면 엄청 불편했거든요 ㅠㅠ 이참에 추천받은 다른 콘텐츠 영상도 보려구요",
    },
    {
      imgPath: 3,
      title: "취미와 취미가 만나는 곳.",
      content:
        "이웃과의 소통이 많이 줄어든 요즘! 송파구방이복지관은 참여자들이 함께 소통하며 여가를 즐길 수 있는 ‘DIY취미활동’ 2기 - 원예활동을 진행",
    },
  ];

  return (
    <div className={classes.feedPage}>
      <p className={classes.latestMent}>
        <img
          src='../../droplet-white.png'
          alt='droplet'
          className={classes.droplet}
        />
        여러분의 이야기를 들려주세요!
      </p>
      <FeedWrite />
      <div>
        <p className={classes.latestMent}>
          <img
            src='../../droplet-white.png'
            alt='droplet'
            className={classes.droplet}
          />
          최신 버블들을 만나보세요
        </p>
        {testList.map((feeditem) => {
          return (
            <div className={classes.feedCard}>
              <FeedCard
                imgPath={feeditem.imgPath}
                title={feeditem.title}
                content={feeditem.content}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default FeedPage;
