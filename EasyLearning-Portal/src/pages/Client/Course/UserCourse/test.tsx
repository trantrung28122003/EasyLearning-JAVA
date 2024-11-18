import React, { useState } from "react";
import "./test.css";
import ClientShared from "../../Shared/ClientShared";

function FilterableResults() {
  const allProducts = [
    {
      name: "IT Fundamentals - Everything you need to know about IT",
      rating: 4.6,
      reviews: 6670,
      hours: 12.5,
      lectures: 166,
      price: "1,599,000₫",
    },
    {
      name: "CompTIA IT Fundamentals (FCO-U61) Complete Course & Exam",
      rating: 4.6,
      reviews: 1754,
      hours: 14,
      lectures: 175,
      price: "1,599,000₫",
    },
    {
      name: "Product 3",
      rating: 4.5,
      reviews: 1000,
      hours: 10,
      lectures: 100,
      price: "1,200,000₫",
    },
    {
      name: "Product 4",
      rating: 4.7,
      reviews: 800,
      hours: 8,
      lectures: 80,
      price: "900,000₫",
    },
    {
      name: "Product 5",
      rating: 4.8,
      reviews: 1500,
      hours: 9,
      lectures: 120,
      price: "1,000,000₫",
    },
    {
      name: "Product 6",
      rating: 4.3,
      reviews: 600,
      hours: 15,
      lectures: 200,
      price: "1,300,000₫",
    },
    {
      name: "Product 7",
      rating: 4.6,
      reviews: 1200,
      hours: 11,
      lectures: 140,
      price: "1,400,000₫",
    },
    {
      name: "Product 8",
      rating: 4.5,
      reviews: 900,
      hours: 10,
      lectures: 110,
      price: "1,250,000₫",
    },
    {
      name: "Product 9",
      rating: 4.7,
      reviews: 1400,
      hours: 16,
      lectures: 180,
      price: "1,600,000₫",
    },
    {
      name: "Product 10",
      rating: 4.9,
      reviews: 2000,
      hours: 18,
      lectures: 220,
      price: "2,000,000₫",
    },
    {
      name: "Product 11",
      rating: 4.4,
      reviews: 800,
      hours: 12,
      lectures: 150,
      price: "1,350,000₫",
    },
    {
      name: "Product 10",
      rating: 4.9,
      reviews: 2000,
      hours: 18,
      lectures: 220,
      price: "2,000,000₫",
    },
    {
      name: "Product 11",
      rating: 4.4,
      reviews: 800,
      hours: 12,
      lectures: 150,
      price: "1,350,000₫",
    },
    {
      name: "Product 10",
      rating: 4.9,
      reviews: 2000,
      hours: 18,
      lectures: 220,
      price: "2,000,000₫",
    },
    {
      name: "Product 11",
      rating: 4.4,
      reviews: 800,
      hours: 12,
      lectures: 150,
      price: "1,350,000₫",
    },
    {
      name: "Product 10",
      rating: 4.9,
      reviews: 2000,
      hours: 18,
      lectures: 220,
      price: "2,000,000₫",
    },
    {
      name: "Product 11",
      rating: 4.4,
      reviews: 800,
      hours: 12,
      lectures: 150,
      price: "1,350,000₫",
    },
    {
      name: "Product 10",
      rating: 4.9,
      reviews: 2000,
      hours: 18,
      lectures: 220,
      price: "2,000,000₫",
    },
    {
      name: "Product 11",
      rating: 4.4,
      reviews: 800,
      hours: 12,
      lectures: 150,
      price: "1,350,000₫",
    },
  ];

  const itemsPerPage = 5; // Số sản phẩm hiển thị trên mỗi trang
  const totalPages = Math.ceil(allProducts.length / itemsPerPage); // Tính số trang

  const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại

  const visiblePages = 1; // Number of pages to display before ellipsis

  const changePage = (page: number) => {
    if (page >= 1 && page <= totalPages) {
      setCurrentPage(page);
    }
  };

  // const [currentPage, setCurrentPage] = useState(1);
  // const visiblePages = 2; // Số trang hiển thị trước và sau trang hiện tại

  // Hàm chuyển trang
  const handleClick = (page: number) => {
    if (page >= 1 && page <= totalPages) {
      setCurrentPage(page);
    }
  };

  // Hàm lấy danh sách các trang để hiển thị
  const getPages = () => {
    const pages: (number | string)[] = [];

    // Thêm trang đầu tiên
    pages.push(1);

    // Thêm dấu "..." nếu có nhiều trang trước và sau trang hiện tại
    if (currentPage > visiblePages + 1) {
      pages.push("...");
    }

    // Thêm các trang gần trang hiện tại (ở giữa)
    for (
      let i = Math.max(currentPage - visiblePages, 2);
      i <= Math.min(currentPage + visiblePages, totalPages - 1);
      i++
    ) {
      pages.push(i);
    }

    // Thêm dấu "..." nếu có nhiều trang sau trang hiện tại
    if (currentPage < totalPages - visiblePages) {
      pages.push("...");
    }

    // Thêm trang cuối cùng
    if (totalPages > 1) {
      pages.push(totalPages);
    }

    return pages;
  };

  // Lấy các sản phẩm cho trang hiện tại
  const currentProducts = allProducts.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  const handleSortChange = (e: any) => {
    const { id, checked } = e.target;

    if (id === "relevance" && checked) {
      console.log("Sắp xếp theo liên quan nhất");
      // Thực hiện hành động sắp xếp theo liên quan nhất
    }
    if (id === "rating" && checked) {
      console.log("Sắp xếp theo đánh giá cao nhất");
      // Thực hiện hành động sắp xếp theo đánh giá cao nhất
    }
    if (id === "price-asc" && checked) {
      console.log("Sắp xếp theo giá thấp đến cao");
      // Thực hiện hành động sắp xếp theo giá thấp đến cao
    }
    if (id === "price-desc" && checked) {
      console.log("Sắp xếp theo giá cao đến thấp");
      // Thực hiện hành động sắp xếp theo giá cao đến thấp
    }
  };

  const [isFilterOpen, setIsFilterOpen] = useState(true);
  const [isRatingsOpen, setIsRatingsOpen] = useState(true);
  const [isLanguageOpen, setIsLanguageOpen] = useState(true);
  const [isSortByOpen, setIsSortByOpen] = useState(true);

  const toggleFilter = () => setIsFilterOpen(!isFilterOpen);
  const toggleRatings = () => setIsRatingsOpen(!isRatingsOpen);
  const toggleLanguage = () => setIsLanguageOpen(!isLanguageOpen);
  const toggleSortBy = () => setIsSortByOpen(!isSortByOpen);

  return (
    <ClientShared>
      <div className="container">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <div className="category-dropdown">
            <span className="category-link" style={{ marginLeft: "20px" }}>
              Thể loại
            </span>
            <div className="category-menu">
              <a href="#" className="dropdown-item">
                IT & Software
              </a>
              <a href="#" className="dropdown-item">
                Business
              </a>
              <a href="#" className="dropdown-item">
                Finance & Accounting
              </a>
              <a href="#" className="dropdown-item">
                Design
              </a>
              <a href="#" className="dropdown-item">
                Marketing
              </a>
            </div>
          </div>

          <div className="input-group">
            <span className="input-group-text">
              <i className="fas fa-search"></i>
            </span>
            <input
              type="text"
              className="form-control"
              placeholder="Tìm kiếm khóa học bất kì"
            />
            <button className="btn btn-primary">Tìm kiếm</button>
          </div>
        </div>
        <hr style={{ marginBottom: "120px" }} />
        <div className="mb-3">
          {" "}
          <strong> 28 kết quả từ "thanh tìm kiếm" </strong>{" "}
        </div>
        <div className="mb-3">
          <button
            className="btn btn-primary btn-filter"
            onClick={() => setIsFilterOpen(!isFilterOpen)}
          >
            {isFilterOpen ? "Thu bộ lọc" : "Hiện thêm bộ lọc"}
          </button>
        </div>

        <div className="row">
          {/* Phần bộ lọc */}
          {isFilterOpen && (
            <div className="col-md-3">
              <div className="filter-section">
                <div className="filter-item">
                  <div className="filter-header" onClick={toggleSortBy}>
                    <h5>Sắp xếp theo</h5>
                    <i
                      className={`fas ${
                        isSortByOpen ? "fa-chevron-up" : "fa-chevron-down"
                      }`}
                    ></i>
                  </div>
                  {isSortByOpen && (
                    <div className="filter-content">
                      <div className="form-check">
                        <input
                          className="form-check-input"
                          type="radio"
                          id="relevance"
                          name="sort"
                          onChange={() =>
                            console.log("Sắp xếp theo liên quan nhất")
                          }
                        />
                        <label className="form-check-label" htmlFor="relevance">
                          Liên quan nhất
                        </label>
                      </div>

                      <div className="form-check">
                        <input
                          className="form-check-input"
                          type="radio"
                          id="rating"
                          name="sort"
                          onChange={() =>
                            console.log("Sắp xếp theo đánh giá cao nhất")
                          }
                        />
                        <label className="form-check-label" htmlFor="rating">
                          Đánh giá cao nhất
                        </label>
                      </div>

                      {/* Radio Button: Giá thấp đến cao */}
                      <div className="form-check">
                        <input
                          className="form-check-input"
                          type="radio"
                          id="price-asc"
                          name="sort"
                          onChange={() =>
                            console.log("Sắp xếp theo giá thấp đến cao")
                          }
                        />
                        <label className="form-check-label" htmlFor="price-asc">
                          Giá thấp đến cao
                        </label>
                      </div>

                      {/* Radio Button: Giá cao đến thấp */}
                      <div className="form-check">
                        <input
                          className="form-check-input"
                          type="radio"
                          id="price-desc"
                          name="sort"
                          onChange={() =>
                            console.log("Sắp xếp theo giá cao đến thấp")
                          }
                        />
                        <label
                          className="form-check-label"
                          htmlFor="price-desc"
                        >
                          Giá cao đến thấp
                        </label>
                      </div>
                    </div>
                  )}
                </div>

                <div className="filter-item">
                  <div className="filter-header" onClick={toggleLanguage}>
                    <h5>Hình thức học</h5>
                    <i
                      className={`fas ${
                        isLanguageOpen ? "fa-chevron-up" : "fa-chevron-down"
                      }`}
                    ></i>
                  </div>
                  {isLanguageOpen && (
                    <div className="filter-content">
                      <div className="form-check">
                        <input
                          className="form-check-input"
                          type="radio"
                          name="course-type"
                          id="online-course"
                          onChange={() => console.log("Online course selected")}
                        />
                        <label
                          className="form-check-label"
                          htmlFor="online-course"
                        >
                          Online
                        </label>
                      </div>
                      <div className="form-check">
                        <input
                          className="form-check-input"
                          type="radio"
                          name="course-type"
                          id="offline-course"
                          onChange={() =>
                            console.log("Offline course selected")
                          }
                        />
                        <label
                          className="form-check-label"
                          htmlFor="offline-course"
                        >
                          Học trực tiếp
                        </label>
                      </div>
                    </div>
                  )}
                </div>

                <div className="filter-item">
                  <div className="filter-header" onClick={toggleRatings}>
                    <h5>Đánh giá người học</h5>
                    <i
                      className={`fas ${
                        isRatingsOpen ? "fa-chevron-up" : "fa-chevron-down"
                      }`}
                    ></i>
                  </div>
                  {isRatingsOpen && (
                    <div className="filter-content">
                      <div className="form-check">
                        <input
                          className="form-check-input"
                          type="radio"
                          name="rating"
                          id="rating-5"
                        />
                        <label className="form-check-label" htmlFor="rating-5">
                          {[...Array(5)].map((_, index) => (
                            <i
                              key={index}
                              className={`fas ${
                                index < 5 ? "fa-star text-warning" : "fa-star"
                              }`}
                              style={{ fontSize: "14px" }}
                            ></i>
                          ))}
                          <span style={{ marginLeft: "10px" }}>5.0 & up</span>
                        </label>
                      </div>

                      <div className="form-check">
                        <input
                          className="form-check-input"
                          type="radio"
                          name="rating"
                          id="rating-4"
                        />
                        <label className="form-check-label" htmlFor="rating-4">
                          {[...Array(5)].map((_, index) => (
                            <i
                              key={index}
                              className={`fas ${
                                index < 4 ? "fa-star text-warning" : "fa-star"
                              }`}
                              style={{ fontSize: "14px" }}
                            ></i>
                          ))}
                          <span style={{ marginLeft: "10px" }}>4.0 & up</span>
                        </label>
                      </div>
                      <div className="form-check">
                        <input
                          className="form-check-input"
                          type="radio"
                          name="rating"
                          id="rating-3"
                        />
                        <label className="form-check-label" htmlFor="rating-3">
                          {[...Array(5)].map((_, index) => (
                            <i
                              key={index}
                              className={`fas ${
                                index < 3 ? "fa-star text-warning" : "fa-star"
                              }`}
                              style={{ fontSize: "14px" }}
                            ></i>
                          ))}
                          <span style={{ marginLeft: "10px" }}>3.0 & up</span>
                        </label>
                      </div>
                      <div className="form-check">
                        <input
                          className="form-check-input"
                          type="radio"
                          name="rating"
                          id="rating-2"
                        />
                        <label className="form-check-label" htmlFor="rating-2">
                          {[...Array(5)].map((_, index) => (
                            <i
                              key={index}
                              className={`fas ${
                                index < 2 ? "fa-star text-warning" : "fa-star"
                              }`}
                              style={{ fontSize: "14px" }}
                            ></i>
                          ))}
                          <span style={{ marginLeft: "10px" }}>2.0 & up</span>
                        </label>
                      </div>
                    </div>
                  )}
                </div>
              </div>
            </div>
          )}

          {/* Phần hiển thị sản phẩm và phân trang */}
          <div
            className={isFilterOpen ? "col-md-9" : "col-md-12"}
            style={{ marginTop: "12px" }}
          >
            <div className="product-list">
              {currentProducts.map((product, index) => (
                <div
                  key={index}
                  className="mb-3 p-3 border rounded d-flex align-items-center justify-content-between"
                >
                  {/* Left side: Product Image */}
                  <div
                    className="product-image me-3 d-flex justify-content-center align-items-center"
                    style={{ width: "180px" }}
                  >
                    <img
                      src="https://easylearning.blob.core.windows.net/images-videos/aspnetcore.jpeg375748d4-157e-42ec-9b53-7c0af1b7266e"
                      alt="Product"
                      style={{
                        width: "100%",
                        height: "auto",
                        objectFit: "contain",
                      }}
                    />
                  </div>

                  {/* Right side: Product Details */}
                  <div className="flex-grow-1">
                    <h5>{product.name}</h5>
                    <p className="text-muted mb-1">Trần Quốc Trung</p>
                    <p className="mb-1">
                      <strong>{product.rating}</strong> ⭐ ({product.reviews}{" "}
                      reviews)
                    </p>
                    <p className="mb-1">
                      {product.hours} total hours - {product.lectures} lectures
                      - level ALL
                    </p>
                  </div>

                  {/* Price Section */}
                  <div className="text-end" style={{ minWidth: "150px" }}>
                    <p className="text-danger fs-5 mb-0">₫{product.price}</p>
                    <p className="text-muted text-decoration-line-through mb-0">
                      12000₫
                    </p>
                  </div>
                </div>
              ))}
            </div>

            {/* Phần phân trang */}
            <div className="pagination mt-3">
              <button
                className="pagination-btn"
                onClick={() => handleClick(currentPage - 1)}
                disabled={currentPage === 1}
              >
                <span>&lt;</span>
              </button>

              {getPages().map((page, index) =>
                page === "..." ? (
                  <span key={index} className="pagination-ellipsis">
                    ...
                  </span>
                ) : (
                  <button
                    key={index}
                    className={`pagination-number ${
                      currentPage === page ? "active" : ""
                    }`}
                    onClick={() => handleClick(page as number)} // Chuyển đổi string sang number khi click
                  >
                    {page}
                  </button>
                )
              )}

              <button
                className="pagination-btn"
                onClick={() => handleClick(currentPage + 1)}
                disabled={currentPage === totalPages}
              >
                <span>&gt;</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </ClientShared>
  );
}

export default FilterableResults;
