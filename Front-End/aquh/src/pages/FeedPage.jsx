import React, { useState, setState, useEffect } from "react";
import { Component } from "react";
import classes from "./FeedPage.module.css";
import FeedWrite from "../components/feed/FeedWrite";
import FeedCard from "../components/feed/FeedCard";
import Modal from "react-modal";
import FeedModal from "../components/feed/FeedModal";
import https from "../utils/https";
// import classes from "./FeedPage.module.css";

function FeedPage() {
  const [isNew, setIsNew] = useState(true);
  const [isPopular, setIsPopular] = useState(false);

  const [newList, setNewList] = useState([]);
  const [filter, setFilter] = useState("recent");
  const [renderFlag, setRenderFlag] = useState(1);

  const [clickedFeedNumber, setclickedFeedNumber] = useState(-1);
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => {
    getList(filter);
  }, [renderFlag]);

  async function getList(filter) {
    await https
      .get("api/v1/feed/list", {
        params: {
          filter: filter,
        },
      })
      .then((res) => {
        setNewList(res.data);
        console.log(res.data);
      })
      .catch((err) => {
        console.log("에러", err);
        return;
      });
  }

  const clickNew = () => {
    setFilter("recent");
    setIsNew(true);
    setIsPopular(false);
    setRenderFlag(renderFlag + 1);
  };

  const clickPopular = () => {
    setFilter("famous");
    setIsNew(false);
    setIsPopular(true);
    setRenderFlag(renderFlag + 1);
  };

  return (
    <div className={classes.feedPage}>
      <div className={classes.feedWriteSection}>
        <FeedWrite />
      </div>
      <div className={classes.feedListSection}>
        <div className={classes.feedCategories}>
          <p
            onClick={clickNew}
            className={`${classes.feedCategory} ${
              isNew && classes.selectedButton
            }`}
          >
            최신순
          </p>
          <p
            onClick={clickPopular}
            className={`${classes.feedCategory} ${
              isPopular && classes.selectedButton
            }`}
          >
            인기순
          </p>
        </div>

        {isNew ? (
          <div className={classes.feedListNew}>
            <p className={classes.feedMent}>
              <img
                src="../../droplet-white.png"
                alt="droplet"
                className={classes.droplet}
              />
              &nbsp;최신 피드들을 만나보세요 !
            </p>
            <div className={classes.feedCards}>
              {newList.map((feed) => {
                // console.log("map으로 뿌린 피드", feed);
                // console.log("map으로 뿌린 피드의 이미지", feed.feedImgTrans);

                return (
                  <div className={classes.newFeedCard} key={feed.feedNumber}>
                    <FeedCard
                      feedTitle={feed.title}
                      feedContent={feed.content}
                      feedCreateDate={feed.createDate}
                      feedImage={feed.feedImgTrans}
                      feedNumber={feed.feedNumber}
                      userNickName={feed.nickName}
                      feedLikeCount={feed.feedLikeCnt}
                      feedSymbolList={feed.symbolLink}
                      setModalOpen={setModalOpen}
                      setclickedFeedNumber={setclickedFeedNumber}
                      feedLevel={feed.level}
                    />
                  </div>
                );
              })}
            </div>
            <FeedModal
              modalOpen={modalOpen}
              setModalOpen={setModalOpen}
              feedNumber={clickedFeedNumber}
            />
          </div>
        ) : null}

        {isPopular ? (
          <div className={classes.feedListPopular}>
            <p className={classes.feedMent}>
              <img
                src="../../droplet-white.png"
                alt="droplet"
                className={classes.droplet}
              />
              &nbsp;금주의 인기 피드들을 만나보세요 !
            </p>
            <div className={classes.feedCards}>
              {newList.map((feed) => {
                // console.log("map으로 뿌린 피드", feed);
                // console.log("map으로 뿌린 피드의 이미지", feed.feedImgTrans);

                return (
                  <div className={classes.newFeedCard} key={feed.feedNumber}>
                    <FeedCard
                      feedTitle={feed.title}
                      feedContent={feed.content}
                      feedCreateDate={feed.createDate}
                      feedImage={feed.feedImgTrans}
                      feedNumber={feed.feedNumber}
                      feedLikeCount={feed.feedLikeCount}
                      feedSymbolList={feed.symbolLink}
                      userNickName={feed.nickName}
                      setModalOpen={setModalOpen}
                      setclickedFeedNumber={setclickedFeedNumber}
                      feedLevel={feed.level}
                    />
                  </div>
                );
              })}
            </div>
            <FeedModal
              modalOpen={modalOpen}
              setModalOpen={setModalOpen}
              feedNumber={clickedFeedNumber}
            />
          </div>
        ) : null}
      </div>
    </div>
  );
}

export default FeedPage;
