import axios, { AxiosRequestConfig } from "axios";

interface AxiosProps {
  path: string;
  method: "GET" | "POST" | "PATCH" | "DELETE";
  body?: any;
  options?: AxiosRequestConfig;
}

export const baseUrlApi = "/api/api";

const axiosInstance = axios.create({ baseURL: baseUrlApi });

export const requestApi = async ({ path, method, body, options = {} }: AxiosProps) => {
  const config: AxiosRequestConfig = {
    url: path,
    method,
    data: body,
    withCredentials: true,
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    ...options,
  };

  return axiosInstance(config);
};
