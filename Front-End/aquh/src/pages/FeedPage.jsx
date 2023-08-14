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
  const [popularList, setPopularList] = useState([]);
  const [isPopularFeed, setIsPopularFeed] = useState(false);

  //============피드 최신순 리스트 뿌리기===========
  // TODO: 글 작성하면 새로고침 없이 바로 list에 보이기
  useEffect(() => {
    getList("recent");
  }, []);


  async function getList(filter) {
    await https
      .get("api/v1/feed/list", {
        params: {
          filter: filter,
        },
      })
      .then((res) => {
        setNewList(res.data);
      })
      .catch((err) => {
        console.log("에러", err);
        return;
      });
  }

  const clickNew = () => {
    getList("recent");
    setIsNew(true);
    setIsPopular(false);
    setIsFollow(false);
  };

  // =================피드 인기순 리스트 뿌리기 ================
  useEffect(() => {
    getFamousList("famous");
  }, []);

  useEffect(() => {
    if (isPopularFeed) {
      console.log(isPopularFeed);
      getFamousList("famous");
    }
    // setIsNewFeed(false);we
  }, [isPopularFeed]);

  async function getFamousList(famous) {
    await https
      .get("api/v1/feed/list", {
        params: {
          filter: famous,
        },
      })
      .then((res) => {
        setPopularList(res.data);
      })
      .then(setIsPopularFeed(false))
      .catch((err) => {
        console.log("에러", err);
        return;
      });
  }

  const clickPopular = () => {
    getList("famous");
    setIsNew(false);
    setIsPopular(true);
    setIsFollow(false);
  };

  //=========피드 팔로잉한 사람들 리스트 뿌리기==========
  const clickFollow = () => {
    getList("follow");
    setIsNew(false);
    setIsPopular(false);
    setIsFollow(true);
  };

  // FeedCard 상세 페이지 modal 오픈
  const [modalOpen, setModalOpen] = useState(false);
  const [clickFeedData, setClickFeedData] = useState();

  useEffect(() => {
    if(modalOpen === true){
     
          https.get(`/api/v1/feed/${localStorage.getItem("feedNumber")}`).then((responseData) => {
            console.log(responseData);
            setClickFeedData(responseData.data.data);
          }).then((error) => {
            console.log(error);
          });
      }
    }, [modalOpen]);

  return (
    <div className={classes.feedPage}>
      <div className={classes.feedWriteSection}>
        <FeedWrite/>
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
                src='../../droplet-white.png'
                alt='droplet'
                className={classes.droplet}
              />
              금주의 인기 피드들을 만나보세요 !
            </p>
            <div>
              {popularList.map((feed) => {
                console.log("map으로 뿌린 피드", feed);
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

        {/* {isFollow ? (
          <div className={classes.feeListFollowing}>
            <p className={classes.feedMent}>
              <img
                src='../../droplet-white.png'
                alt='droplet'
                className={classes.droplet}
              />
              내 친구들의 피드들을 확인 해 보세요 !
            </p>
            <div>
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
        ) : null} */}
      </div>
    </div>
  );
}

export default FeedPage;
