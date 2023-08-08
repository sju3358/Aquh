import React from 'react';
import { useEffect, useState } from 'react';
import { mySymbolList } from '../../utils/api/api.symbol_service';

export default function UserSymbolList() {

  const [symbols, setSymbols] = useState([]);

  useEffect(() => {
    const fetchSymbolList = async () => {
      try {
        const response = await mySymbolList(1);
        const res = response.data;
        setSymbols(res)
      }
      catch(error){
        console.log(`Oh no! ${error}`);
      }
    };
    fetchSymbolList();
  }, [])

  return (
    <div>
      
    </div>
  );
}

