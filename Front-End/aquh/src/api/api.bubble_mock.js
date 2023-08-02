import MockAdapter from 'axios-mock-adapter';
import api from "./api.bubble_service.js";

const bubble_mock = new MockAdapter(api, { delayResponse: 200 });

bubble_mock.onGet('list').reply((req) => {
    const res = [
    { 
      id : 1,
      room_type	: "0",
      room_title	: "같이 요리 할 사람",
      room_date	: "08월 24일 19시 30분",
      room_thumbnail : "./assets/shoes.png"
    },
    { 
      id : 2,
      room_type	: "1",
      room_title	: "같이 요리 할 사람",
      room_date	: "08월 24일 19시 30분",
      room_thumbnail : "./assets/fashion.png"
    },
    { 
      id : 3,
      room_type	: "0",
      room_title	: "같이 요리 할 사람",
      room_date	: "08월 24일 19시 30분",
      room_thumbnail : "./assets/flower.png"
    },
    { 
      id : 4,
      room_type	: "1",
      room_title	: "같이 요리 할 사람",
      room_date	: "08월 24일 19시 30분",
      room_thumbnail : "./assets/drawing.png"
    }
  ];
    return [200, res];
});

export default bubble_mock;