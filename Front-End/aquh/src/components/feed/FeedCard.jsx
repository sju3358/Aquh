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
  feedTitle,
  feedContent,
  feedCreateDate,
  feedImage,
  feedNumber,
  setModalOpen,
  userNickName,
}) {
  // TODO : 캐릭터 레벨에 따른 사진 보여주기

  // 글 상세보기 modal 오픈, 글 불러오기
  const openModal = () => {
    localStorage.setItem("feedNumber", feedNumber);
    setModalOpen(true);
  };
  // =========================================================
  return (
    <div className={classes.FeedCard} onClick={openModal}>
      {/* TODO : 카드를 클릭하면 모달창으로 상세페이지 설정 */}

      {/* {charImg()} */}
      <h3 className={classes.feedTitle}>{feedTitle}</h3>
      <p className={classes.creator_nickname}>{userNickName} </p>
      <p className={classes.feedCreateDate}>작성 시간 : {feedCreateDate}</p>
      {/* TODO : 생성일 0분전으로 바꾸는 로직 */}
      <p className={classes.feedContent}> {feedContent}</p>
      {/* TODO : 내용 일부만(3줄) 보이게 수정->css */}

      {feedImage && (
        <img className={classes.feedImg} src={`${feedImage}`} alt='img_null' />
      )}
    </div>
  );
}
export default FeedCard;
