import MockAdapter from 'axios-mock-adapter';
import api from "./api.bubble_service.js";

const bubble_mock = new MockAdapter(api, { delayResponse: 200 });

bubble_mock.onGet('list').reply((req) => {
    const res = [
    { 
      room_id: "1",
      room_type	: "0",
      room_title	: "같이 요리 할 사람1",
      room_date	: "08월 27일 13시 51분",
      room_thumbnail : "../../shoes.png"
    },
    { 
      room_id: "2",
      room_type	: "1",
      room_title	: "같이 요리 할 사람2",
      room_date	: "08월 27일 13시 52분",
      room_thumbnail : "../../flower.png"
    },
    { 
      room_id: "3",
      room_type	: "0",
      room_title	: "같이 요리 할 사람3",
      room_date	: "08월 27일 13시 53분",
      room_thumbnail : "../../fashion.png"
    },
    { 
      room_id: "4",
      room_type	: "1",
      room_title	: "같이 요리 할 사람4",
      room_date	: "08월 27일 13시 54분",
      room_thumbnail : "../../painting.png"
    }
  ];
    return [200, res];
});

export default bubble_mock;