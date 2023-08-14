import BubbleCard from "./BubbleCard"
import classes from './BubbleList.module.css';

export default function BubbleList({
  bubbles = [],
  selectedCategory,
  selectedType
}) {

  let filteredBubbles = bubbles
    // bubble의 속성에는 CategoryName으로 "전체"가 없다. 
    // 그러므로 filter안에 들어갔을 때, 전체를 클릭하면 false가 나와 아무것도 없게 된다.
    // 따라서 selectedCategory에 default 값을 전체로 설정해두고, 그게 아닐 경우에는 filter를 걸어준다. 
  if (selectedCategory && selectedCategory !== "전체") {
    filteredBubbles = filteredBubbles
      .filter(bubble => bubble.categoryName === selectedCategory)
  }
  if (selectedType && selectedType !== "전체") {
    filteredBubbles = filteredBubbles
      .filter(bubble => bubble.bubbleType === (selectedType === "버블톡" ? true : false))
  }

  const bubbleCards = filteredBubbles?.map((bubble) => (
      <BubbleCard
          key={bubble.bubbleNumber}
          title={bubble.bubbleTitle}
          content={bubble.bubbleContent}
          thumbnail={bubble.bubbleThumbnail}
          type={bubble.bubbleType}
          category={bubble.categoryName}
          host={bubble.hostMemberNumber}
          openDate={bubble.planOpenDate}
          closeDate={bubble.planCloseDate}
          onJoin={() => { }}
          selectedCategory={selectedCategory}
      />
  ));

  return (
    <div className={classes.bubbleList}>
      {bubbleCards}
    </div>
  )
}