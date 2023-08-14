import React from "react";
import { useEffect, useState } from "react";
import https from "../../utils/https";
import classes from "../../pages/FeedPage.module.css";
import FeedCard from "./FeedCard";
import FeedModal from "./FeedModal";

export default function UserFeedList() {
  const [modalOpen, setModalOpen] = useState(false);
  const [clickFeedData, setClickFeedData] = useState();
  const [feedList, setFeedList] = useState([]);

  useEffect(() => {
    https
      .get("api/v1/feed")
      .then((res) => {
        setFeedList(res.data.feedList);
        console.log(feedList);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  useEffect(() => {
    if (modalOpen === true) {
      https
        .get(`/api/v1/feed/${localStorage.getItem("feedNumber")}`)
        .then((responseData) => {
          console.log(responseData);
          setClickFeedData(responseData.data.data);
        })
        .then((error) => {
          console.log(error);
        });
    }
  }, [modalOpen]);

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
        </div>
      </div>
    </div>
  );
}
