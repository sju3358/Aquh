import React, { useState } from "react";
import axios from "axios";
import classes from "./FeedWrite.module.css";

import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";

function FeedWrite({ setIsNewFeed }) {
  const [feedTitle, setFeedTitle] = useState("");
  const [feedContent, setFeedConTent] = useState("");
  const [file, setFile] = useState(null);

  const memberNumber = useRecoilValue(memberNumberState);

  const onChangeFeedTitle = (e) => {
    const currentFeedTitle = e.target.value;
    setFeedTitle(currentFeedTitle);
    console.log(currentFeedTitle);
  };
  const onChangeFeedContent = (e) => {
    const currentFeedContent = e.target.value;
    setFeedConTent(currentFeedContent);
    console.log(currentFeedContent);
  };
  const onChangeFeedFile = (e) => {
    const currentFile = e.target.files[0];
    setFile(currentFile);
    console.log("여기 파일임", currentFile);
  };

  const onClinkWriteBtn = () => {
    if (feedTitle && feedContent) {
      const formData = new FormData();
      const jsonData = {
        member: {
          memberNumber: memberNumber,
        },
        title: feedTitle,
        content: feedContent,
      };

      // Append the JSON data under a different key
      formData.append(
        "feed",
        new Blob([JSON.stringify(jsonData)], { type: "application/json" })
      );

      if (file) {
        formData.append("file", file);
      } else {
        formData.append("file", new Blob(), "empty");
      }

      //     axios
      //       .post("https://i9b108.p.ssafy.io/api/v1/feed", formData, {
      //         headers: {
      //           "AUTH-TOKEN": localStorage.getItem("access_token"),
      //         },
      //       })
      //       .then((response) => {
      //         console.log("Response:", response.data);
      //       })
      //       .catch((error) => {
      //         console.error("Error:", error);
      //       });
      //   } else if (!feedTitle) {
      //     alert("글 제목을 작성해주세요");
      //   } else if (!feedContent) {
      //     alert("글 내용을 작성해주세요");
      //   }
      // };
      axios
        .post("https://localhost:8080/api/v1/feed", formData, {
          headers: {
            "AUTH-TOKEN": localStorage.getItem("access_token"),
          },
        })
        .then((response) => {
          console.log("Response:", response.data);
        })
        .then(setIsNewFeed(true))
        .catch((error) => {
          console.error(":", error);
        });
    } else if (!feedTitle) {
      alert("글 제목을 작성해주세요");
    } else if (!feedContent) {
      alert("글 내용을 작성해주세요");
    }
  };

  return (
    <div className={classes.feedWriteCard}>
      <div className={classes.feedTitle}>
        <input
          type='text'
          value={feedTitle}
          onChange={onChangeFeedTitle}
          placeholder='제목을 입력하세요'
        />
      </div>
      <div className={classes.feedContent}>
        <textarea
          cols='30'
          rows='10'
          value={feedContent}
          onChange={onChangeFeedContent}
          placeholder='내용을 입력하세요'></textarea>
      </div>
      <div className={classes.feedFile}>
        <input onChange={onChangeFeedFile} accept='image/*' type='file' />
      </div>
      <button onClick={onClinkWriteBtn}>글 작성하기</button>
    </div>
  );
}

export default FeedWrite;
