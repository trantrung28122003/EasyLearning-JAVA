import React from "react";
import ClientShared from "../Shared/ClientShared";

const Course: React.FC = () => {
  return (
    <ClientShared>
      <div className="container">
        {
          <div className="container-xxl py-5">
            <div className="container">
              <div className="text-center wow fadeInUp" data-wow-delay="0.1s">
                <h6 className="section-title bg-white text-center text-primary px-3">
                  Các Khóa Học
                </h6>
                <h1 className="mb-5">Các Khóa Học của Category</h1>
              </div>
              <div className="row g-4 justify-content-center"></div>
            </div>
          </div>
        }
      </div>
    </ClientShared>
  );
};

export default Course;
