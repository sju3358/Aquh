import MockAdapter from 'axios-mock-adapter';
import api from "./api.auth_service.js"

const login_mock = new MockAdapter(api, { delayResponse: 500 });

login_mock.onPost('auth').reply((req) => {
    const data = JSON.parse(req.data);
    console.log(data);
    if (data.member_email === '' || data.member_password === '') {
        const errorRes = {
            message: '회원 정보가 존재하지 않습니다',
            status: 400,
            data: null
        };
        return [400, errorRes];
    }
    const res = {
        access_token: 'qoewnaldirjwlerierwleirurldks',
        refresh_token: 'qoewnaldirjwleriqoeri1ik3134',
        member_number: 245,
        isSocialLogin: false // TODO : 이거만 다른 case 사용
    };
    return [200, res];
});

export default login_mock;

