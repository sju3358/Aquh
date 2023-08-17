import React from "react";
import FeedCard from "../components/feed/FeedCard";
// import classes from "../FeedCard.module.css";
// import PopulatedBubbleList from "../components/bubble/PopulatedBubbleList";
import classes from "./MainPage.module.css";
import Nav from "../components/ui/Nav";
import Footer from "../components/ui/Footer";
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
          <img
            src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/People/Artist.png"
            alt="Artist"
            width="25"
            height="25"
            className={classes.dynamicEmojies}
          />
          <p className={classes.introMessage}>
          "집에서 혼자서만 그리던 그림들을 <span className={classes.emphasize}>다른 사람들과 공유</span>하니<br/> 내 방속에 갤러리를 오픈 한 것 같은 기분이에요"
          </p>
        </div>
        {/* <div className={classes.handclap}>
        <img className={classes.line} src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Hand%20gestures/Clapping%20Hands%20Light%20Skin%20Tone.png" alt="Clapping Hands Light Skin Tone" width="25" height="25" />
        <img className={classes.line} src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Hand%20gestures/Clapping%20Hands%20Light%20Skin%20Tone.png" alt="Clapping Hands Light Skin Tone" width="25" height="25" />
        <img className={classes.line} src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Hand%20gestures/Clapping%20Hands%20Light%20Skin%20Tone.png" alt="Clapping Hands Light Skin Tone" width="25" height="25" />
        </div> */}
        <div className={classes.figureBox}>
          <p className={classes.introMessage}>
          "요리를 어렵게만 생각했는데 <br/> <span className={classes.emphasize}>실시간으로 다같이 하니까</span> 근사한 만찬이 되었어요!""
          </p>
          
          <img
            src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/People%20with%20professions/Woman%20Cook%20Light%20Skin%20Tone.png"
            alt="Camera with Flash"
            width="25"
            height="25"
            className={classes.dynamicEmojies}
          />
        </div>
        
        <img
          src="../../bubbletalk-main.png"
          alt="bubblingTalk"
          className={classes.bubbleMainImg}
        />
        <div className={classes.figureBox}>
          <img
            src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/People%20with%20professions/Woman%20in%20Lotus%20Position%20Medium%20Skin%20Tone.png"
            alt="Man Lifting Weights Medium-Dark Skin Tone"
            width="25"
            height="25"
            className={classes.dynamicEmojies}
          />
          <p className={classes.introMessage}>
         "아쿠아에서 <span className={classes.emphasize}>다른 사람들과 명상하는 모임</span>을 시작한 뒤<br/> 더 생산적으로 하루를 시작하게 되었어요"
            <br />{" "}
          </p>
        </div>

        {/* <section className={classes.feedBox}>{testFeed}</section> */}
      </main>
      <Footer />
    </div>
  );
}
