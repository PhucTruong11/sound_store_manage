-- 1. Tạo Database tên là 'quanlylaptop' (nếu chưa có)
CREATE DATABASE IF NOT EXISTS quanlylaptop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. Chọn Database này để làm việc
USE quanlylaptop;

-- 3. Tạo bảng Sản Phẩm (giống class Laptop.java của bạn)
CREATE TABLE IF NOT EXISTS sanpham (
    MaSP VARCHAR(20) NOT NULL PRIMARY KEY,
    TenSP VARCHAR(100) NOT NULL,
    DonGia DOUBLE NOT NULL
);

-- 4. Thêm sẵn 3-4 dòng dữ liệu mẫu để test
INSERT INTO sanpham (MaSP, TenSP, DonGia) VALUES
('LT001', 'Dell XPS 13 Plus', 28000000),
('LT002', 'MacBook Air M2', 24500000),
('LT003', 'Asus ROG Strix G15', 31000000),
('LT004', 'Lenovo ThinkPad X1', 35000000);