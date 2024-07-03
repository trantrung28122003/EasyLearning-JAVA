import React from "react";

const CustomSpinner: React.FC = () => {
  return (
    <div
      id="spinner"
      className="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center"
    >
      <div
        className="spinner-border text-primary"
        style={{ width: "3rem", height: "3rem" }}
        role="status"
      >
        <span className="sr-only">Đang Tải...</span>
      </div>
    </div>
  );
};

export default CustomSpinner;
