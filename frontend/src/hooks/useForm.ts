import { useState, ChangeEvent, Dispatch, SetStateAction } from "react";

export const useForm = <T>(initValues: T): [T, (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => void, Dispatch<SetStateAction<T>>] => {
  const [values, setValues] = useState<T>(initValues);

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>): void => {
    const { id, value } = e.target;
    setValues((prevValues) => ({ ...prevValues, [id]: value }));
  };

  return [values, handleChange, setValues];
};
