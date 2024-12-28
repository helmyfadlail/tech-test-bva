import { Link } from "react-router-dom";

import { useAuth, useForm } from "../hooks";

import { Button } from "../components";

import { loginRequest, registerRequest } from "../types/dto";

const initValue = { username: "", email: "", password: "", confirmPassword: "" };
export const RegisterPage = () => {
  const [value, handleChange] = useForm<registerRequest>(initValue);

  const { execute, loading } = useAuth<loginRequest>("/auth/register", "/login");

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    await execute(value, "Register success");
  };

  return (
    <section className="flex items-center justify-center min-h-screen bg-background">
      <div className="w-full rounded-lg shadow bg-dark md:mt-0 sm:max-w-md xl:p-0">
        <div className="p-6 space-y-4 md:space-y-6 sm:p-8 text-light">
          <h1 className="text-xl font-bold leading-tight tracking-tight md:text-2xl">Create an account</h1>
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <label htmlFor="username" className="block mb-2 text-sm font-medium">
                Username
              </label>
              <input type="text" id="username" onChange={handleChange} placeholder="john_doe" className="input-form" required disabled={loading} />
            </div>
            <div className="mb-4">
              <label htmlFor="email" className="block mb-2 text-sm font-medium">
                Email
              </label>
              <input type="email" id="email" onChange={handleChange} placeholder="john_doe.5@gmail.com" className="input-form" required disabled={loading} />
            </div>
            <div className="mb-6">
              <label htmlFor="password" className="block mb-2 text-sm font-medium">
                Password
              </label>
              <input type="password" id="password" onChange={handleChange} placeholder="••••••••" className="input-form" required disabled={loading} />
            </div>
            <div className="mb-6">
              <label htmlFor="confirmPassword" className="block mb-2 text-sm font-medium">
                Confirm Password
              </label>
              <input type="password" id="confirmPassword" onChange={handleChange} placeholder="••••••••" className="input-form" required disabled={loading} />
            </div>
            <Button type="submit" className="w-full bg-primary py-2.5">
              {loading ? <div className="loader size-8"></div> : "Sign Up"}
            </Button>
            <p className="mt-4 text-sm font-light text-gray-400">
              Already have an account?{" "}
              <Link to="/login" className="font-medium text-secondary hover:underline">
                Login here
              </Link>
            </p>
          </form>
        </div>
      </div>
    </section>
  );
};
