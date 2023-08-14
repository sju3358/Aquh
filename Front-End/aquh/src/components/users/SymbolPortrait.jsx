import React from "react";
import classes from "./SymbolPortrait.module.css";
import https from "../../utils/https";
import { useRecoilValue } from "recoil";
import { memberActiveSymbolState } from "../../store/loginUserInfoState";
export default function SymbolPortrait({
  symbolNumber,
  symbolImgName,
  symbolName,
  isActive,
  isAcquired,
}) {
  const memberActiveSymbols = useRecoilValue(memberActiveSymbolState);

  const handleClick = () => {
    if (memberActiveSymbols >= 5 && isActive === false) {
      alert("심볼은 최대 5개까지만 장착 가능합니다.");
    } else {
      https.put(`/api/v1/symbol/${symbolNumber}`).then(() => {
        /* eslint no-restricted-globals: ["off"] */
        location.reload();
      });
    }
  };
  return (
    <section className={classes.symbolCard}>
      <div className={classes.imgBackground}>
        {/* TODO: isActive를 획득여부로 교체해야함. */}
        <img
          src={symbolImgName}
          alt="symbolImgName"
          onClick={handleClick}
          className={`${
            isAcquired ? classes.symbolImage : classes.symbolImageInvalid
          }`}
        />
      </div>
    </section>
  );
}
