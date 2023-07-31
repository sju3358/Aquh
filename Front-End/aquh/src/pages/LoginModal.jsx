import { useState } from 'react'
import LoginForm from '../users/LoginForm'
import { login } from '../api/api.auth_service'
import login_mock from '../api/api.login_mock';

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

    return <>
        {errorMessage && <div className="error-message">Oh fuck! {errorMessage}</div>}
        {accessToken && <p>You're logged in with this token: {accessToken}</p>}
        <LoginForm onLogin={(formData) => performLogin(formData)} />
    </>
}