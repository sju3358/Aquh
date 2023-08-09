import React from 'react';
import { useEffect, useState } from 'react';
import { mySymbolList } from '../../utils/api/api.symbol_service';
import { useParams } from 'react-router-dom';
import UserSymbolPortrait from './UserSymbolPortrait';
import classes from './UserSymbolList.module.css';

export default function UserSymbolList() {
  // TODO:
  // 1. 획득한 심볼인지 확인해야함.
  // 2. 획득한 심볼이면 클릭해서 선택 / 비선택 할 수 있어야 한다.
  // 3. 획득한 심볼이 아니라면 흑백으로 보여야하고 클릭할 수 없어야 한다.
  // 4. 획득한 심볼이라면 클릭해서 선택을 하고 (최대 5개까지 선택 가능) => 상태를 저장한다.
  // 어케하지...? ㅠ  

  const id = useParams().userId;
  const [symbols, setSymbols] = useState([]);

  useEffect(() => {
    const fetchSymbolList = async () => {
      try {
        const response = await mySymbolList(id);
        const res = response.data.data;
        setSymbols(res)
      }
      catch(error){
        console.log(`Oh nonono UserSymbolList! ${error}`);
      }
    };
    fetchSymbolList();
  }, [id])

  const symbolcards = symbols.map((e) => (
  <UserSymbolPortrait 
    key={e.symbolNumber} 
    symbolImgName={e.symbolImgName} 
    symbolName={e.symbolName} 
  />
  ));

  return (
    <div className={classes.symbolContainer}>
        {symbolcards}
    </div>
  );
}

