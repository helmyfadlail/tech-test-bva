import * as React from "react";

import { useForm, useGetApi, usePostApi, useToggleState } from "../../hooks";

import { Button, Img, Modal } from "../../components";

import { updateMemberRequest, memberResponse, pictureUrlRequest, responseData } from "../../types/dto";

const initPictureUrl = { file: null, name: "", preview: "" };
const initValue = { id: "", name: "", position: "", superior: "" };

export const UpdateModal = ({ id }: { id: string }) => {
  const [_, modal, toggleModal] = useToggleState();

  const [pictureUrl, setPictureUrl] = React.useState<pictureUrlRequest>(initPictureUrl);
  const [formData, handleChange, setFormData] = useForm<updateMemberRequest>(initValue);

  const { response: memberResponse } = useGetApi<responseData<memberResponse>>({ path: `/members/${id}` });
  const { execute, loading } = usePostApi<updateMemberRequest>("PATCH", `/members/${id}`);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files ? e.target.files[0] : null;

    if (file) {
      const preview = URL.createObjectURL(file);
      setPictureUrl({ file, name: file.name, preview });
    } else {
      setPictureUrl({ file: null, name: "", preview: "" });
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const body = { id, name: formData.name, position: formData.position, superior: formData.superior, pictureUrl: pictureUrl.file };
    execute(body, "Member has been created successfully");
  };

  React.useEffect(() => {
    if (memberResponse?.data) {
      setFormData({
        id,
        name: memberResponse.data.name,
        position: memberResponse.data.position,
        superior: memberResponse.data.superior,
      });
      setPictureUrl({
        preview: memberResponse.data.pictureUrl,
        file: null,
        name: memberResponse.data.pictureUrl,
      });
    }
  }, [memberResponse]);

  return (
    <>
      <Button onClick={toggleModal} className="px-4 py-1 mr-2 bg-cyan-500 hover:bg-cyan-600">
        Edit
      </Button>
      <Modal isVisible={modal} onClose={toggleModal}>
        <h1 className="mb-6 text-2xl font-semibold">Update User</h1>
        <form onSubmit={handleSubmit} className="text-light">
          <div className="mb-4">
            <label htmlFor="name" className="block mb-2 text-sm font-medium">
              Name
            </label>
            <input type="text" id="name" value={formData.name} onChange={handleChange} className="input-form" disabled={loading} />
          </div>
          <div className="mb-4">
            <label htmlFor="position" className="block mb-2 text-sm font-medium">
              Position
            </label>
            <input type="text" id="position" value={formData.position} onChange={handleChange} className="input-form" disabled={loading} />
          </div>
          <div className="mb-4">
            <label htmlFor="superior" className="block mb-2 text-sm font-medium">
              Superior
            </label>
            <input type="text" id="superior" value={formData.superior} onChange={handleChange} className="input-form" disabled={loading} />
          </div>
          <div className="relative flex flex-row items-center mb-4 overflow-hidden bg-gray-600 border border-gray-500 rounded-lg">
            <input type="file" id="images" hidden accept="image/*" onChange={handleFileChange} />
            <label htmlFor="images" className="file-label">
              Choose file
            </label>
            <label className="text-sm text-light whitespace-nowrap">{!pictureUrl.name ? "Please select a file" : pictureUrl.name}</label>
            <small className="absolute top-0 right-0 flex items-center w-24 h-full px-2 bg-gray-600 text-gray whitespace-nowrap">Max. (2mb)</small>
          </div>
          <div className="w-full mb-6">{pictureUrl.preview && <Img src={pictureUrl.preview} alt="preview image" className="mx-auto rounded-lg aspect-square w-60" height={240} width={240} />}</div>
          <Button type="submit" className="bg-cyan-500 hover:bg-cyan-600 px-4 sm:px-8 py-1 sm:py-2.5">
            {loading ? <div className="loader size-8"></div> : "Submit"}
          </Button>
        </form>
      </Modal>
    </>
  );
};
