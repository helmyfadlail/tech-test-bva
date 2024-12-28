import { useState } from "react";

import toast from "react-hot-toast";

import { requestApi } from "../utils";
import Cookies from "universal-cookie";

export const useAuth = <T>(path: string, redirectPath: string) => {
  const [loading, setLoading] = useState<boolean>();
  const [error, setError] = useState<string>();

  const cookies = new Cookies(null, { path: "/" });

  const execute = async (body: T, message: string) => {
    setLoading(true);
    await requestApi({ path, method: "POST", body })
      .then((response) => {
        toast.success(message);
        if (path === "/auth/login") {
          cookies.set("X-API-TOKEN", response.data.data.token);
        }
        setTimeout(() => {
          window.location.href = redirectPath;
        }, 2000);
      })
      .catch((error) => {
        toast.error(error.response?.data.errors || "There was an error");
        setError(error.response?.data.errors || "There was an error");
      })
      .finally(() => {
        setLoading(false);
      });
  };

  return { loading, error, execute };
};
