import { useState } from "react";

import Cookies from "universal-cookie";

import toast from "react-hot-toast";
import { requestApi } from "../utils";

export const usePostApi = <T>(method: "GET" | "POST" | "PATCH" | "DELETE", path: string) => {
  const [loading, setLoading] = useState<boolean>();
  const [error, setError] = useState<string>();

  const cookies = new Cookies(null, { path: "/" });

  const execute = async (body: T, message: string) => {
    setLoading(true);
    await requestApi({
      path,
      method,
      body,
      options: {
        headers: {
          "X-API-TOKEN": `${cookies.get("X-API-TOKEN")}`,
          "Access-Control-Allow-Origin": "*",
          "Content-Type": "multipart/form-data",
        },
      },
    })
      .then(() => {
        toast.success(message);
        window.location.reload();
      })
      .catch((error) => {
        toast.error(error.response.data.errors || "There was an error");
        setError(error instanceof Error ? error.message : "There was an error");
      })
      .finally(() => {
        setLoading(false);
      });
  };

  return { loading, error, execute };
};
