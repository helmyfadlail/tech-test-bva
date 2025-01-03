import { useToggleState } from "../hooks";

import { PiCaretDownBold } from "react-icons/pi";

import { DropdownProps } from "../types";

export const Dropdown = ({ parentClassName, className, data, handleFiltered, selectedValue }: DropdownProps) => {
  const [ref, popover, togglePopover] = useToggleState(false);

  const selected = data?.find((item) => item.value === selectedValue);

  return (
    <span ref={ref} className={`dropdown ${parentClassName ?? ""} ${popover ? "ring-primary ring-2" : "border border-gray-600 ring-0"}`} onClick={togglePopover}>
      {selected?.display}
      <PiCaretDownBold size={14} className={`duration-300 absolute right-2 fill-light ${popover && "rotate-180"}`} />
      {popover && (
        <div className={`popover ${className ?? ""}`}>
          {data?.map((item, index) => (
            <button key={index} onClick={() => handleFiltered(item.value)} className="w-full px-4 py-2 text-start hover:bg-gray-600">
              {item.display}
            </button>
          ))}
        </div>
      )}
    </span>
  );
};
