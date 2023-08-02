import LoginForm from '../users/LoginForm';

export default {
  component: LoginForm
};

// TODO: use action
export const Primary = {
  render: () => <LoginForm onLogin={() => console.log("Form submitted")} />,
};