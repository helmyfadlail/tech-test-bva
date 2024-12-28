import { Link } from "react-router-dom";

import { useAuth, useForm } from "../hooks";

import Cookies from "universal-cookie";

import { Button } from "../components";

import { loginRequest } from "../types/dto";

const initValue = { username: "", password: "" };

export const LoginPage = () => {
  const [value, handleChange] = useForm<loginRequest>(initValue);
  const { execute, loading } = useAuth<loginRequest>("/auth/login", "/");

  const cookies = new Cookies(null, { path: "/" });

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    await execute(value, "Login success");
    cookies.set("user", value.username);
  };

  return (
    <section className="flex items-center justify-center min-h-screen bg-background">
      <div className="w-full rounded-lg shadow bg-dark md:mt-0 sm:max-w-md xl:p-0">
        <div className="p-6 space-y-4 md:space-y-6 sm:p-8 text-light">
          <h1 className="text-xl font-bold leading-tight tracking-tight md:text-2xl">Sign in to your account</h1>
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <label htmlFor="username" className="block mb-2 text-sm font-medium">
                Username
              </label>
              <input type="text" id="username" onChange={handleChange} placeholder="john_doe" className="input-form" required disabled={loading} />
            </div>
            <div className="mb-6">
              <label htmlFor="password" className="block mb-2 text-sm font-medium">
                Password
              </label>
              <input type="password" id="password" onChange={handleChange} placeholder="••••••••" className="input-form" required disabled={loading} />
            </div>
            <Button type="submit" className="w-full bg-primary py-2.5">
              {loading ? <div className="loader size-8"></div> : "Sign in"}
            </Button>
            <p className="mt-4 text-sm font-light text-gray-400">
              Don't have an account yet?{" "}
              <Link to="/register" className="font-medium text-secondary hover:underline">
                Register here
              </Link>
            </p>
          </form>
        </div>
      </div>
    </section>
  );
};
