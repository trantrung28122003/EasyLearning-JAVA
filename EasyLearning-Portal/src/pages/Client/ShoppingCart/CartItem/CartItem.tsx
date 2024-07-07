import React from "react";
import style from "./CartItem.module.css";
const CartItem: React.FC = () => {
  return (
    <>
      <div className={`card mb-4 ${style.item_wrapper}`}>
        <div className="card-body p-4">
          <div className="row align-items-center">
            <div className="col-md-2">
              <img className="img-fluid" alt="Generic placeholder image" />
            </div>
            <div className="col-md-4 d-flex justify-content-center">
              <div>
                <p className="small text-muted mb-4 pb-2">Tên khóa học</p>
                <p className="lead fw-normal mb-0"></p>
              </div>
            </div>
            <div className="col-md-2 d-flex justify-content-center">
              <div>
                <p className="small text-muted mb-4 pb-2">Giá tiền</p>
                <p id="price" className="lead fw-normal mb-0 "></p>
              </div>
            </div>

            <div className="col-md-2 d-flex justify-content-center">
              <div>
                <p
                  className={`small text-muted mb-4 pb-2 ${style.course_info}`}
                >
                  Thông tin khóa học{" "}
                </p>
                <button className="btn btn-primary">
                  <i className="fas fa-info-circle"></i> Thông tin
                </button>
              </div>
            </div>
            <div className="col-md-2 d-flex justify-content-center">
              <div>
                <p className="small text-muted mb-4 pb-2">Actions</p>
                <button className={`btn btn-danger ${style.btn_delete}`}>
                  <i className="fas fa-trash-alt"></i> Xóa
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default CartItem;
