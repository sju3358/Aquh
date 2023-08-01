import MockAdapter from 'axios-mock-adapter';
import api from "./api.bubble_service.js";

const bubble_mock = new MockAdapter(api, { delayResponse: 200 });

bubble_mock.onGet('list').reply((req) => {
    const res = [
    { 
      room_type	: "0",
      room_title	: "같이 요리 할 사람",
      room_content	: "* 매너있는 분만!",
      room_thumbnail : "{이미지 url}"
    },
    { 
      room_type	: "1",
      room_title	: "같이 요리 할 사람",
      room_content	: "* 매너있는 분만!",
      room_thumbnail : "{이미지 url}"
    },
    { 
      room_type	: "0",
      room_title	: "같이 요리 할 사람",
      room_content	: "* 매너있는 분만!",
      room_thumbnail : "{이미지 url}"
    },
    { 
      room_type	: "1",
      room_title	: "같이 요리 할 사람",
      room_content	: "* 매너있는 분만!",
      room_thumbnail : "{이미지 url}"
    }
  ];
    return [200, res];
});

export default bubble_mock;