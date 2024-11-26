import React, { useEffect, useState } from "react";
import ClientShared from "../Shared/ClientShared";
import { DoCallAPIWithToken } from "../../../services/HttpService";
import {
  BASE_URL_SHOPPING_CART,
  DO_PAYMENT_MOMO,
} from "../../../constants/API";
import { HTTP_OK } from "../../../constants/HTTPCode";
import style from "./CheckOut.module.css";
import { formatCurrency } from "../../../hooks/useCurrency";
import { useLocation } from "react-router-dom";

const CheckOut: React.FC = () => {
  const [shoppingCart, setShoppingCart] = useState<ShoppingCart>();
  const location = useLocation();
  const { totalPriceDiscount } = location.state || {};
  const doGetShoppingCart = () => {
    DoCallAPIWithToken(BASE_URL_SHOPPING_CART, "get").then((res) => {
      if (res.status === HTTP_OK) {
        const shoppingCart: ShoppingCart = res.data;
        setShoppingCart(shoppingCart);
      }
    });
  };

  const doPayment = () => {
    if (shoppingCart) {
      const payload: MomoPaymentRequest = {
        amount: totalPriceDiscount.toString() || "0",
        note: "",
      };
      DoCallAPIWithToken(DO_PAYMENT_MOMO, "post", payload).then((res) => {
        if (res.status === HTTP_OK) {
          console.log(res.data);
          window.location.href = res.data;
        }
      });
    }
  };

  useEffect(() => {
    doGetShoppingCart();
  }, []);
  return (
    <ClientShared>
      <div className="snippet-body" style={{ marginBottom: "28px" }}>
        <div className="py-4 text-center ">
          <h2 style={{ color: "#06bbcc" }}>Phương thức thanh toán</h2>
        </div>

        <div className={style.Order}>
          <div className="col-md-5 col-lg-4 order-md-last">
            <h4>
              <span className="text-primary">Giỏ hàng</span>
            </h4>
            <ul className="list-group mb-3">
              {shoppingCart &&
                shoppingCart?.shoppingCartItemResponses.map((item, index) => {
                  return (
                    <li
                      key={index}
                      className="list-group-item d-flex justify-content-between lh-sm"
                      style={{ width: "500px" }}
                    >
                      <div>
                        <h5 className="my-0">{item.cartItemName}</h5>
                        <small className="text-muted"></small>
                      </div>
                      {item.cartItemPrice !== item.cartItemPriceDiscount ? (
                        <div>
                          <h6 className="my-0">
                            {formatCurrency(item.cartItemPriceDiscount)}₫
                          </h6>
                          <small
                            style={{ textDecoration: "line-through" }}
                            className="text-muted"
                          >
                            {formatCurrency(item.cartItemPrice)}₫
                          </small>
                        </div>
                      ) : (
                        <div>
                          <h6 className="my-0">
                            {formatCurrency(item.cartItemPrice)}₫
                          </h6>
                        </div>
                      )}
                    </li>
                  );
                })}

              <li
                className="list-group-item d-flex justify-content-between"
                style={{ width: "500px" }}
              >
                <div>
                  <h6 className="my-0">Tổng tiền</h6>
                  <span className="text-muted">Giảm giá</span>
                </div>
                <div>
                  <h6 className="my-0">
                    {formatCurrency(totalPriceDiscount ?? 0)}₫
                  </h6>
                  <small
                    style={{ textDecoration: "line-through" }}
                    className="text-muted"
                  >
                    {formatCurrency(shoppingCart?.totalPrice ?? 0)}₫
                  </small>
                </div>
              </li>
            </ul>
          </div>

          <div>
            <div className="form-group" hidden>
              <label
                asp-htmlFor="OrderTotalPrice"
                className="control-label"
              ></label>
              <input type="hidden" asp-htmlFor="OrderTotalPrice" />
              <span
                asp-validation-htmlFor="OrderTotalPrice"
                className="text-danger"
              ></span>
            </div>
            <hr className="my-4" />
            <h4 className="mb-3" style={{ color: "#06bbcc" }}>
              Hình thức thanh toán
            </h4>
            <div className="my-3">
              <div className="form-check">
                <input
                  id="credit"
                  name="paymentMethod"
                  type="radio"
                  className="form-check-input"
                  checked
                  required
                />
                <label className="form-check-label" htmlFor="credit">
                  Ví Momo
                </label>
              </div>
            </div>
            <hr className="my-4" />
            <button
              className="btn btn-lg"
              onClick={() => {
                doPayment();
              }}
              style={{
                backgroundColor: "#06bbcc",
                borderColor: "#06bbcc",
                color: "white",
                alignItems: "center",
                width: "500px",
              }}
            >
              Đi đến thanh toán
            </button>
          </div>
        </div>
      </div>
    </ClientShared>
  );
};

export default CheckOut;
