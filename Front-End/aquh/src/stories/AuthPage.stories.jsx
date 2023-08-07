import AuthPage from '../pages/AuthPage';
import { RecoilRoot } from 'recoil';

export default {
  component: AuthPage,
}

export const Primary = {
  render: () => <RecoilRoot> <AuthPage /> </RecoilRoot>
};