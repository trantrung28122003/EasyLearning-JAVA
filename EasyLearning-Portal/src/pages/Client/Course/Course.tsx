import React, { useEffect, useState } from "react";
import { GET_ALL_CATEGORY } from "../../../constants/API";
import { HTTP_OK } from "../../../constants/HTTPCode";
import { Category } from "../../../model/Category";
import { DoCallAPIWithOutToken } from "../../../services/HttpService";
import ClientShared from "../Shared/ClientShared";
import { ApplicationResponse } from "../../../model/BaseResponse";
import Card from "../../../components/card/Card";

const Course: React.FC = () => {
  const [categories, setCategories] = useState<Category[]>([]);
  const doCallGetAllCourseByCategory = () => {
    DoCallAPIWithOutToken(GET_ALL_CATEGORY, "get").then((res) => {
      if (res.status === HTTP_OK) {
        const response: ApplicationResponse<Category[]> = res.data;
        setCategories(response.result);
      }
    });
  };
  useEffect(() => {
    doCallGetAllCourseByCategory();
  }, []);
  return (
    <ClientShared>
      <div className="container">
        {categories.map((category, index) => {
          return (
            <div className="container-xxl py-5" key={index}>
              <div className="container">
                <div className="text-center wow fadeInUp" data-wow-delay="0.1s">
                  <h6 className="section-title bg-white text-center text-primary px-3">
                    Các Khóa Học
                  </h6>
                  <h1 className="mb-5">
                    Các Khóa Học của {category.categoryName}
                  </h1>
                </div>
                <div className="row g-4 justify-content-center">
                  {category.courses.map((course, i) => {
                    return <Card course={course} key={i} />;
                  })}
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </ClientShared>
  );
};

export default Course;
