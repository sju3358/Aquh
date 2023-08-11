import React from 'react';
import { useEffect, useState } from 'react';
import { fetchSingleUser } from '../../utils/api/api.auth_service';

export default function UserLevelCard({level, maxExp, presentExp, remainingExp}) {

  // const [user, setUser] = useState({level : 0, maxExp : 0, presentExp : 0, remainingExp: 0});
  // useEffect(() => {
  //   const getSingleUser = async () => {
  //     const response = await fetchSingleUser();
  //     try{
  //       const res = response.data.data
  //       // console.log("가져왔다 유저정보보보보", res)
  //       setUser(res)
  //     }
  //     catch(error){
  //       console.log("에러ㅏ에러에러", error)
  //     }
  //   };
  //   getSingleUser();
  // },[]);


  return (
    <div>
      <p>Level: {level}</p> 
      <p>최대 경험치: {maxExp}</p>
      <p>현재 경험치: {presentExp}</p>
      <p>남은 경험치: {remainingExp}</p>
    </div>
  );
}

