import React, { useEffect, useState } from "react";
import ClientShared from "../Shared/ClientShared";
import { DoCallAPIWithToken } from "../../../services/HttpService";
import { BASE_URL_SHOPPING_CART } from "../../../constants/API";
import { HTTP_OK } from "../../../constants/HTTPCode";
import CartItem from "./CartItem/CartItem";
import { useNavigate } from "react-router-dom";

const ShoppingCart: React.FC = () => {
  const [shoppingCart, setShoppingCart] = useState<ShoppingCart>();
  const navigator = useNavigate();
  const applyCoupon = () => {};
  const doGetShoppingCart = () => {
    DoCallAPIWithToken(BASE_URL_SHOPPING_CART, "get").then((res) => {
      if (res.status === HTTP_OK) {
        const shoppingCart: ShoppingCart = res.data;
        setShoppingCart(shoppingCart);
      }
    });
  };

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat("vi-VN").format(value * 1000);
  };

  useEffect(() => {
    setTimeout(() => {
      window.scrollTo(0, 0);
    }, 0);

    doGetShoppingCart();
  }, []);
  return (
    <ClientShared>
      <div className="container" style={{ minHeight: "100vh" }}>
        <div className="row d-flex justify-content-center align-items-center ">
          <div className="col">
            <p>
              <span className="h2">Giỏ hàng: </span>
              <span id="cartItemCount" className="h4">
                có {shoppingCart?.shoppingCartItems.length} sản phẩm
                <br />
              </span>
            </p>
            {shoppingCart?.shoppingCartItems.map((item) => {
              return <CartItem item={item} />;
            })}

            <div className="card mb-5">
              <div className="card-body p-4">
                <div className="float-end">
                  <p className="mb-0 me-5 d-flex align-items-center">
                    <span id="order-total">
                      <strong style={{ fontSize: "24px" }}>
                        Tổng tiền:{" "}
                        {formatCurrency(shoppingCart?.totalPrice || 0)}{" "}
                        <span>VNĐ</span>
                      </strong>
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
            <div
              className="d-flex justify-content-end"
              style={{ marginBottom: "28px" }}
            >
              <a>
                <button
                  onClick={() => {
                    navigator("/courses/search");
                  }}
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
                  Tiếp tục mua hàng
                </button>
              </a>

              {(shoppingCart?.shoppingCartItems?.length ?? 0) > 0 && (
                <a>
                  <button
                    type="button"
                    className="btn btn-primary btn-lg"
                    onClick={() => {
                      navigator("/checkout");
                    }}
                  >
                    <i className="fas fa-shopping-cart"></i> Thanh toán
                  </button>
                </a>
              )}
            </div>
            <div id="error-message" className="text-danger"></div>
          </div>
        </div>
      </div>
    </ClientShared>
  );
};

export default ShoppingCart;
