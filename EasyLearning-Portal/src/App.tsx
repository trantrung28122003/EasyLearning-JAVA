import Footer from "./components/footer/Footer";
import Header from "./components/header/Header";
import Navbar from "./components/navbar/Navbar";
import ApplicationRoutes from "./routes/ApplicationRoutes";

const App = () => {
  return (
    <>
      <Navbar />
      <ApplicationRoutes />
      <Footer />
    </>
  );
};

export default App;
