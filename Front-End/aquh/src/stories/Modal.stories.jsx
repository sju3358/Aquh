import Modal from "../components/ui/Modal";

export default {
  title: 'UI/Modal',
  component: Modal,
  parameters: {
    layout: 'fullscreen',
  },
};


export const Open = {
  render: () =>
    <Modal isModalOpen>Hello world!</Modal>
};

export const Closed = {
    render: () =>
      <Modal>Hello world!</Modal>
  };