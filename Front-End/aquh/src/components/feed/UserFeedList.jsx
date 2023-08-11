import React from "react";
import { useEffect, useState } from "react";
import https from "../../utils/https";
import classes from "../../pages/FeedPage.module.css";
import FeedCard from "./FeedCard";
import FeedModal from "./FeedModal";

export default function UserFeedList() {
  const [modalOpen, setModalOpen] = useState(false);
  const [clickFeedData, setClickFeedData] = useState();

  let feedList = [
    {
      feedNumber: 4,
      feedCreatorNumber: 2,
      title: "테스트3",
      content: "테스트3",
      feedLikeCnt: 0,
      viewCnt: 0,
      feedActive: true,
      feedImgOrigin: null,
      feedImgTrans: null,
      createDate: "2023-08-11T12:25:02.653+00:00",
      deleteDate: null,
      exp: 180,
      symbolLink: [],
      followingCnt: 0,
      nickName: "N_가나다초콜릿",
      level: 1,
    },
    {
      feedNumber: 3,
      feedCreatorNumber: 2,
      title: "테스트2",
      content: "테스트2",
      feedLikeCnt: 0,
      viewCnt: 0,
      feedActive: true,
      feedImgOrigin: null,
      feedImgTrans: null,
      createDate: "2023-08-11T12:24:59.716+00:00",
      deleteDate: null,
      exp: 180,
      symbolLink: [],
      followingCnt: 0,
      nickName: "N_가나다초콜릿",
      level: 1,
    },
    {
      feedNumber: 2,
      feedCreatorNumber: 2,
      title: "테스트1",
      content: "테스트1",
      feedLikeCnt: 0,
      viewCnt: 0,
      feedActive: true,
      feedImgOrigin: null,
      feedImgTrans: null,
      createDate: "2023-08-11T12:24:56.881+00:00",
      deleteDate: null,
      exp: 180,
      symbolLink: [],
      followingCnt: 0,
      nickName: "N_가나다초콜릿",
      level: 1,
    },
    {
      feedNumber: 1,
      feedCreatorNumber: 2,
      title: "테스트1",
      content: "테스트2",
      feedLikeCnt: 0,
      viewCnt: 0,
      feedActive: true,
      feedImgOrigin: null,
      feedImgTrans: null,
      createDate: "2023-08-11T12:24:51.725+00:00",
      deleteDate: null,
      exp: 180,
      symbolLink: [],
      followingCnt: 0,
      nickName: "N_가나다초콜릿",
      level: 1,
    },
  ];

  useEffect(() => {
    https
      .get("api/v1/feed")
      .then((res) => {
        feedList = res.data.feedList;
        console.log(feedList);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div className={classes.feedPage}>
      <div className={classes.feedListSection}>
        <div className={classes.feedListNew}>
          <div>
            {true ? (
              <div className={classes.feedListNew}>
                <p className={classes.feedMent}>
                  <img
                    src="../../droplet-white.png"
                    alt="droplet"
                    className={classes.droplet}
                  />
                  대충 내가 뿌린 피드라는 뜻
                </p>
                <div>
                  {feedList.map((feed) => {
                    return (
                      <div
                        className={classes.newFeedCard}
                        key={feed.feedNumber}
                      >
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
          </div>
          <FeedModal
            modalOpen={modalOpen}
            setModalOpen={setModalOpen}
            clickFeedData={clickFeedData}
          />
        </div>
      </div>
    </div>
  );
}
