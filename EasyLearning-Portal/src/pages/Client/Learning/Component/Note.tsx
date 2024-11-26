import React from "react";

const Note: React.FC = () => {
  return (
    <>
      <div className="p-3">
        <article className="card bg-light mb-3">
          <header className="card-header border-0 bg-transparent d-flex align-items-center">
            <button
              className="btn btn-primary note-button me-3"
              style={{ padding: "0 28px", borderRadius: "16px" }}
            >
              00:00:00
            </button>
            <span
              className="fw-semibold note-link"
              style={{ cursor: "pointer" }}
            >
              Tên phần đào tạo
            </span>
            <div className="dropdown ms-auto">
              <button
                className="btn btn-link text-decoration-none"
                type="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <i className="fas fa-ellipsis-v"></i>
              </button>
              <ul className="dropdown-menu">
                <li>
                  <button className="dropdown-item">Sửa lại ghi chú</button>
                </li>
                <li>
                  <button className="dropdown-item">Xóa ghi chú</button>
                </li>
              </ul>
            </div>
          </header>

          {/* <header className="card-header border-0 bg-transparent">
            <span className="ms-3 text-muted small">Tên sự kiện</span>
          </header> */}
          <footer className="card-footer bg-white border-0 py-1 px-3">
            <div className="card-body py-2 px-3" style={{ fontSize: "18px" }}>
              Nội dung ghi chú
            </div>
          </footer>
        </article>
      </div>

      <div className="modal-delete custom-modal-delete d-none">
        <div className="modal-content delete-confirmation-form">
          <p>Xóa ghi chú này?</p>
          <div className="button-container">
            <button className="btn btn-primary">Xóa</button>
            <button className="btn btn-secondary">Hủy</button>
          </div>
        </div>
      </div>
    </>
  );
};

export default Note;
