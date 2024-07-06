import React, { ReactNode } from "react";
import Navbar from "../../../components/navbar/Navbar";
import Footer from "../../../components/footer/Footer";
interface ClientProps {
  component: ReactNode;
}

const ClientShared: React.FC<ClientProps> = ({ component }) => {
  return (
    <>
      <Navbar />
      {component}
      <Footer />
    </>
  );
};

export default ClientShared;
