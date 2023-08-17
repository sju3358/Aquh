import React from "react";
import { useEffect, useState } from "react";
import { fetchSingleUser } from "../../utils/api/api.auth_service";
import { motion } from "framer-motion";
import classes from "./UserLevelCard.module.css";

//here are the docs: https://www.framer.com/motion/transition/
export default function UserLevelCard({ maxExp, presentExp, remainingExp }) {
  // NaN 에러 해결 Math.round 추가
  const fillPercent = Math.round((presentExp / maxExp) * 100);

  const transition = {
    duration: 2,
    type: "spring",
    // bounce: 0.25,
    // damping: 50,
    // mass: 0.5
  };

  return (
    <div className={classes.track}>
      <motion.div
        className={classes.inner}
        animate={{ width: `${fillPercent}%` }}
        transition={transition}
      ></motion.div>
    </div>
  );
}

// <p>Level: {level}</p>
// <p>최대 경험치: {maxExp}</p>
// <p>현재 경험치: {presentExp}</p>
// <p>남은 경험치: {remainingExp}</p>
