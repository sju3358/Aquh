import React from 'react';

export default function SymbolCard({symbolNumber = 0, symbolImgName = "symbol.png", symbolName="베스트 프렌드"}) {
  return (
    <div>
      <img src={symbolImgName} alt="symbolImgName" />
      <div>{symbolName}</div>
    </div>
  );
}

