import * as React from "react";

import { useGetApi, useToggleState } from "../../hooks";

import { Button, Img, Modal } from "../../components";

import { memberResponse, responseData } from "../../types/dto";

export const DetailModal = ({ id }: { id: string }) => {
  const [_, modal, toggleModal] = useToggleState();

  const [member, setMember] = React.useState<memberResponse>();

  const { response: memberResponse } = useGetApi<responseData<memberResponse>>({ path: `/members/${id}` });

  React.useEffect(() => {
    if (memberResponse?.data) {
      setMember({
        id,
        name: memberResponse.data.name,
        position: memberResponse.data.position,
        superior: memberResponse.data.superior,
        pictureUrl: memberResponse.data.pictureUrl,
      });
    }
  }, [memberResponse]);

  return (
    <>
      <Button onClick={toggleModal} className="px-4 py-1 mr-2 bg-orange-500 hover:bg-orange-600">
        Detail
      </Button>
      <Modal isVisible={modal} onClose={toggleModal}>
        <h1 className="mb-6 text-2xl font-semibold">Detail User</h1>
        <div className="text-light">
          <div className="mb-4">
            <label htmlFor="name" className="block mb-2 text-sm font-medium">
              Name
            </label>
            <input type="text" id="name" value={member?.name} className="input-form" disabled />
          </div>
          <div className="mb-4">
            <label htmlFor="position" className="block mb-2 text-sm font-medium">
              Position
            </label>
            <input type="text" id="position" value={member?.position} className="input-form" disabled />
          </div>
          <div className="mb-4">
            <label htmlFor="superior" className="block mb-2 text-sm font-medium">
              Superior
            </label>
            <input type="text" id="superior" value={member?.superior} className="input-form" disabled />
          </div>
          <div className="w-full mb-6">{member?.pictureUrl && <Img src={member?.pictureUrl} alt="preview image" className="mx-auto rounded-lg aspect-square w-60" height={240} width={240} />}</div>
        </div>
      </Modal>
    </>
  );
};
