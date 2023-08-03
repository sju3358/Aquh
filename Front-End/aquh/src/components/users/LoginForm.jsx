import { useState } from 'react';

export default function LoginForm({ onLogin }) {

  const [form, setForm] = useState({ email: '', password: '' });

  const handleSubmit = (e) => {
    e.preventDefault();
    onLogin(form);
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value })
  }

  return (
    <>
      <form onSubmit={handleSubmit}>
        <label htmlFor="email"></label>
        <input type="email" name="email" value={form.email} onChange={handleChange} />
        <label htmlFor="password"></label>
        <input type="password" name="password" value={form.password} onChange={handleChange} />
        <input type="submit" value="Login" />
      </form>
    </>
  )
}