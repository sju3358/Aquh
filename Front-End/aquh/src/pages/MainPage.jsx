import React from "react";
import FeedCard from "../components/feed/FeedCard";
// import classes from "../FeedCard.module.css";
// import PopulatedBubbleList from "../components/bubble/PopulatedBubbleList";
import classes from "./MainPage.module.css";
import Nav from "../components/ui/Nav";
export default function MainPage() {
  const testList = [
    {
      imgPath: 1,
      title: "오늘은 뜨개질 첫 날",
      content: "뜨개질 너무 재밌어서 요즘 이거밖에 안해요!!",
    },
    {
      imgPath: 2,
      title: "베이킹이 취미인데 좋아요",
      content:
        "베이킹 영상 광고없이 실습하니까 좋아요. 취미라서 이제 초보라 많이 봐야하는데 광고 나오면 엄청 불편했거든요 ㅠㅠ 이참에 추천받은 다른 콘텐츠 영상도 보려구요",
    },
    {
      imgPath: 3,
      title: "취미와 취미가 만나는 곳.",
      content:
        "이웃과의 소통이 많이 줄어든 요즘! 송파구방이복지관은 참여자들이 함께 소통하며 여가를 즐길 수 있는 ‘DIY취미활동’ 2기 - 원예활동을 진행",
    },
  ];
  // 코드 밖으로 분리
  const testFeed = testList.map((feeditem) => (
    <FeedCard
      imgPath={feeditem.imgPath}
      title={feeditem.title}
      content={feeditem.content}
    />
  ));

  return (
    <div>
      <main className={classes.container}>
        <img
          src="../../bubbling-main.png"
          alt="bubblingMain"
          className={classes.bubbleMainImg}
        />
        <div className={classes.figureBox}>
        <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/People/Artist.png" 
        alt="Artist" 
        width="25" 
        height="25" 
        className={classes.dynamicEmojies} />
        <p className={classes.introMessage}>"제가 항상 그림을 잘 그리는지 못그리는지 궁금했는데 <br/> 사람들에게 보여주고 얘기를 들어볼 수 있어서 너무 좋아요!"</p>
        </div>
        <div className={classes.figureBox}>
        <p className={classes.introMessage}>"사진찍는 취미에 대해 말할 수 잇어서 어쩌구저쩌구저쩌구 어쩌구<br/> "</p>
        <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Camera%20with%20Flash.png" 
        alt="Camera with Flash" 
        width="25" 
        height="25"
        className={classes.dynamicEmojies} />
        </div>
        <img
          src="../../bubbletalk-main.png"
          alt="bubblingTalk"
          className={classes.bubbleMainImg}
        />
        <div className={classes.figureBox}> 
        <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/People%20with%20activities/Man%20Lifting%20Weights%20Medium-Dark%20Skin%20Tone.png" 
        alt="Man Lifting Weights Medium-Dark Skin Tone" 
        width="25" 
        height="25" 
        className={classes.dynamicEmojies} />
        <p className={classes.introMessage}>"운동을 즐기면서 어쩌구 ㅈ저쩌구 같이할 수 잇는 사람을 찾습니다 어쩌구"<br/> </p>
        </div>
       

        <section className={classes.feedBox}>{testFeed}</section>
      </main>
    </div>
  );
}
