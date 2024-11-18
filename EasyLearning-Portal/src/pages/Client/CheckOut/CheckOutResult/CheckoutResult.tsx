import { useSearchParams, useNavigate } from "react-router-dom";
import { CONFIRM_PAYMENT } from "../../../../constants/API";
import { HTTP_OK } from "../../../../constants/HTTPCode";
import { DoCallAPIWithToken } from "../../../../services/HttpService";
import { useEffect } from "react";

const CheckoutResult = () => {
  let [searchParams] = useSearchParams();
  const navigate = useNavigate(); // Khai báo hook useNavigate

  const request: ConfirmPaymentRequest = {
    partnerCode: searchParams.get("partnerCode") ?? "",
    accessKey: searchParams.get("accessKey") ?? "",
    requestId: searchParams.get("requestId") ?? "",
    amount: searchParams.get("amount") ?? "",
    orderId: searchParams.get("orderId") ?? "",
    orderInfo: searchParams.get("orderInfo") ?? "",
    orderType: searchParams.get("orderType") ?? "",
    transId: searchParams.get("transId") ?? "",
    message: searchParams.get("message") ?? "",
    localMessage: searchParams.get("localMessage") ?? "",
    responseTime: searchParams.get("responseTime") ?? "",
    errorCode: searchParams.get("errorCode") ?? "",
    payType: searchParams.get("payType") ?? "",
    extraData: searchParams.get("extraData") ?? "",
    signature: searchParams.get("signature") ?? "",
  };

  const doCallConfirmPayment = () => {
    DoCallAPIWithToken(CONFIRM_PAYMENT, "post", request).then((res) => {
      if (res.status === HTTP_OK) {
        if (res.data.result === "Payment successful") {
          navigate("/paymentSuccess");
        } else {
          navigate("/paymentFailure");
        }
      } else {
        navigate("/paymentFailure");
      }
    });
  };

  useEffect(() => {
    doCallConfirmPayment();
  }, []);

  return null;
};

export default CheckoutResult;
