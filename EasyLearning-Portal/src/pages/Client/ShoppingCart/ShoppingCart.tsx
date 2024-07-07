import React from "react";

const ShoppingCart: React.FC = () => {
  const applyCoupon = () => {};
  return (
    <div>
      <div className="container h-100">
        <div className="row d-flex justify-content-center align-items-center h-100">
          <div className="col">
            <p>
              <span className="h2">Giỏ hàng </span>
              <span id="cartItemCount" className="h4">
                t<br />
              </span>
            </p>
            <div className="card mb-5">
              <div className="card-body p-4">
                <div className="float-end">
                  <p className="mb-0 me-5 d-flex align-items-center">
                    <span id="order-total" className="small text-muted me-2">
                      Tổng tiền:
                    </span>{" "}
                    <span className="lead fw-normal"></span>
                  </p>
                </div>
              </div>
            </div>
            <div className="input-group mb-3" style={{ width: "300px" }}>
              <input
                id="coupon-code"
                type="text"
                className="form-control"
                placeholder="Nhập mã giảm giá..."
                aria-label="Coupon code"
                aria-describedby="apply-coupon"
              />
              <button
                style={{ backgroundColor: "#2bc5d4", color: "white" }}
                className="btn btn-outline-secondary ms-2"
                type="button"
                onClick={applyCoupon}
                id=" apply-coupon"
              >
                Áp dụng
              </button>
            </div>
            <div className="d-flex justify-content-end">
              <a asp-controller="CustomerCourses" asp-action="ListCourse">
                <button
                  type="button"
                  data-mdb-button-init
                  data-mdb-ripple-init
                  className="btn btn-light btn-lg me-2"
                  style={{ color: "#2bc5d4", border: "2px solid #2bc5d4" }}
                >
                  <i
                    className="fas fa-arrow-circle-right"
                    style={{ color: "#2bc5d4" }}
                  ></i>{" "}
                  tiếp tục mua hàng
                </button>
              </a>
              <a asp-controller="Order" asp-action="Create">
                <button
                  type="button"
                  data-mdb-button-init
                  data-mdb-ripple-init
                  className="btn btn-primary btn-lg"
                >
                  <i className="fas fa-shopping-cart"></i> Thanh toán
                </button>
              </a>
            </div>
            <div id="error-message" className="text-danger"></div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ShoppingCart;
