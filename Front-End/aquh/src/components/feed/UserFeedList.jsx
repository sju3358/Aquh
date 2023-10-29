import React from "react";
import { useEffect, useState } from "react";
import https from "../../utils/https";
import classes from "../../pages/FeedPage.module.css";
import FeedCard from "./FeedCard";
import FeedModal from "./FeedModal";

export default function UserFeedList() {
  const [modalOpen, setModalOpen] = useState(false);
  const [clickedFeedNumber, setClickedFeedNumber] = useState(-1);
  const [feedList, setFeedList] = useState([]);

  useEffect(() => {
    https
      .get("api/v1/feed")
      .then((res) => {
        setFeedList(res.data.feedList);
        console.log("sss", res.data.feedList);
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
                  &nbsp; 내가 쓴 피드
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
                          feedLevel={feed.level}
                          feedLikeCount={feed.feedLikeCnt}
                          feedSymbolList={feed.symbolLink}
                          setclickedFeedNumber={setClickedFeedNumber}
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
      </div>
    </div>
  );
}
