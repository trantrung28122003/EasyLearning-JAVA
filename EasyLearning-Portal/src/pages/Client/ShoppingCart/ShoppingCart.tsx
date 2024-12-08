import React, { useEffect, useState } from "react";
import ClientShared from "../Shared/ClientShared";
import { DoCallAPIWithToken } from "../../../services/HttpService";
import {
  BASE_URL_SHOPPING_CART,
  GET_COUPONS_BY_USER,
} from "../../../constants/API";
import { HTTP_OK } from "../../../constants/HTTPCode";
import CartItem from "./CartItem/CartItem";
import { useNavigate } from "react-router-dom";
import { formatCurrency } from "../../../hooks/useCurrency";

import "./ShoppingCart.css";
import DataLoader from "../../../components/lazyLoadComponent/DataLoader";
const ShoppingCart: React.FC = () => {
  const [shoppingCart, setShoppingCart] = useState<ShoppingCart>();
  const [errorMessage, setErrorMessage] = useState<string | null>(null); // Lưu thông báo lỗi
  const [appliedCoupon, setAppliedCoupon] = useState<string | null>(null);
  const [totalPriceDiscount, setTotalPriceDiscount] = useState(0);
  const [couponsByUser, setCouponsByUser] = useState<UserDiscountResponse[]>();
  const [isLoading, setIsLoading] = useState(true);

  const navigator = useNavigate();
  const doGetShoppingCart = () => {
    setIsLoading(true);
    DoCallAPIWithToken(BASE_URL_SHOPPING_CART, "get")
      .then((res) => {
        if (res.status === HTTP_OK) {
          const shoppingCart: ShoppingCart = res.data;
          setShoppingCart(shoppingCart);
          setTotalPriceDiscount(shoppingCart.totalPriceDiscount);
        }
      })
      .finally(() => setIsLoading(false));
  };

  const doGetCouponsByUser = () => {
    DoCallAPIWithToken(GET_COUPONS_BY_USER, "get").then((res) => {
      if (res.status === HTTP_OK) {
        setCouponsByUser(res.data.result);
      }
    });
  };

  const applyCoupon = () => {
    const couponCode = (
      document.getElementById("coupon-code") as HTMLInputElement
    )?.value;

    if (!couponCode.trim()) {
      setErrorMessage("Vui lòng nhập mã giảm giá!");
      return;
    }
    const ApplyDiscountRequest = {
      shoppingCartId: shoppingCart?.id,
      totalPriceDiscount: shoppingCart?.totalPriceDiscount,
      discountCode: couponCode,
    };
    setIsLoading(true);
    DoCallAPIWithToken(
      `${BASE_URL_SHOPPING_CART}/applyDiscount`,
      "POST",
      ApplyDiscountRequest
    )
      .then((res) => {
        if (res.status === HTTP_OK) {
          if (res.data.message) {
            setErrorMessage(res.data.message);
          } else {
            setErrorMessage(null);
            setAppliedCoupon(couponCode);
            console.log(res.data.result);
            setTotalPriceDiscount(res.data.result.priceDiscount);
            localStorage.setItem("discountCode", couponCode);
          }
        } else {
          setErrorMessage("Có lỗi xảy ra khi áp dụng mã giảm giá!");
          setAppliedCoupon(null);
        }
      })
      .catch((error) => {
        setErrorMessage("Có lỗi xảy ra khi áp dụng mã giảm giá!");
        setAppliedCoupon(null);
      })
      .finally(() => setIsLoading(false));
  };

  const removeCoupon = () => {
    setErrorMessage(null);
    setAppliedCoupon(null);
    setTotalPriceDiscount(shoppingCart?.totalPriceDiscount || 0);
    localStorage.removeItem("discountCode");
  };
  const [showDiscountModal, setShowDiscountModal] = useState(false);

  useEffect(() => {
    setTimeout(() => {
      window.scrollTo(0, 0);
    }, 0);
    doGetShoppingCart();
    doGetCouponsByUser();
  }, []);

  return (
    <ClientShared>
      <DataLoader isLoading={isLoading} />
      <div
        className="container"
        style={{ minHeight: "100vh", marginTop: "20px" }}
      >
        <div className="row d-flex justify-content-center align-items-center ">
          <div className="col">
            <p>
              <span className="h2">Giỏ hàng: </span>
              <span id="cartItemCount" className="h4">
                có {shoppingCart?.shoppingCartItemResponses.length} sản phẩm
                <br />
              </span>
            </p>
            {shoppingCart?.shoppingCartItemResponses.map((item) => {
              return <CartItem item={item} />;
            })}

            <div className="card mb-5">
              <div className="card-body p-4">
                <div className="float-end">
                  <p className="mb-0 me-5 d-flex align-items-center">
                    <span id="order-total">
                      <strong style={{ fontSize: "22px" }}>
                        Tổng tiền: {formatCurrency(totalPriceDiscount)}₫
                      </strong>
                      <span
                        style={{
                          fontSize: "18px",
                          color: "#888",
                          textDecoration: "line-through",
                          marginLeft: "12px",
                        }}
                      >
                        {formatCurrency(shoppingCart?.totalPrice)}₫
                      </span>
                    </span>{" "}
                    <span className="lead fw-normal"></span>
                  </p>
                </div>
              </div>
            </div>
            <div className="d-flex align-items-center">
              <div className="input-group" style={{ width: "300px" }}>
                <input
                  id="coupon-code"
                  type="text"
                  className="form-control"
                  placeholder="Nhập mã giảm giá..."
                  aria-label="Coupon code"
                  aria-describedby="apply-coupon"
                  onChange={() => setErrorMessage(null)}
                />
                <button
                  style={{ backgroundColor: "#2bc5d4", color: "white" }}
                  className="btn btn-outline-secondary ms-2"
                  type="button"
                  onClick={applyCoupon}
                  id="apply-coupon"
                >
                  Áp dụng
                </button>
              </div>

              <button
                className="btn btn-outline-secondary btn-show-discount me-2"
                onClick={() => setShowDiscountModal(true)}
              >
                Danh sách mã giảm giá
              </button>
            </div>

            {showDiscountModal && (
              <div
                className="modal-overlay"
                onClick={() => setShowDiscountModal(false)}
              >
                <div
                  className="modal-container"
                  onClick={(e) => e.stopPropagation()}
                >
                  <h4>Danh sách mã giảm giá dành cho bạn</h4>
                  <ul className="modal-list">
                    {couponsByUser &&
                      couponsByUser.map((coupon) => (
                        <li
                          key={coupon.discountCode}
                          className="modal-item"
                          onClick={() => {
                            (
                              document.getElementById(
                                "coupon-code"
                              ) as HTMLInputElement
                            ).value = coupon.discountCode;
                            setShowDiscountModal(false);
                            setErrorMessage(null);
                          }}
                        >
                          <span className="modal-item-text">
                            {coupon.discountCode}
                          </span>
                          <span className="modal-item-text">
                            {coupon.discountDescription}
                          </span>
                          {/*    */}
                        </li>
                      ))}
                  </ul>
                  <button
                    className="modal-close-btn"
                    onClick={() => setShowDiscountModal(false)}
                  >
                    Đóng
                  </button>
                </div>
              </div>
            )}

            <div
              className="mb-3"
              style={{ width: "300px", marginLeft: "12px" }}
            >
              {errorMessage && (
                <p style={{ color: "red", marginTop: "8px" }}>{errorMessage}</p>
              )}
              {appliedCoupon && !errorMessage && (
                <div
                  style={{ marginTop: "8px" }}
                  // className="mb-0 me-5 d-flex align-items-center"
                >
                  <p style={{ color: "green" }}>
                    Mã giảm giá <strong>{appliedCoupon}</strong> đã được áp
                    dụng!
                  </p>
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={removeCoupon}
                  >
                    Xoá mã
                  </button>
                </div>
              )}
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

              {(shoppingCart?.shoppingCartItemResponses?.length ?? 0) > 0 && (
                <a>
                  <button
                    type="button"
                    className="btn btn-primary btn-lg"
                    onClick={() => {
                      navigator("/checkout", {
                        state: {
                          totalPriceDiscount, // Truyền tổng tiền đã giảm
                        },
                      });
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
