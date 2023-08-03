import { useState } from 'react'
import LoginForm from '../components/users/LoginForm'
import { login } from '../utils/api/api.auth_service'
import login_mock from '../utils/api/api.login_mock';
import NaverLoginButton from '../components/users/NaverLoginButton';
import GoogleLoginButton from '../components/users/GoogleLoginButton';

export default function LoginModal() {

    const [errorMessage, setErrorMessage] = useState(null);
    const [accessToken, setAccessToken] = useState(null);

    const performLogin = (formData) => {
        const response = login(formData)
            .then(response => {
                setAccessToken(response.data.access_token)
            })
            .catch(error => {
                setErrorMessage(error.response.data.message)
            })
    };

    return (
        <>
            {errorMessage && <div className="error-message">Oh fuck! {errorMessage}</div>}
            {accessToken && <p>You're logged in with this token: {accessToken}</p>}
            <LoginForm onLogin={(formData) => performLogin(formData)} />
            <NaverLoginButton />
            <GoogleLoginButton />
        </>
    )
    
}