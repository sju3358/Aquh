import React, { useEffect, useState } from "react";
import Modal from "react-modal";
import React from "react";

export default function FeedModal({ modalOpen, closeModal }) {
  return (
    <Modal isOpen={modalOpen} onRequestClose={closeModal}>
      {/* 모달 내용 */}
    </Modal>
  );
}
