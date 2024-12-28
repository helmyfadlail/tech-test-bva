import * as React from "react";

import { motion } from "framer-motion";

import { RxCross1 } from "react-icons/rx";

import { ModalProps } from "../types";

export const Modal = ({ isVisible, onClose, children }: ModalProps) => {
  React.useEffect(() => {
    if (isVisible) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "auto";
    }
    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isVisible]);

  if (!isVisible) return null;

  return (
    <div className="fixed top-0 left-0 flex items-center justify-center w-full h-full min-h-screen p-4 bg-black bg-opacity-50 z-1000">
      <motion.div
        className="relative w-full max-w-lg px-4 pb-4 mx-auto overflow-y-auto rounded-lg shadow-lg scrollbar pt-14 max-h-custom-modal bg-dark md:p-10"
        initial={{ opacity: 0, scale: 0.8 }}
        animate={{ opacity: 1, scale: 1 }}
        exit={{ opacity: 0, scale: 0.8 }}
        transition={{ duration: 0.3, ease: "easeInOut" }}
      >
        <button className="btn-cross-border group" onClick={onClose}>
          <RxCross1 size={20} className="text-red-500 group-hover:text-light" />
        </button>
        {children}
      </motion.div>
    </div>
  );
};