import * as React from "react";

import { usePostApi, useToggleState } from "../../hooks";

import { Button, Modal } from "../../components";

export const DeleteModal = ({ id, name }: { id: string; name: string }) => {
  const [_, modal, toggleModal] = useToggleState();

  const { execute, loading } = usePostApi("DELETE", `/members/${id}`);

  const handleDeleteMember = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    execute({}, `Member named ${name} was successfully deleted `);
  };

  return (
    <>
      <Button onClick={toggleModal} className="px-4 py-1 bg-red-500 hover:bg-red-600">
        Delete
      </Button>
      <Modal isVisible={modal} onClose={toggleModal}>
        <h2 className="text-xl font-semibold">Confirm Deletion</h2>
        <p className="mt-4">Are you sure you want to delete this {name} member?</p>
        <div className="mt-6">
          <Button type="button" onClick={handleDeleteMember} className="px-4 sm:px-8 py-1 sm:py-2.5 bg-red-500 hover:bg-red-600">
            {loading ? <div className="loader size-8"></div> : "Delete"}
          </Button>
        </div>
      </Modal>
    </>
  );
};
