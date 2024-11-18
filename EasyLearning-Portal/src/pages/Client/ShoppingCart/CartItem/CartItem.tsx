import React from "react";
import style from "./CartItem.module.css";
import { REMOVE_FROM_CART } from "../../../../constants/API";
import { DoCallAPIWithToken } from "../../../../services/HttpService";
import { HTTP_OK } from "../../../../constants/HTTPCode";
import { useNavigate } from "react-router-dom";
interface CartItemProps {
  item: ShoppingCartItem;
}
const CartItem: React.FC<CartItemProps> = ({ item }) => {
  const navigate = useNavigate();
  const removeCartItem = () => {
    const URL = REMOVE_FROM_CART + "/" + item.id;
    DoCallAPIWithToken(URL, "post").then((res) => {
      if (res.status === HTTP_OK) {
        window.location.reload();
      }
    });
  };
  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat("vi-VN").format(value * 1000);
  };
  const handleDetailCourse = (courseId: string) => {
    navigate("/course/" + courseId);
  };
  return (
    <>
      <div className={`card mb-4 ${style.item_wrapper}`}>
        <div className="card-body">
          <div className="row align-items-center">
            <div className="col-md-2">
              <img
                className="img-fluid"
                alt="Generic placeholder image"
                src={item.imageUrl}
              />
            </div>
            <div className="col-md-4 d-flex justify-content">
              <div>
                <p className="small text-muted mb-4">Tên khóa học</p>
                <p className="lead fw-normal mb-0">{item.cartItemName}</p>
              </div>
            </div>
            <div className="col-md-2 d-flex justify-content-center">
              <div>
                <p className="small text-muted mb-4">Giá tiền</p>
                <p id="price" className="lead fw-normal mb-0 ">
                  {formatCurrency(item?.cartItemPrice || 0)} <span>VNĐ</span>
                </p>
              </div>
            </div>

            <div className="col-md-2 d-flex justify-content-center">
              <div>
                <p className="small text-muted mb-4">Thông tin khóa học</p>
                <button
                  className="btn btn-primary"
                  onClick={() => handleDetailCourse(item.courseId)}
                >
                  <i className="fas fa-info-circle"></i> Thông tin
                </button>
              </div>
            </div>
            <div className="col-md-2 d-flex justify-content-center">
              <div>
                <button
                  className={`btn btn-danger ${style.btn_delete}`}
                  onClick={() => {
                    removeCartItem();
                  }}
                >
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
