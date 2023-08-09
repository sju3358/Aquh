import React from 'react';
import { useEffect, useState } from 'react';
import { mySymbolList } from '../../utils/api/api.symbol_service';
import { useParams } from 'react-router-dom';

export default function UserSymbolList() {
  const id = useParams().userId;
  const [symbols, setSymbols] = useState([]);

  useEffect(() => {
    const fetchSymbolList = async () => {
      try {
        const response = await mySymbolList(id);
        const res = response.data;
        setSymbols(res)
      }
      catch(error){
        console.log(`Oh nonono UserSymbolList! ${error}`);
      }
    };
    fetchSymbolList();
  }, [])

  return (
    <div>
      
    </div>
  );
}

