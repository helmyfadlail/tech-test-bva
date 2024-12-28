import { ButtonProps } from "../types";

export const Button = ({ children, className, ...props }: ButtonProps) => {
  return (
    <button className={`text-sm text-light font-medium duration-300 shadow-lg rounded-lg ${className ?? ""}`} {...props}>
      {children}
    </button>
  );
};
