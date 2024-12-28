import * as React from "react";
import { CreateModal, DeleteModal, UpdateModal } from "./members";
import { Button, Dropdown, Img, Pagination } from "../components";
import { useGetApi, usePostApi } from "../hooks";

import { useDebounce } from "use-debounce";
import { memberResponse, responseData } from "../types/dto";
import Cookies from "universal-cookie";

const categoriesSearch = [
  { display: "Name", value: "name" },
  { display: "Position", value: "position" },
  { display: "Superior", value: "superior" },
];

export const HomePage = () => {
  const [category, setCategory] = React.useState<string>("name");
  const [searchTerm, setSearchTerm] = React.useState<string>("");
  const [currentPage, setCurrentPage] = React.useState<number>(1);

  const cookies = new Cookies(null, { path: "/" });

  const [debouncedSearchTerm] = useDebounce(searchTerm, 500);

  const params = React.useMemo(() => ({ [category]: debouncedSearchTerm }), [category, debouncedSearchTerm]);

  const { response: members } = useGetApi<responseData<memberResponse[]>>({ path: "/members", params, page: currentPage - 1 });
  const { execute, loading } = usePostApi("DELETE", "/auth/logout");

  const [createModal, setCreateModal] = React.useState<boolean>(false);

  const handleFiltered = (value: string) => {
    setCategory(value);
  };

  const handleLogout = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    execute({}, "Logout success");
    cookies.remove("X-API-TOKEN");
    window.location.href = "/login";
  };

  return (
    <div className="min-h-screen bg-background">
      {/* Navbar */}
      <nav className="flex items-center justify-between p-4 text-light bg-primary">
        <h1 className="text-lg font-semibold">Manage Members Organization</h1>
        <div>
          <span className="mr-4">{cookies.get("user")}</span>
          <Button type="button" onClick={handleLogout} className="px-6 py-2 bg-red-500 hover:bg-red-600">
            {loading ? <div className="loader size-6"></div> : "Logout"}
          </Button>
        </div>
      </nav>

      {/* Table Section */}
      <div className="p-6 space-y-8">
        {/* Add Button */}
        <div className="flex justify-end mb-4">
          <Button onClick={() => setCreateModal(true)} className="px-4 sm:px-8 py-1 sm:py-2.5 bg-green-500 hover:bg-green-600">
            Add
          </Button>
          <CreateModal isVisible={createModal} onClose={() => setCreateModal(false)} />
        </div>

        <div className="flex mb-4">
          <Dropdown data={categoriesSearch} className="top-12 min-w-44" handleFiltered={handleFiltered} parentClassName="min-w-32" selectedValue={category} />
          <div className="relative w-full">
            <input
              onChange={(e) => setSearchTerm(e.target.value)}
              type="search"
              id="search-dropdown"
              className="input-search"
              placeholder="Search name, position or superior by click categories first..."
              required
            />
            <button type="submit" className="btn-search">
              <span className="">Search</span>
            </button>
          </div>
        </div>
        {/* Table */}
        <div className="w-full overflow-x-auto text-light scrollbar">
          <table className="w-full min-w-full overflow-hidden border border-gray-600 rounded-lg bg-dark">
            <thead>
              <tr>
                <th className="px-4 py-2 text-left border-b">Picture</th>
                <th className="px-4 py-2 text-left border-b">Name</th>
                <th className="px-4 py-2 text-left border-b">Position</th>
                <th className="px-4 py-2 text-left border-b">Superior</th>
                <th className="px-4 py-2 text-left border-b">Actions</th>
              </tr>
            </thead>
            <tbody>
              {members?.data.map((member) => (
                <tr key={member.id} className="hover:bg-dark">
                  <td className="px-4 py-2 border-b">
                    <Img src={member.pictureUrl} alt={member.name} className="rounded-full w-14 aspect-square" height={56} width={56} />
                  </td>
                  <td className="px-4 py-2 border-b">{member.name}</td>
                  <td className="px-4 py-2 border-b">{member.position}</td>
                  <td className="px-4 py-2 border-b">{member.superior}</td>
                  <td className="px-4 py-2 border-b">
                    <UpdateModal id={member.id} />
                    <DeleteModal id={member.id} name={member.name} />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <Pagination page={currentPage} setPage={setCurrentPage} totalPage={members?.paging.totalPage || 0} isNumbering />
      </div>
    </div>
  );
};
