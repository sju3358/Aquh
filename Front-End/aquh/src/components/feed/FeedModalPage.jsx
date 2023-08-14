import { useNavigate } from "react-router-dom";
import axios from "axios";
import React, { useEffect, useState } from "react";
import Modal from "react-modal";
import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";

export default function FeedModal({ modalOpen, closeModal }) {
  return (
    <Modal isOpen={modalOpen} onRequestClose={closeModal}>
      {/* 모달 내용 */}
    </Modal>
  );
}
