import AuthPage from '../pages/AuthPage';
import { RecoilRoot } from 'recoil';
import { useNavigate } from 'react-router-dom';
export default {
  component: AuthPage,
}

export const Primary = {
  render: () => <RecoilRoot> <AuthPage /> </RecoilRoot>
};