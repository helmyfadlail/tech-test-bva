import { useEffect, useState } from "react";

import { requestApi } from "../utils";
import Cookies from "universal-cookie";

interface UseGetParamsProps {
  path: string;
  params?: Record<string, any>;
  page?: number;
  size?: number;
}

export const useGetApi = <T>({ path, params, page, size }: UseGetParamsProps) => {
  const [response, setResponse] = useState<T | null>();
  const [loading, setLoading] = useState<boolean>();
  const [error, setError] = useState<string>();

  const cookies = new Cookies(null, { path: "/" });

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      await requestApi({
        path,
        method: "GET",
        options: {
          headers: {
            "X-API-TOKEN": `${cookies.get("X-API-TOKEN")}`,
            "Access-Control-Allow-Origin": "*",
          },
          params: {
            ...params,
            page,
            size,
          },
        },
      })
        .then((response) => setResponse(response.data))
        .catch((error) => setError(error instanceof Error ? error.message : "There was an error where fetching"))
        .finally(() => {
          setLoading(false);
        });
    };

    fetchData();
  }, [path, params, page, size]);

  return { response, error, loading };
};
