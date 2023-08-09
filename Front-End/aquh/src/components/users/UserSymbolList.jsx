import React from 'react';
import { useEffect, useState } from 'react';
import { mySymbolList } from '../../utils/api/api.symbol_service';
import { useParams } from 'react-router-dom';
import UserSymbolPortrait from './UserSymbolPortrait';

export default function UserSymbolList() {

  const id = useParams().userId;
  const [symbols, setSymbols] = useState([]);

  useEffect(() => {
    const fetchSymbolList = async () => {
      try {
        const response = await mySymbolList(id);
        const res = response.data.data;
        console.log("user의 심볼ㄹㄹㄹㄹ", response.data.data)
        setSymbols(res)
      }
      catch(error){
        console.log(`Oh nonono UserSymbolList! ${error}`);
      }
    };
    fetchSymbolList();
  }, [])

  const symbolcards = symbols.map((e) => (
  <UserSymbolPortrait 
    key={e.symbolNumber} 
    symbolImgName={e.symbolImgName} 
    symbolName={e.symbolName} 
  />
  ));

  return (
    <div>
        {symbolcards}
    </div>
  );
}

