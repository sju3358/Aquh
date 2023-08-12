import { useState, useEffect } from "react";
import BubbleList from "./BubbleList";
import BubbleCard from "./BubbleCard"
import { bubbleList } from "../../utils/api/api.bubble_service";
// import bubble_mock from "../../utils/api/api.bubble_mock";

export default function PopulatedBubbleList({selectedCategory}) {
    const [bubbles, setBubbles] = useState([]);

    useEffect(() => {
        const fetchBubbleList = async () => {
          try {
            const response = await bubbleList();
            const res = response.data.data;
            console.log("BubblePage", res)
            setBubbles(res)
          }
          catch(error){
            console.log(`Oh nonono BubblePage! ${error}`);
          }
        }
        fetchBubbleList();
      }, [])
    
    const filteredBubblesByCategory = selectedCategory ? bubbles.filter(bubble => bubble.categoryName === selectedCategory) : bubbles;
  
    const bubbleCards = filteredBubblesByCategory?.map((bubble) => (
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
        <BubbleList>{bubbleCards}</BubbleList>
    )
}