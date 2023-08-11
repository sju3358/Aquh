import classes from "./FeedCard.module.css";
import { useNavigate } from "react-router-dom";
import FeedPage from "../../pages/FeedPage";
import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";
import FeedModal from "./FeedModal";
// import FeedModal from "./FeedModalPage";
import Modal from "react-modal";
import https from "../../utils/https";

function FeedCard({
  userLevel,
  feedTitle,
  feedContent,
  feedCreateDate,
  feedImage,
  feedNumber,
  setModalOpen,
  userNickName,
  feedViewCnt,
}) {
  // TODO : 캐릭터 레벨에 따른 사진 보여주기

  // 글 상세보기 modal 오픈, 글 불러오기
  const openModal = () => {
    localStorage.setItem("feedNumber", feedNumber);

    https
      .get(`/api/v1/feed/${feedNumber}`, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        userLevel = res.data.data.level;
        userNickName = res.data.data.memberNickName;

        feedTitle = res.data.data.title;
        feedContent = res.data.data.content;
        feedImage = res.data.data.img_url;
        feedViewCnt = res.data.data.viewCnt;
        feedCreateDate = res.data.data.createDate;
      })
      .catch((error) => console.log(error));
    setModalOpen(true);
  };
  // =========================================================
  return (
    <div className={classes.FeedCard} onClick={openModal}>
      {/* TODO : 카드를 클릭하면 모달창으로 상세페이지 설정 */}

      {/* {charImg()} */}
      <h3 className={classes.feedTitle}>제목 : {feedTitle}</h3>
      <p className={classes.creator_nickname}>닉네임 :{userNickName} </p>
      <p className={classes.feedContent}>내용: {feedContent}</p>
      {/* TODO : 내용 일부만(3줄) 보이게 수정->css */}
      <p className={classes.feedWriteTime}>작성 시간 : {feedCreateDate}</p>
      {/* TODO : 생성일 0분전으로 바꾸는 로직 */}
      {feedImage && (
        <img className={classes.feedImg} src={`${feedImage}`} alt="img_null" />
      )}
      {/* TODO : 이미지 null값일때 아예 안보이게 수정필요 */}
    </div>
  );
}
export default FeedCard;
