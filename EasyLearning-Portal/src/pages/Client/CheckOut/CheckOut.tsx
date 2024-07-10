import React, { useEffect, useState } from "react";
import ClientShared from "../Shared/ClientShared";
import { DoCallAPIWithToken } from "../../../services/HttpService";
import {
  BASE_URL_SHOPPING_CART,
  DO_PAYMENT_MOMO,
} from "../../../constants/API";
import { HTTP_OK } from "../../../constants/HTTPCode";
import style from "./CheckOut.module.css";

const CheckOut: React.FC = () => {
  const [shoppingCart, setShoppingCart] = useState<ShoppingCart>();
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
        amount: shoppingCart?.totalPrice?.toString() + "000" || "0",
        note: "",
      };
      DoCallAPIWithToken(DO_PAYMENT_MOMO, "post", payload).then((res) => {
        if (res.status === HTTP_OK) {
          console.log(res.data);
          //window.open(res.data, "_newtab");
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
      <div className="snippet-body">
        <div className="py-5 text-center ">
          <h2 style={{ color: "#06bbcc" }}>Phương thức thanh toán</h2>
        </div>

        <div className={style.Order}>
          <div className="col-md-5 col-lg-4 order-md-last">
            <h4>
              <span className="text-primary">Giỏ hàng</span>
            </h4>
            <ul className="list-group mb-3">
              {shoppingCart &&
                shoppingCart?.shoppingCartItems.map((item, index) => {
                  return (
                    <li
                      key={index}
                      className="list-group-item d-flex justify-content-between lh-sm"
                      style={{ width: "500px" }}
                    >
                      <div>
                        <h6 className="my-0">{item.cartItemName}</h6>
                        <small className="text-muted"></small>
                      </div>
                      <span className="text-muted">
                        {item.cartItemPrice}.000 VNĐ
                      </span>
                    </li>
                  );
                })}

              <li
                className="list-group-item d-flex justify-content-between"
                style={{ width: "500px" }}
              >
                <span>Tổng</span>
                <strong>{shoppingCart?.totalPrice}.000 VND</strong>
              </li>
            </ul>
          </div>
          <h4 className="mb-3" style={{ color: "#06bbcc" }}>
            Địa chỉ thanh toán
          </h4>
          <div>
            <div className="col-sm-6">
              <label
                htmlFor="firstName"
                className="form-label"
                style={{ color: "#06bbcc" }}
              >
                Ghi chú
              </label>
              <input
                type="text"
                className="form-control"
                style={{ width: "500px" }}
                id="firstName"
                placeholder=""
                value=""
              />
            </div>
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
