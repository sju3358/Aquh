import React from 'react';
import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { memberNumberState } from '../../store/loginUserState';
import { fetchFeedListByUser } from '../../utils/api/api.auth_service';
import UserFeedCard from './UserFeedCard';

export default function UserFeedList() {

  const [userFeeds, setUserFeeds] = useState([]);
  
  useEffect(() => {
    const fetchFeedList = async () => {
      const response = await fetchFeedListByUser();
      try{
        const res = response.data.feedList;
        // console.log(res)
        setUserFeeds(res)
      }
      catch(error){
      
        console.log(error)
      }
    }; 
    fetchFeedList();
  }, []);

  const userFeedCards = userFeeds?.map((e) => 
    <UserFeedCard
      key={e.feedNumber}
      userNumber={e.feedCreatorNumber}
      title={e.title}
      content={e.content}
      nickname={e.nickName}
      date={e.createDate}
      character={e.characterName}
      />       
  )

  return (
    <div>
      {userFeedCards}
    </div>
  );
}

