import React, { useState, setState, useEffect } from "react";
import { Component } from "react";
import classes from "./FeedPage.module.css";
import FeedWrite from "../components/feed/FeedWrite";
import FeedCard from "../components/feed/FeedCard";
import axios from "axios";
// import classes from "./FeedPage.module.css";

function FeedPage() {
  // 카테고리 클릭에 따른 필터 변경
  const [isNew, setIsNew] = useState(true);
  const [isPopular, setIsPopular] = useState(false);
  const [isFollow, setIsFollow] = useState(false);

  // 각 필터에 맞는 feedList get
  const [newList, setNewList] = useState([]);

  useEffect(() => {
    axiosNew();
  }, []);
  // TODO : 글이 새로고침 될 때 실시간으로 업데이트 -> 무한렌더링 고치기

  async function axiosNew() {
    await axios({
      method: "GET",
      url: "https://i9b108.p.ssafy.io/api/v1/feed/list",
      headers: {
        "AUTH-TOKEN": localStorage.getItem("access_token"),
        // TODO : recoil atom에서 받아오는걸로 추후 수정해야함
      },
      params: {
        filter: "recent",
      },
    })
      .then((res) => {
        setNewList(res.data);
      })
      .catch((err) => {
        return;
      });
  }

  const clickNew = () => {
    setIsNew(true);
    setIsPopular(false);
    setIsFollow(false);
  };

  const clickPopular = () => {
    setIsNew(false);
    setIsPopular(true);
    setIsFollow(false);
  };

  const clickFollow = () => {
    setIsNew(false);
    setIsPopular(false);
    setIsFollow(true);
  };

  return (
    <div className={classes.feedPage}>
      <p className={classes.feedMent}>
        <img
          src='../../droplet-white.png'
          alt='droplet'
          className={classes.droplet}
        />
        나의 이야기를 작성해주세요
      </p>
      <div className={classes.feedWriteSection}>
        <FeedWrite />
      </div>
      <div className={classes.feedListSection}>
        <div className={classes.feedCategories}>
          <p onClick={clickNew} className={classes.feedCategory}>
            최신순
          </p>
          <p onClick={clickPopular} className={classes.feedCategory}>
            인기순
          </p>
          <p onClick={clickFollow} className={classes.feedCategory}>
            팔로잉
          </p>
        </div>

        {isNew ? (
          <div className={classes.feedListNew}>
            <p className={classes.feedMent}>
              <img
                src='../../droplet-white.png'
                alt='droplet'
                className={classes.droplet}
              />
              최신 피드들을 만나보세요 !
            </p>
            <div>
              {newList.map((feed) => {
                console.log("map으로 뿌린 피드", feed);
                // console.log("map으로 뿌린 피드의 이미지", feed.feedImgTrans);

                return (
                  <div className={classes.newFeedCard}>
                    <FeedCard
                      title={feed.title}
                      content={feed.content}
                      createDate={feed.createDate}
                      inputImg={feed.feedImgTrans}
                      inputImgName={feed.feedImgOrigin}
                      feedNumber={feed.feedNumber}
                    />
                  </div>
                );
              })}
            </div>
          </div>
        ) : null}

        {isPopular ? (
          <div className={classes.feedListPopular}>
            <p className={classes.feedMent}>
              <img
                src='../../droplet-white.png'
                alt='droplet'
                className={classes.droplet}
              />
              금주의 인기 피드들을 만나보세요 !
            </p>
          </div>
        ) : null}

        {isFollow ? (
          <div className={classes.feeListFollowing}>
            <p className={classes.feedMent}>
              <img
                src='../../droplet-white.png'
                alt='droplet'
                className={classes.droplet}
              />
              내 친구들의 피드들을 확인 해 보세요 !
            </p>
          </div>
        ) : null}
      </div>
    </div>
  );
}

export default FeedPage;
