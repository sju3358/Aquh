import PopulatedBubbleList from "../components/bubble/PopulatedBubbleList";
//TODO : 실제 api 받아오면 bubble_mock 지우기
import classes from "./BubblePage.module.css";

export default function BubblePage() {

  return (
    <div className={classes.container}>
      <p className={classes.latestMent}>
        <img
          src='../../droplet-white.png'
          alt='droplet'
          className={classes.droplet}
        />
        현재 참여중인 버블이예요
      </p>
      <div className={classes.latestChat}>
        <PopulatedBubbleList />
      </div>
      <p className={classes.latestMent}>
        <img
          src='../../droplet-white.png'
          alt='droplet'
          className={classes.droplet}
        />
        Aquh에서 새로운 버블들을 찾아보세요
      </p>

      <div className={classes.categories}>
        <div className={classes.category}>전체</div>
        <div className={classes.category}>버블링</div>
        <div className={classes.category}>버블톡</div>
      </div>

      <div className={classes.oldChat}>
        <PopulatedBubbleList />
      </div>
    </div>
  );
}
