import React, { useState } from "react";
import AdminShared from "../Shared/AdminShared";

interface Category {
  id: number;
  name: string;
  createdDate: string;
  updatedDate: string;
}

const Categories: React.FC = () => {
  // Dữ liệu ban đầu (Chỉ có thể test tạm thời , không lưu được dữ liệu khi refresh)
  const [categories, setCategories] = useState<Category[]>([
    { id: 1, name: "Thể loại 1", createdDate: "2024-11-01", updatedDate: "2024-11-15" },
    { id: 2, name: "Thể loại 2", createdDate: "2024-10-05", updatedDate: "2024-11-10" },
  ]);

  const [showForm, setShowForm] = useState(false); // Hiển thị hoặc ẩn form
  const [newCategory, setNewCategory] = useState({ name: "", description: "" }); // Thông tin thể loại mới
  const [message, setMessage] = useState<string>("");

  // Xử lý thêm thể loại
  const handleAddCategory = (e: React.FormEvent) => {
    e.preventDefault();

    if (!newCategory.name) {
      setMessage("Tên thể loại không được để trống.");
      return;
    }

    const newId = categories.length > 0 ? categories[categories.length - 1].id + 1 : 1;
    const now = new Date().toISOString().split("T")[0]; // Lấy ngày hiện tại (YYYY-MM-DD)
    const [year, month, day] = now.split("-"); // Tách năm, tháng, ngày
    const formattedDate = `${day}-${month}-${year}`; // Sắp xếp lại thành DD-MM-YYYY

    console.log(formattedDate);

    const category: Category = {
      id: newId,
      name: newCategory.name,
      createdDate: formattedDate,
      updatedDate: formattedDate,
    };

    setCategories([...categories, category]); // Cập nhật danh sách thể loại
    setNewCategory({ name: "", description: "" }); // Reset form
    setShowForm(false); // Ẩn form
    setMessage("Thêm thể loại thành công!");
  };

  // Xử lý xóa thể loại
  const handleDelete = (id: number) => {
    if (window.confirm("Bạn có chắc muốn xóa thể loại này?")) {
      setCategories(categories.filter((category) => category.id !== id));
      setMessage("Đã xóa thể loại thành công.");
    }
  };

  return (
    <AdminShared>
      <div className="container mt-4">
        <h1 style={{ textAlign: "center" }}>Danh sách thể loại</h1>
        <hr />
        <div className="d-flex justify-content-end mb-3">
          <button className="btn btn-info" onClick={() => setShowForm(!showForm)}>
            <i className="fa fa-plus" style={{ marginRight: "10px" }}></i> Thêm thể loại mới
          </button>
        </div>

        {/* Form thêm thể loại */}
        {showForm && (
          <div className="card mb-3">
            <div className="card-header bg-primary text-white">Thêm Thể Loại</div>
            <div className="card-body">
              <form onSubmit={handleAddCategory}>
                <div className="mb-3">
                  <label htmlFor="categoryName" className="form-label">
                    Tên Thể Loại
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="categoryName"
                    value={newCategory.name}
                    onChange={(e) => setNewCategory({ ...newCategory, name: e.target.value })}
                    placeholder="Nhập tên thể loại"
                  />
                </div>
                <div className="d-flex justify-content-end">
                  <button type="submit" className="btn btn-success me-2">
                    Lưu
                  </button>
                  <button
                    type="button"
                    className="btn btn-secondary"
                    onClick={() => setShowForm(false)}
                  >
                    Hủy
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}

        {/* Thông báo */}
        {message && (
          <div className="alert alert-info" role="alert">
            {message}
          </div>
        )}

        {/* Bảng danh sách thể loại */}
        <table className="table table-striped table-bordered">
          <thead style={{ backgroundColor: "#06bbcc", color: "white" }}>
            <tr>
              <th>Tên thể loại</th>
              <th>Ngày tạo</th>
              <th>Ngày cập nhật</th>
              <th>Chức năng</th>
            </tr>
          </thead>
          <tbody>
            {categories.length > 0 ? (
              categories.map((category) => (
                <tr key={category.id}>
                  <td>{category.name}</td>
                  <td>{category.createdDate}</td>
                  <td>{category.updatedDate}</td>
                  <td>
                    <button
                      className="btn btn-sm btn-primary me-2"
                      onClick={() =>
                        (window.location.href = `/admin/category/update/${category.id}`)
                      }
                    >
                      <i className="bi bi-pencil-square"></i>
                      Sửa
                    </button>
                    <button
                      className="btn btn-sm btn-danger me-2"
                      onClick={() => handleDelete(category.id)}
                    >
                      <i className="bi bi-trash"></i>
                      Xóa
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={4} className="text-center">
                  Không có thể loại nào.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </AdminShared>
  );
};

export default Categories;
