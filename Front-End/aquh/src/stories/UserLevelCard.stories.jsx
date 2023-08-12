import UserLevelCard from "../components/users/UserLevelCard";

export default {
  title: 'Users/UserLevelCard',
  component: UserLevelCard
};


export const Empty = {
  render: () => <UserLevelCard maxExp={1000} presentExp={0} />,
};

export const HalfFull = {
    render: () => <UserLevelCard maxExp={1000} presentExp={500} />,
};

export const Full = {
    render: () => <UserLevelCard maxExp={1000} presentExp={1000} />,
};