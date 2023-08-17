import NicknamePage from '../pages/NicknamePage';
import { RecoilRoot } from 'recoil';
// import { useNavigate } from 'react-router-dom';
export default {
  component: NicknamePage,
  parameters: {
    layout: 'fullscreen',
  },
}

export const Primary = {
  render: () => <RecoilRoot> <NicknamePage /> </RecoilRoot>
}