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
  // 카테고리 클릭에 따른 필터 변경
  const [isNew, setIsNew] = useState(true);
  const [isPopular, setIsPopular] = useState(false);
  const [isFollow, setIsFollow] = useState(false);

  // 각 필터에 맞는 feedList get
  const [newList, setNewList] = useState([]);
  const [isNewFeed, setIsNewFeed] = useState(false);

  // TODO: 글 작성하면 새로고침 없이 바로 list에 보이기
  useEffect(() => {
    axiosNew();
  }, []);

  useEffect(() => {
    if (isNewFeed) {
      console.log(isNewFeed);
      axiosNew();
    }
    // setIsNewFeed(false);we
  }, [isNewFeed]);

  async function axiosNew() {
    await https
      .get("api/v1/feed/list", {
        params: {
          filter: "recent",
        },
      })
      .then((res) => {
        setNewList(res.data);
      })
      .then(setIsNewFeed(false))
      .catch((err) => {
        console.log("에러", err);
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
  // FeedCard 상세 페이지 modal 오픈
  const [modalOpen, setModalOpen] = useState(false);
  const [clickFeedData, setClickFeedData] = useState();

  useEffect(() => {
    async function axiosFeedData() {
      try {
        const responseData = await https.get(
          `/api/v1/feed/${localStorage.getItem("feedNumber")}`
        );

        console.log(responseData.data.data);
        setClickFeedData(responseData.data.data);
      } catch {
        console.log("error");
      }
    }
    axiosFeedData();
  }, [modalOpen]);

  return (
    <div className={classes.feedPage}>
      <p className={classes.feedMent}>
        <img
          src="../../droplet-white.png"
          alt="droplet"
          className={classes.droplet}
        />
        나의 이야기를 작성해주세요
      </p>
      <div className={classes.feedWriteSection}>
        <FeedWrite setIsNewFeed={setIsNewFeed} />
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
                src="../../droplet-white.png"
                alt="droplet"
                className={classes.droplet}
              />
              최신 피드들을 만나보세요 !
            </p>
            <div>
              {newList.map((feed) => {
                // console.log("map으로 뿌린 피드", feed);
                // console.log("map으로 뿌린 피드의 이미지", feed.feedImgTrans);

                return (
                  <div className={classes.newFeedCard} key={feed.feedNumber}>
                    <FeedCard
                      title={feed.title}
                      content={feed.content}
                      createDate={feed.createDate}
                      inputImg={feed.feedImgTrans}
                      inputImgName={feed.feedImgOrigin}
                      feedNumber={feed.feedNumber}
                      nickName={feed.nickName}
                      setModalOpen={setModalOpen}
                      // setClickedFeedNum={setClickedFeedNum}
                    />
                  </div>
                );
              })}
            </div>
            <FeedModal
              modalOpen={modalOpen}
              setModalOpen={setModalOpen}
              clickFeedData={clickFeedData}
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
              금주의 인기 피드들을 만나보세요 !
            </p>
          </div>
        ) : null}

        {isFollow ? (
          <div className={classes.feeListFollowing}>
            <p className={classes.feedMent}>
              <img
                src="../../droplet-white.png"
                alt="droplet"
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
