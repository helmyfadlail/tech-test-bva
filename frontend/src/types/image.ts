export interface ImageProps {
  src: string;
  alt: string;
  width: number;
  height: number;
  className?: string;
  loading?: "lazy" | "eager";
  sizes?: string;
  srcSet?: string;
}
