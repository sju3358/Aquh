import https from "../../utils/https_nonHeader";
import React, { useEffect, useState } from "react";
import classes from "./FeedModal.module.css";
import Modal from "react-modal";
import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";
import emptyHeart from "../../assets/emptyHeart.png";
import fullHeart from "../../assets/fullHeart.png";
import { GoArrowUpRight } from "react-icons/go";
import { FiFilePlus } from "react-icons/fi";

export default function FeedModal({ setModalOpen, modalOpen, feedNumber }) {
  const modalStyle = {
    //모달창 바깥부분 관련 스타일링
    overlay: {
      position: "fixed",
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
      backgroundColor: "rgba(0,0,0,0.8)",
      zIndex: 10,
    },
    // 모달창 내부 관련 스타일링
    content: {
      // width: "80rem",
      display: "flex",
      justifyContent: "center",
      // flexDirection: "column",
      backgroundColor: "#ffffff",
      overflow: "auto",
      zIndex: 10,
      // top,left,right,bottom ==> 모달창의 사이즈가 아니고 여백에서부터
      // 얼마나 떨어지게 할지 입니다.
      top: "40px",
      left: "200px",
      right: "200px",
      bottom: "40px",
      // border: "2px solid black",

      borderRadius: "20px",
    },
  };

  //피드 info state
  const [feedTitle, setFeedTitle] = useState("");
  const [feedContent, setFeedConTent] = useState("");
  const [feedIsClickLikeBtn, setFeedIsClickLikeBtn] = useState();
  const [feedMemberNickname, setFeedMemberNickname] = useState("");
  const [feedCreatorNumber, setFeedCreatorNumber] = useState(-1);
  const [feedCreateTime, setFeedCreateTime] = useState("");
  const [feedImageUrl, setFeedImageUrl] = useState("");

  //수정 관련 state
  const [inputFile, setInputFile] = useState();
  const [inputFileName, setInputFileName] = useState("");
  const [isModify, setIsModify] = useState();
  const [feedInputTitle, setInputFeedTitle] = useState("");
  const [feedInputContent, setInputFeedConTent] = useState("");
  const [feedSymbolList, setFeedSymbolList] = useState([]);
  const [feedUserLevel, setFeedUserLevel] = useState(-1);
  const loginMemberNumber = useRecoilValue(memberNumberState);
  // 재업로드
  useEffect(() => {
    if (modalOpen === true) {
      https
        .get(`/api/v1/feed/${feedNumber}`)
        .then((responseData) => {
          setFeedTitle(responseData.data.data.title);
          setFeedConTent(responseData.data.data.content);
          setFeedMemberNickname(responseData.data.data.memberNickName);
          setFeedCreatorNumber(responseData.data.data.feedCreatorNumber);
          setFeedCreateTime(responseData.data.data.createDate);
          setFeedImageUrl(responseData.data.data.img_url);
          setFeedSymbolList(responseData.data.data.symbolNumber);
          setFeedUserLevel(responseData.data.data.level);
          // console.log(responseData.data);

          https
            .get(`/api/v1/feed/like/${feedNumber}`)
            .then((res) => {
              setFeedIsClickLikeBtn(res.data.isClick);
            })
            .catch((error) => {
              console.log(error);
            });
        })
        .then((error) => {
          // console.log(error);
        });
    }
  }, [modalOpen]);

  const onClickLikeBtn = () => {
    setFeedIsClickLikeBtn(!feedIsClickLikeBtn);
    const memberNumber = loginMemberNumber;
    https
      .post("/api/v1/feed/like", { feedNumber, memberNumber })
      .then((res) => {
        // console.log(res);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const onChangeFeedTitle = (e) => {
    const inputFeedTitleData = e.target.value;
    setInputFeedTitle(inputFeedTitleData);
  };
  const onChangeFeedContent = (e) => {
    const inputFeedContentData = e.target.value;
    setInputFeedConTent(inputFeedContentData);
  };
  const onChangeFeedFile = (e) => {
    const inputFileData = e.target.files[0];
    if (inputFileData) {
      setInputFile(inputFileData);
      setInputFileName(inputFileData.name);
    } else {
      setInputFileName("사진이 없습니다");
    }
    // console.log("ASDFASDF 여기 파일임", inputFileData);
  };

  const onClinkModifyBtn = () => {
    if (feedTitle && feedContent) {
      const formData = new FormData();
      const jsonData = {
        member: {
          memberNumber: feedCreatorNumber,
        },
        title: feedTitle,
        content: feedContent,
        feedNumber: feedNumber,
      };

      // Append the JSON data under a different key
      formData.append(
        "feed",
        new Blob([JSON.stringify(jsonData)], { type: "application/json" })
      );

      if (inputFile) {
        formData.append("file", inputFile);
      } else {
        formData.append("file", new Blob(), "empty");
      }

      https
        .put("/api/v1/feed", formData, {})
        .then((response) => {
          closeModal(false);
          /* eslint no-restricted-globals: ["off"] */
          location.reload();
        })

        .catch((error) => {
          console.error(":", error);
        });
    } else if (!feedInputTitle) {
      alert("글 제목을 작성해주세요");
    } else if (!feedContent) {
      alert("글 내용을 작성해주세요");
    }
  };

  const onClickModify = () => {
    setInputFeedTitle(feedTitle);
    setInputFeedConTent(feedContent);
    setInputFile(feedImageUrl);
    setIsModify(true);
  };

  const onClickDelete = () => {
    if (confirm("정말 삭제 하시겠습니까?")) {
      https.put(`/api/v1/feed/${feedNumber}`).then((res) => {
        alert("피드가 삭제되었습니다.");

        /* eslint no-restricted-globals: ["off"] */
        location.reload();
      });
    }
  };

  const closeModal = () => {
    setIsModify(false);
    setModalOpen(false);
  };

  const modifyComponent = () => {
    return (
      <div className={classes.feedWriteCard}>
        <input
          type="text"
          value={feedInputTitle}
          className={classes.feedTitle}
          onChange={onChangeFeedTitle}
          placeholder="제목을 입력하세요"
        />

        <textarea
          cols="80"
          rows="22"
          value={feedInputContent}
          className={classes.feedContent}
          onChange={onChangeFeedContent}
          placeholder="내용을 입력하세요"
        ></textarea>

        <div className={classes.feedFile}>
          <input
            className={classes.uploadFeedName}
            value={inputFileName}
            placeholder="첨부파일"
            readOnly={true}
          />
          <label htmlFor="newFile" className={classes.feedFileLabel}>
            파일찾기
          </label>
          <input
            className={classes.feedImgRealBtn}
            onChange={onChangeFeedFile}
            accept="image/*"
            id="newFile"
            type="file"
          />

          <button className={classes.buttonRewrite} onClick={onClinkModifyBtn}>
            수정하기
          </button>
        </div>
      </div>
    );
  };

  const feedDetailComponent = () => {
    return (
      <div className={classes.modalReadContainer}>
        <div className={classes.FeedDetailCard}>
          <div className={classes.feedHeader}>
            <div className={classes.feedHeaderProfile}>
              <img
                src={`../../pfp${feedUserLevel}.png`}
                className={classes.profileImg}
              />
              <p className={classes.creator_nickname}>
                &nbsp;&nbsp;&nbsp;{feedMemberNickname}&nbsp;&nbsp;&nbsp;
              </p>
              {feedSymbolList != undefined
                ? feedSymbolList.map((symbol, index) => {
                    return (
                      <span key={index}>
                        <img
                          src={`${symbol.symbolImgName}`}
                          className={classes.symbolImg}
                        />
                        &nbsp; &nbsp;
                      </span>
                    );
                  })
                : null}
            </div>
            <button className={classes.likeButton} onClick={onClickLikeBtn}>
              <img
                src={feedIsClickLikeBtn ? fullHeart : emptyHeart}
                alt={feedIsClickLikeBtn ? "full_heart" : "empty_heart"}
              />
            </button>
          </div>

          <h3 className={classes.feedDetailTitle}>{feedTitle}</h3>
          <p className={classes.feedInfo}>
            <span className={classes.feedCreateDate}>
              작성 날짜 : {feedCreateTime}
            </span>
          </p>
          {/* TODO : 생성일 0분전으로 바꾸는 로직 */}
          <p className={classes.feedDetailContent}> {feedContent}</p>
          {feedImageUrl && (
            <img
              className={classes.feedCardImg}
              src={`${feedImageUrl}`}
              alt="img_null"
            />
          )}
        </div>

        {
          loginMemberNumber === feedCreatorNumber ? (
            <div className={classes.buttonContainer}>
              <button className={classes.buttonRewrite} onClick={onClickModify}>
                수정하기
              </button>
              <button className={classes.buttonDelete} onClick={onClickDelete}>
                삭제하기
              </button>
            </div>
          ) : null // <button>수정하기</button>
        }
      </div>
    );
  };

  return (
    <Modal
      // className={classes.modalCard}
      style={modalStyle}
      isOpen={modalOpen}
      onRequestClose={() => closeModal()}
    >
      {isModify ? modifyComponent() : feedDetailComponent()}
    </Modal>
  );
}
