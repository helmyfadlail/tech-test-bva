import { useNavigate } from "react-router-dom";
import { Button } from "../components";

export const NotFound = () => {
  const navigate = useNavigate();

  return (
    <div className="flex flex-col items-center justify-center min-h-screen text-light bg-background">
      <h1 className="mb-4 text-6xl font-bold">404</h1>
      <p className="mb-6 text-xl">Oops! The page you're looking for doesn't exist.</p>
      <Button onClick={() => navigate("/")} className="px-6 py-3 bg-primary hover:bg-primary/80">
        Go Home
      </Button>
    </div>
  );
};
