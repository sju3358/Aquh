import PopulatedBubbleList from "../components/bubble/PopulatedBubbleList";
//TODO : 실제 api 받아오면 bubble_mock 지우기
import classes from "./BubblePage.module.css";
import { bubbleList } from "../utils/api/api.bubble_service";
import { useEffect, useState } from "react";
import BubbleCard from "../components/bubble/BubbleCard";
import { bubbleCategory } from "../utils/api/api.bubble_service";
import BubbleCategory from "../components/bubble/BubbleCategory";
import BubbleList from "../components/bubble/BubbleList";

export default function BubblePage() {

  const [bubbles, setBubbles] = useState([]);
  const [category, setCategory] = useState([]);
  // selectedCategory
  const [selectedCategory, setSelectedCategory] = useState("");


  // fetch BubbleList 
  useEffect(() => {
    const fetchBubbleList = async () => {
      try {
        const response = await bubbleList();
        const res = response.data.data;
        console.log("BubbleListtttttt", res)
        setBubbles(res)
      }
      catch(error){
        console.log(`Oh nonono BubblePage! ${error}`);
      }
    }
    fetchBubbleList();
  }, [])
  
  // give props to BubbleCard
  const bubbleCards = bubbles?.map((bubble) => {
    return (
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
    )
  })


  // fetch CategoryList
  useEffect(() => {
    const fetchBubbleCategory = async () => {
      try {
        const response = await bubbleCategory();
        const res = response.data.category;
        setCategory(res);
      }
      catch(error){
        console.log(`Oh nonono BubblePage! ${error}`);
      } 
    }
    fetchBubbleCategory();
  })

  
  // give props to BubbleCategory 
  const categories = category?.map((category) => {
    return (
      <BubbleCategory 
        key={category.categoryNumber}
        name={category.categoryName}
        onSelect={setSelectedCategory}
        selectedCategory={selectedCategory}
      />
    )
  })

  return (
   
    <div className={classes.container}>
       {categories}
      <p className={classes.latestMent}>
        <img
          src='../../droplet-white.png'
          alt='droplet'
          className={classes.droplet}
        />
        현재 참여중인 버블이예요
      </p>
      <div className={classes.latestChat}>
        
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
        <div className={classes.category}  >전체</div>
        <div className={classes.category}  >버블링</div>
        <div className={classes.category}  >버블톡</div>
      </div>

      <div className={classes.oldChat}>
      <PopulatedBubbleList selectedCategory={selectedCategory} />
      </div>
    </div>
  );
}
