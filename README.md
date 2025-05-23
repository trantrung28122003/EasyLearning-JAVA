# 🚀 EasyLearning - Online Course Sales Website & App

## 📌 Giới thiệu

EasyLearning là một nền tảng học trực tuyến cho phép người dùng mua, đăng ký và học các khóa học online. Dự án được phát triển với công nghệ **Spring Boot**, **MySQL**, **React**, **Vite**, **Flutter**, và tích hợp nhiều tính năng nâng cao như xác thực JWT, thanh toán VNPay/MoMo, quản lý chứng chỉ, v.v.

## ✨ Tính năng chính

- 📚 Quản lý khóa học (thêm, sửa, xóa, tìm kiếm khóa học)
- 🎥 Hệ thống video bài giảng, bài kiểm tra
- 📝 Ghi chú bài học ngay trên video
- 🚫 Chống tua nhanh khi học video
- 🏅 Cấp chứng chỉ sau khi hoàn thành khóa học
- 🔐 Xác thực và phân quyền người dùng bằng JWT
- 💳 Thanh toán trực tuyến qua VNPay, MoMo
- ☁️ Lưu trữ phương tiện trên Cloudinary
- 💬 Hỗ trợ bình luận theo thời gian thực, thông báo đẩy
- 🛒 Quản lý giỏ hàng và lịch sử giao dịch
- 👤 Quản lý tài khoản cá nhân và chứng chỉ
- 📆 Quản lý lịch học (thời khóa biểu)

## 🛠 Công nghệ sử dụng

### 🔧 Backend

- **Ngôn ngữ lập trình:** Java 22 ☕
- **Framework:** Spring Boot 3.2.7
- **Quản lý phụ thuộc:** Maven
- **Cơ sở dữ liệu:** MySQL 8+
- **ORM:** Spring Data JPA
- **Bảo mật:** Spring Security với JWT
- **Lưu trữ tệp:** Cloudinary
- **Thanh toán trực tuyến:** VNPay, MoMo
- **Gửi email:** Spring Mail
- **Xác thực OAuth2:** Spring Security + JWT
- **Logging:** SLF4J, Logback

### 🎨 Frontend

- **Ngôn ngữ lập trình:** TypeScript, JavaScript 📜
- **Thư viện:** React ⚛️
- **Công cụ xây dựng:** Vite ⚡
- **Quản lý trạng thái:** Redux 🔄
- **Giao tiếp API:** Axios 🌍
- **Thiết kế giao diện:** Tailwind CSS 🎨
- **Biểu đồ dữ liệu:** Recharts 📊

### 📱 Mobile

- **Ngôn ngữ lập trình:** Dart 🎯
- **Framework:** Flutter 🦋

### 🔗 Khác

- **Quản lý mã nguồn:** Git 🛠️
- **Lưu trữ mã nguồn:** GitHub 📂
- **Công cụ xây dựng và quản lý phụ thuộc:** Node.js với npm hoặc yarn 🏗️

## 🚀 Cài đặt và chạy dự án

### 1️⃣ Cài đặt Backend

#### 📌 Yêu cầu

- Java 22 ☕
- MySQL 8+ 🛢️
- Maven 3+ 🏗️

#### 📥 Clone project và thiết lập database

```bash
git clone https://github.com/trantrung28122003/EasyLearning-JAVA.git
cd EasyLearning-JAVA
```

Cấu hình file `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/easylearning
    username: root
    password: yourpassword
  jpa:
    hibernate:
      ddl-auto: update
```

Cài đặt thư viện:

```bash
mvn clean install
```

Chạy ứng dụng:

```bash
mvn spring-boot:run
```

#### ☁️ Cấu hình Cloudinary

Thêm thông tin Cloudinary vào `application.yml`:

```yaml
cloudinary:
  cloud-name: your-cloud-name
  api-key: your-api-key
  api-secret: your-api-secret
```

### 2️⃣ Cài đặt Frontend

#### 📌 Yêu cầu

- Node.js 18+ 🏗️
- npm hoặc yarn 📦

#### 📥 Clone và chạy project

```bash
git clone https://github.com/trantrung28122003/EasyLearning-React.git
cd EasyLearning-React
npm install
npm run dev
```

### 3️⃣ Cài đặt Mobile App

#### 📌 Yêu cầu

- Flutter 3+
- Dart 3+
- Android Studio hoặc VS Code

#### 📥 Clone và chạy project

```bash
git clone https://github.com/trantrung28122003/EasyLearning-Flutter.git
cd EasyLearning-Flutter
flutter pub get
flutter run
```

## 🎨 Hình ảnh giao diện

### 🔑 Xác thực & Tài khoản

- **🔐 Trang đăng nhập**  
  ![Login](Docs/Images/login_page.png)
- **🆕 Trang đăng ký**  
  ![Register](Docs/Images/register_page.png)
- **👤 Hồ sơ cá nhân**  
  ![Profile](Docs/Images/user_profile_page.png)
- **📜 Lịch sử đơn hàng**  
  ![Order History](Docs/Images/order_history_page.png)
- **📚 Khóa học đã đăng ký**  
  ![User Courses](Docs/Images/user_courses_page.png)
- **🏆 Danh sách chứng chỉ**  
  ![Certificates](Docs/Images/certificate_list_page.png)

### 🎓 Khóa học & Nội dung học tập

- **📚 Trang chi tiết khóa học**  
  ![Course Detail](Docs/Images/course_detail_page.png)
- **🌃 Học thừu**  
  ![Trial Learning](Docs/Images/trial_learning.png)
- **⭐ Đánh giá khóa học**  
  ![Feedback](Docs/Images/course_feedback.png)

### 🏦 Học tập & Bài giảng

- **🏠 Trang chủ**  
  ![Home](Docs/Images/home_page.png)
- **📂 Danh sách khóa học**  
  ![Course List](Docs/Images/course_list_page.png)
- **🎥 Màn hình học tập chính**  
  ![Learning Main](Docs/Images/learning_main.png)
- **📝 Bài tập trắc nghiệm**  
  ![Quiz](Docs/Images/learning_quizz_page.png)
- **👒 Ghi chú bài học**  
  ![Notes](Docs/Images/note_page.png)
- **⛔ Chống tua nhanh**  
  ![Anti Skip](Docs/Images/learning_anti_skip_page.png)
- **🎓 Nhận chứng chỉ**  
  ![Certificate](Docs/Images/certificate_page.png)
- **📅 Thời khóa biểu**  
  ![Schedule](Docs/Images/course_schedule_page.png)

### 💳 Thanh toán & Mua hàng

- **💰 Chọn phương thức thanh toán**  
  ![Payment Selection](Docs/Images/payment_selection_page.png)
- **📲 Hiển thị mã thanh toán**  
  ![Payment QR Code](Docs/Images/payment_qr_code_page.png)
- **🛒 Giỏ hàng**  
  ![Cart](Docs/Images/cart_page.png)
- **➕ Thêm vào giỏ hàng**  
  ![Add to Cart](Docs/Images/cart_add_page.png)

### 💬 Giao tiếp & Tương tác

- **💬 Bình luận & Thảo luận**  
  ![Comments](Docs/Images/comment_page.png)
- **🔔 Thông báo hệ thống**  
  ![Notifications](Docs/Images/notification_page.png)

### 📱 Giao diện Mobile

- **🔐 Đăng nhập Mobile**
  <p align="center">
    <img src="Docs/Images/mobile_login_page.png" alt="Mobile Login">
  </p>

- **🏠 Trang chủ Mobile**
  <p align="center">
    <img src="Docs/Images/mobile_home_page.png" alt="Mobile Home">
  </p>

- **📂 Danh sách khóa học Mobile**
  <p align="center">
    <img src="Docs/Images/mobile_course_list.png" alt="Mobile Course List">
  </p>

- **📚 Chi tiết khóa học Mobile**
  <p align="center">
    <img src="Docs/Images/mobile_course_detail.png" alt="Mobile Course Detail">
  </p>

- **🛍️ Giỏ hàng Mobile**
  <p align="center">
    <img src="Docs/Images/mobile_cart.png" alt="Mobile Cart">
  </p>

- **👤 Hồ sơ người dùng Mobile**
  <p align="center">
    <img src="Docs/Images/mobile_user_profile.png" alt="Mobile Profile">
  </p>

### 🛠️ Quản trị viên

- **📚 Tạo khóa học** ![Admin Create Course](Docs/Images/admin_create_course_page.png)
- **📋 Danh sách khóa học** ![Admin Course List](Docs/Images/admin_courses_list_page.png)
- **👥 Danh sách người dùng** ![Admin Users](Docs/Images/admin_users_list_page.png)

## 🤝 Đóng góp

Nếu bạn muốn đóng góp cho dự án, vui lòng tạo pull request hoặc liên hệ qua email: `easylearning@gmail.com` 📩.
