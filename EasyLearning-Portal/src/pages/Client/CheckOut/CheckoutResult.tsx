import { useSearchParams } from "react-router-dom";
import ClientShared from "../Shared/ClientShared";
import { CONFIRM_PAYMENT } from "../../../constants/API";
import { HTTP_OK } from "../../../constants/HTTPCode";
import { DoCallAPIWithToken } from "../../../services/HttpService";
import { useEffect } from "react";

const CheckoutResult = () => {
  let [searchParams] = useSearchParams();
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
  console.log(request);
  const doCallConfirmPayment = () => {
    DoCallAPIWithToken(CONFIRM_PAYMENT, "post", request).then((res) => {
      if (res.status === HTTP_OK) {
        console.log(res.data);
        window.location.href = res.data;
      }
    });
  };

  useEffect(() => {
    doCallConfirmPayment();
  }, []);
  return (
    <ClientShared>
      <div>Thanh toán thành công</div>
    </ClientShared>
  );
};

export default CheckoutResult;
