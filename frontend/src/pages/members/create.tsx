import { useForm, usePostApi } from "../../hooks";

import { Button, Img, Modal } from "../../components";

import { createMemberRequest, pictureUrlRequest } from "../../types/dto";
import React from "react";

const initPictureUrl = { file: null, name: "", preview: "" };
const initValue = { name: "", position: "", superior: "" };

export const CreateModal = ({ isVisible, onClose }: { isVisible: boolean; onClose: () => void }) => {
  const [formData, handleChange] = useForm<createMemberRequest>(initValue);
  const [pictureUrl, setPictureUrl] = React.useState<pictureUrlRequest>(initPictureUrl);

  const { execute, loading } = usePostApi<createMemberRequest>("POST", "/members");

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
    const body = { name: formData.name, position: formData.position, superior: formData.superior, pictureUrl: pictureUrl.file };
    execute(body, "Member has been created successfully");
  };

  return (
    <Modal isVisible={isVisible} onClose={onClose}>
      <h1 className="mb-6 text-2xl font-semibold text-light">Create Member</h1>
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
          <label className="text-sm text-light whitespace-nowrap">{!pictureUrl.file ? "Please select a file" : pictureUrl.name}</label>
          <small className="absolute top-0 right-0 flex items-center w-24 h-full px-2 bg-gray-600 text-gray whitespace-nowrap">Max. (2mb)</small>
        </div>
        <div className="w-full mb-6">{pictureUrl.file && <Img src={pictureUrl.preview} alt="preview image" className="mx-auto rounded-lg aspect-square w-60" height={240} width={240} />}</div>
        <Button type="submit" className="bg-green-500 hover:bg-green-600 px-4 sm:px-8 py-1 sm:py-2.5">
          {loading ? <div className="loader size-8"></div> : "Submit"}
        </Button>
      </form>
    </Modal>
  );
};
