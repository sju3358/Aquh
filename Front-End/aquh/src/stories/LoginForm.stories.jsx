import LoginForm from "../components/users/LoginForm";

export default {
  title: 'Users/LoginModal',
  component: LoginForm
};

// TODO: use action
export const Primary = {
  render: () => <LoginForm onLogin={() => console.log("Form submitted")} />,
};