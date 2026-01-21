DROP TABLE IF EXISTS sanpham;
DROP TABLE IF EXISTS nhanvien;
DROP TABLE IF EXISTS khachhang;

-- 1. Tạo Database tên là 'quanlyamthanh' (nếu chưa có)
CREATE DATABASE IF NOT EXISTS quanlyamthanh CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. Chọn Database này để làm việc
USE quanlyamthanh;

-- 3. Tạo bảng Sản Phẩm (giống class Laptop.java của bạn)
CREATE TABLE IF NOT EXISTS sanpham (
    MaSP VARCHAR(20) NOT NULL PRIMARY KEY,
    TenSP VARCHAR(100) NOT NULL,
    DonGia DOUBLE NOT NULL
);

-- 3. Chèn 20 thiết bị âm thanh mẫu
INSERT INTO sanpham (MaSP, TenSP, DonGia) VALUES
('AT001', 'Loa Marshall Stanmore III', 9500000),
('AT002', 'Tai nghe Sony WH-1000XM5', 8490000),
('AT003', 'Loa Bluetooth JBL Charge 5', 3990000),
('AT004', 'Tai nghe Apple AirPods Max', 13500000),
('AT005', 'Loa Harman Kardon Aura Studio 4', 6900000),
('AT006', 'Tai nghe Bose QuietComfort Ultra', 10500000),
('AT007', 'Loa Marshall Emberton II', 4500000),
('AT008', 'Tai nghe Sennheiser Momentum 4', 9200000),
('AT009', 'Loa kéo Sony SRS-XG300', 7200000),
('AT010', 'Loa Bookshelf Edifier R1700BT', 2800000),
('AT011', 'Tai nghe Marshall Major IV', 3600000),
('AT012', 'Loa Sonos Era 300', 12500000),
('AT013', 'Tai nghe Gaming Razer BlackShark V2', 2500000),
('AT014', 'Loa Soundbar Samsung HW-Q990C', 21500000),
('AT015', 'Tai nghe In-ear Moondrop Chu II', 550000),
('AT016', 'Amply DAC Fiio K7 Full Balanced', 5200000),
('AT017', 'Loa Fender Indio 2', 8900000),
('AT018', 'Tai nghe Beats Studio Pro', 7500000),
('AT019', 'Loa Devialet Phantom I 108dB', 85000000),
('AT020', 'Microphone Shure SM7B', 11500000);

CREATE TABLE IF NOT EXISTS nhanvien (
    ma_nv INT AUTO_INCREMENT PRIMARY KEY,
    ten_nv VARCHAR(100) NOT NULL,
    sdt VARCHAR(20),
    dia_chi VARCHAR(200),
    chuc_vu VARCHAR(50)
);
INSERT INTO nhanvien (ten_nv, sdt, dia_chi, chuc_vu)
VALUES 
('Nguyễn Văn A', '0901234567', 'Hà Nội', 'Quản lý'),
('Trần Thị B', '0912345678', 'TP HCM', 'Nhân viên');


CREATE TABLE IF NOT EXISTS khachhang (
    ma_kh INT AUTO_INCREMENT PRIMARY KEY,
    ten_kh VARCHAR(100) NOT NULL,
    sdt VARCHAR(20),
    dia_chi VARCHAR(200)
);
INSERT INTO khachhang (ten_kh, sdt, dia_chi)
VALUES
('Lê Văn C', '0987654321', 'Đà Nẵng'),
('Phạm Thị D', '0976543210', 'Cần Thơ');
