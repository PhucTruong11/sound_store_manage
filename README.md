# 💻 Hệ Thống Quản Lý Cửa Hàng Laptop
> **Laptop Store Management System**

Dự án đồ án môn học **Ngôn ngữ lập trình Java**, sử dụng kiến trúc **3-Tier** (3 lớp) kết hợp với giao diện **Java Swing** và cơ sở dữ liệu **MySQL**.

---

## 📂 Cấu Trúc Thư Mục (Project Structure)

Dự án được bố trí theo chuẩn **Maven** để dễ dàng quản lý thư viện và đóng gói:

```
src/main/java/
├── App.java                          # Entry point
├── Backend/                          # Xử lý nghiệp vụ & truy cập dữ liệu
└── Frontend/                         # Giao diện người dùng (Swing)

src/main/resources/
├── config/                           # Tệp cấu hình hệ thống
└── images/                           # Icon & Logo

docs/                                 # Tài liệu thiết kế & SQL script
screenshots/                          # Hình ảnh giao diện
```

---

## 📋 Tính Năng Chính

✅ Quản lý danh sách laptop  
✅ Cập nhật thông tin sản phẩm  
✅ Xóa sản phẩm khỏi hệ thống  
✅ Tìm kiếm nhanh chóng  
✅ Quản lý kho hàng  
✅ Ghi chép giao dịch  
✅ Nhập/Xuất dữ liệu bằng Excel  
✅ Xuất hóa đơn bằng PDF  
✅ Dashboard thống kê

---

## Tổng quan dự án
    -Đăng nhập(Nhân viên, khách hàng, admin)
    -Trang chủ
        Bán hàng
        Nhập hàng
        Sản phẩm
        Hóa đơn bán hàng/nhập hàng
        Khuyến mãi
        Nhân viên
            Admin(có thể phân quyền cho nhân viên)
            Nhân viên bán hàng
            Kế toán
            Bảo vệ
        Khách hàng 
        Nhà cung cấp
        Tài khoản(Đăng nhập, đăng xuất)
        Giỏ hàng
        Cài đặt
    Sản phẩm
        Thêm
        Sửa
        Xóa
        Nhập, xuất Excel
        Tìm kiếm
            Theo tên
            theo giá
            hãng
        Sắp xếp
            tăng dần
            giảm dần
            từ - đến
    Bán hàng
        Chọn sản phẩm
            Xác nhận mua
                Xuất hóa đơn bán hàng(xuất pdf)
            Thêm vào giỏ hàng
                Giữ hàng
    Thống kê
        Thống kê cơ bản(xuất pdf)
            Sản phẩm
            Nhân viên
            Khách hàng
            Nhà cung cấp
            Bán hàng
            Nhập hàng


## 🛠️ Công Nghệ Sử Dụng

| Thành Phần | Chi Tiết |
|-----------|---------|
| **📝 Ngôn Ngữ** | Java JDK 21+ |
| **📦 Build Tool** | Apache Maven |
| **🎨 Giao Diện** | Java Swing & MigLayout |
| **🗄️ Database** | MySQL (XAMPP) |
| **🔗 Kết Nối** | JDBC (Java Database Connectivity) |

---

## 🗄️ Cơ Sở Dữ Liệu (Database)

Hệ thống sử dụng cơ sở dữ liệu quan hệ với các bảng chính:

| Bảng | Mô Tả |
|-----|--------|
| **Sản Phẩm (SanPham)** | Quản lý thông tin chung Laptop, Bàn phím, Chuột... |
| **Chi Tiết Thiết Bị** | Thông số kỹ thuật chi tiết (CPU, RAM, GPU...) |
| **Nhân Viên & Tài Khoản** | Quản lý người dùng & phân quyền hệ thống |
| **Hóa Đơn (Nhập/Xuất)** | Lịch sử giao dịch & chi tiết hóa đơn |
| **Đối Tác** | Thông tin Khách hàng & Nhà cung cấp |

> 📌 **Lưu ý:** File script khởi tạo database nằm tại `docs/database.sql`

---

## 🚀 Hướng Dẫn Cài Đặt

### 1️⃣ Cài Đặt Database

```bash
# Bước 1: Mở XAMPP và khởi động module MySQL
# Bước 2: Truy cập phpMyAdmin
# Bước 3: Tạo database mới tên là: quanlylaptop
# Bước 4: Import file SQL từ thư mục docs/
```

### 2️⃣ Cấu Hình Code

```bash
# Mở dự án bằng VS Code hoặc IntelliJ
# Kiểm tra thông tin kết nối trong Backend/DatabaseHelper.java
# Điều chỉnh username, password và tên database nếu cần
```

### 3️⃣ Chạy Ứng Dụng

```bash
# Build dự án bằng Maven
mvn clean install

# Chạy ứng dụng
mvn exec:java -Dexec.mainClass="App"

# Hoặc chạy trực tiếp file
java -cp target/classes App
```

---