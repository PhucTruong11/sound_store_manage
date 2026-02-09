-- DROP TABLE IF EXISTS sanpham;
-- DROP TABLE IF EXISTS nhanvien;
-- DROP TABLE IF EXISTS khachhang;

-- Tạo Database tên là 'quanlyamthanh' (nếu chưa có)
CREATE DATABASE IF NOT EXISTS quanlyamthanh CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE quanlyamthanh;

-- --------------------------------------------------------

--
-- Nhóm Con Người (Kế thừa)
--

CREATE TABLE ConNguoi (
    ID VARCHAR(20) PRIMARY KEY, -- Định dạng: NV001 hoặc KH001
    HoTen VARCHAR(40) NOT NULL,
    SDT VARCHAR(20),
    DiaChi VARCHAR(100)
);

-- --------------------------------------------------------

CREATE TABLE NhanVien (
    ID VARCHAR(20) PRIMARY KEY,
    ChucVu VARCHAR(50),
    Email VARCHAR(100),
    Luong DOUBLE,
    TrangThai BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (ID) REFERENCES ConNguoi(ID)
);

-- --------------------------------------------------------

CREATE TABLE KhachHang (
    ID VARCHAR(20) PRIMARY KEY,
    TrangThai BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (ID) REFERENCES ConNguoi(ID)
);

-- --------------------------------------------------------

--
-- Tài khoản
--

CREATE TABLE NhomQuyen (
    MaNhomQuyen VARCHAR(20) PRIMARY KEY,
    TenNhomQuyen VARCHAR(100),
    MoTa TEXT
);

CREATE TABLE TaiKhoan (
    TenDangNhap VARCHAR(50) PRIMARY KEY,
    MatKhau VARCHAR(255) NOT NULL,  -- Nên mã hóa BCrypt
    MaNV VARCHAR(20) UNIQUE,
    MaNhomQuyen VARCHAR(20),
    TrangThai BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(ID),
    FOREIGN KEY (MaNhomQuyen) REFERENCES NhomQuyen(MaNhomQuyen)
);

CREATE TABLE ChucNang (
    MaChucNang VARCHAR(50) PRIMARY KEY,
    TenChucNang VARCHAR(100) NOT NULL,
    MoTa TEXT
);

CREATE TABLE ChiTietQuyen (
    MaNhomQuyen VARCHAR(20),
    MaChucNang VARCHAR(50),
    HanhDong VARCHAR(20),  -- create, read, update, delete
    PRIMARY KEY (MaNhomQuyen, MaChucNang, HanhDong),
    FOREIGN KEY (MaNhomQuyen) REFERENCES NhomQuyen(MaNhomQuyen),
    FOREIGN KEY (MaChucNang) REFERENCES ChucNang(MaChucNang)
);

-- --------------------------------------------------------


--
-- Sản phẩm & Thuộc tính
--

CREATE TABLE LoaiSP (
    MaLoai VARCHAR(20) PRIMARY KEY,
    TenLoai VARCHAR(50) NOT NULL -- Loa, Tai nghe, Amply, Micro
);

CREATE TABLE HangSX (
    MaHang VARCHAR(20) PRIMARY KEY,
    TenHang VARCHAR(50) NOT NULL,
    QuocGia VARCHAR(50)
);

CREATE TABLE SanPham (
    MaSP VARCHAR(20) PRIMARY KEY,
    TenSP VARCHAR(100) NOT NULL,
    MaLoai VARCHAR(20),
    MaHang VARCHAR(20),
    MoTa TEXT,
    ThoiGianBaoHanh INT DEFAULT 12, -- Tháng
    TrangThai BOOLEAN DEFAULT TRUE,
    HinhAnh VARCHAR(255),
    FOREIGN KEY (MaLoai) REFERENCES LoaiSP(MaLoai),
    FOREIGN KEY (MaHang) REFERENCES HangSX(MaHang)
);

--
-- Phiên bản sản phẩm
--

CREATE TABLE PhienBanSP (
    MaPhienBan VARCHAR(20) PRIMARY KEY,
    MaSP VARCHAR(20),
    MauSac VARCHAR(50),
    CongSuat VARCHAR(50),     -- VD: 80W, 40W
    Pin VARCHAR(50),          -- VD: 30h, 20h
    KetNoi VARCHAR(100),      -- VD: Bluetooth 5.2
    GiaNhap DOUBLE,
    GiaBan DOUBLE,
    SoLuongTon INT DEFAULT 0,
    TrangThai BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);

--
-- Quản lý imei (Từng chiếc) 

CREATE TABLE ChiTietSP (
    MaImei VARCHAR(50) PRIMARY KEY, -- MS1-001, SN2-001
    MaPhienBan VARCHAR(20),
    MaPhieuNhap VARCHAR(20),
    MaPhieuXuat VARCHAR(20),
    TinhTrang VARCHAR(50) DEFAULT 'Trong kho', -- Đã bán, Bảo hành
    TrangThai BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (MaPhienBan) REFERENCES PhienBanSP(MaPhienBan)
);

-- --------------------------------------------------------

--
-- Nhập hàng
-- 

CREATE TABLE NhaCungCap ( 
    MaNCC VARCHAR(20) PRIMARY KEY, 
    TenNCC VARCHAR(100), 
    DiaChi VARCHAR(255), 
    Sdt VARCHAR(20),
    TrangThai BOOLEAN DEFAULT TRUE
);

CREATE TABLE PhieuNhap (
    MaPhieuNhap VARCHAR(20) PRIMARY KEY,
    NgayNhap DATETIME DEFAULT CURRENT_TIMESTAMP,
    MaNV VARCHAR(20),
    MaNCC VARCHAR(20),
    TongTien DOUBLE DEFAULT 0,
    TrangThai BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(ID),
    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC)
);

CREATE TABLE ChiTietPhieuNhap ( 
    MaPhieuNhap VARCHAR(20), 
    MaPhienBan VARCHAR(20) NOT NULL, 
    SoLuong INT,
    DonGia DOUBLE,
    ThanhTien DOUBLE,
    PRIMARY KEY (MaPhieuNhap, MaPhienBan),
    FOREIGN KEY (MaPhieuNhap) REFERENCES PhieuNhap(MaPhieuNhap),
    FOREIGN KEY (MaPhienBan) REFERENCES PhienBanSP(MaPhienBan)
);

-- --------------------------------------------------------

-- 
-- Bán hàng
-- 

CREATE TABLE KhuyenMai (
    MaKM VARCHAR(20)  PRIMARY KEY, 
    TenKM VARCHAR(100), 
    DieuKienGiam DOUBLE,  -- Giá trị đơn tối thiểu
    PhanTramGiam DOUBLE, 
    NgayBatDau DATE, 
    NgayKetThuc DATE,
    TrangThai BOOLEAN DEFAULT TRUE
);

CREATE TABLE PhieuXuat (
    MaPhieuXuat VARCHAR(20) PRIMARY KEY,
    NgayXuat DATETIME DEFAULT CURRENT_TIMESTAMP,
    MaNV VARCHAR(20),
    MaKH VARCHAR(20),
    MaKM VARCHAR(20),
    TongTien DOUBLE DEFAULT 0,
    TrangThai BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(ID),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(ID),
    FOREIGN KEY (MaKM) REFERENCES KhuyenMai(MaKM)
);

CREATE TABLE ChiTietPhieuXuat ( 
    MaPhieuXuat VARCHAR(20),
    MaPhienBan VARCHAR(20),
    SoLuong INT,
    DonGia DOUBLE,
    ThanhTien DOUBLE,
    PRIMARY KEY (MaPhieuXuat, MaPhienBan),
    FOREIGN KEY (MaPhieuXuat) REFERENCES PhieuXuat(MaPhieuXuat),
    FOREIGN KEY (MaPhienBan) REFERENCES PhienBanSP(MaPhienBan)
);

-- --------------------------------------------------------

--
-- Bảo hành
--

CREATE TABLE BaoHanh ( 
    MaBH VARCHAR(20) PRIMARY KEY, 
    MaImei VARCHAR(50), 
    MaPhieuXuat VARCHAR(20), 
    NgayBatDau DATE DEFAULT (CURRENT_DATE),
    NgayKetThuc DATE,
    TinhTrang VARCHAR(50) DEFAULT 'Đang sửa chữa', 
    TrangThai BOOLEAN DEFAULT TRUE, 
    FOREIGN KEY (MaImei) REFERENCES ChiTietSP(MaImei),
    FOREIGN KEY (MaPhieuXuat) REFERENCES PhieuXuat(MaPhieuXuat)
);

CREATE TABLE ChiTietBaoHanh ( 
    MaCTBH VARCHAR(20) PRIMARY KEY, 
    MaBH VARCHAR(20), 
    NoiDung TEXT, 
    TinhTrang VARCHAR(50) DEFAULT 'Đang sửa chữa',
    FOREIGN KEY (MaBH) REFERENCES BaoHanh(MaBH) ON DELETE CASCADE
);

-- --------------------------------------------------------

-- 
-- THIẾT LẬP TRIGGER CẬP NHẬT TỒN KHO & TỔNG TIÊN
-- 

DELIMITER //

-- Tự động TĂNG tồn kho khi NHẬP hàng
CREATE TRIGGER trg_UpdateStockAfterImport
AFTER INSERT ON ChiTietPhieuNhap
FOR EACH ROW
BEGIN
    UPDATE PhienBanSP
    SET SoLuongTon = SoLuongTon + NEW.SoLuong 
    WHERE MaPhienBan = NEW.MaPhienBan;
END//

-- Tự động GIẢM tồn kho khi BÁN hàng
CREATE TRIGGER trg_UpdateStockAfterExport
AFTER INSERT ON ChiTietPhieuXuat
FOR EACH ROW
BEGIN
    UPDATE PhienBanSP 
    SET SoLuongTon = SoLuongTon - NEW.SoLuong 
    WHERE MaPhienBan = NEW.MaPhienBan;
END//


-- Tự động TỔNG TIỀN khi NHẬP hàng
CREATE TRIGGER trg_CalcThanhTienNhap
BEFORE INSERT ON ChiTietPhieuNhap
FOR EACH ROW
BEGIN
    SET NEW.ThanhTien = NEW.SoLuong * NEW.DonGia;
END//

CREATE TRIGGER trg_UpdateTongTienNhap
AFTER INSERT ON ChiTietPhieuNhap
FOR EACH ROW
BEGIN
    UPDATE PhieuNhap
    SET TongTien = (
        SELECT SUM(ThanhTien)
        FROM ChiTietPhieuNhap
        WHERE MaPhieuNhap = NEW.MaPhieuNhap
    )
    WHERE MaPhieuNhap = NEW.MaPhieuNhap;
END//

-- Tự động TỔNG TIỀN khi XUẤT hàng
CREATE TRIGGER trg_CalcThanhTienXuat
BEFORE INSERT ON ChiTietPhieuXuat
FOR EACH ROW
BEGIN
    SET NEW.ThanhTien = NEW.SoLuong * NEW.DonGia;
END//

CREATE TRIGGER trg_UpdateTongTienXuat
AFTER INSERT ON ChiTietPhieuXuat
FOR EACH ROW
BEGIN
    UPDATE PhieuXuat
    SET TongTien = (
        SELECT SUM(ThanhTien)
        FROM ChiTietPhieuXuat
        WHERE MaPhieuXuat = NEW.MaPhieuXuat
    )
    WHERE MaPhieuXuat = NEW.MaPhieuXuat;
END//



DELIMITER ;

-- --------------------------------------------------------

-- 
-- CONSTRAINT & INDEX TỐI ƯU HÓA
-- 

-- -- CONSTRAINT ĐỂ ĐẢM BẢO DỮ LIỆU HỢP LỆ
ALTER TABLE PhienBanSP 
ADD CONSTRAINT chk_giaban CHECK (GiaBan > GiaNhap);

ALTER TABLE PhienBanSP 
ADD CONSTRAINT chk_soluong CHECK (SoLuongTon >= 0);

ALTER TABLE ChiTietPhieuNhap 
ADD CONSTRAINT chk_soluong_nhap CHECK (SoLuong > 0);

ALTER TABLE ChiTietPhieuXuat
ADD CONSTRAINT chk_soluong_xuat CHECK (SoLuong > 0);


-- INDEX ĐỂ TỐI ƯU HÓA 
CREATE INDEX idx_sp_ten ON SanPham(TenSP);
CREATE INDEX idx_pn_ngay ON PhieuNhap(NgayNhap);
CREATE INDEX idx_px_ngay ON PhieuXuat(NgayXuat);
CREATE INDEX idx_chitietsp_tinhtrang ON ChiTietSP(TinhTrang);
CREATE INDEX idx_connguoi_sdt ON ConNguoi(SDT);

-- --------------------------------------------------------


-- DỮ LIỆU

INSERT INTO NhomQuyen VALUES 
('NQ01', 'Quản lý cửa hàng', 'Full quyền'),
('NQ02', 'Nhân viên bán hàng', 'Chỉ bán và xem kho'),
('NQ03', 'Nhân viên kho', 'Chỉ nhập hàng');

INSERT INTO ConNguoi (ID, HoTen, SDT, DiaChi) VALUES 
('NV01', 'Trương Phúc', '0909123456', 'Đà Nẵng'),
('NV02', 'Lê Văn Nam', '0909123457', 'Hà Nội'),
('KH01', 'Nguyễn Khách', '0912345678', 'TP.HCM'),
('KH02', 'Trần VIP', '0987654321', 'Cần Thơ');

INSERT INTO NhanVien (ID, ChucVu, Email, Luong) VALUES 
('NV01', 'Quản lý', 'phuc@sw.com', 20000000),
('NV02', 'Nhân viên bán hàng', 'nam@sw.com', 10000000);

INSERT INTO KhachHang (ID) VALUES 
('KH01'),
('KH02');

INSERT INTO TaiKhoan (TenDangNhap, MatKhau, MaNV, MaNhomQuyen) VALUES 
('admin', '123456', 'NV01', 'NQ01'),
('kho', '123456', 'NV02', 'NQ03');

-- --------------------------------------------------------

INSERT INTO LoaiSP VALUES 
('L01', 'Loa Bluetooth'),
('L02', 'Tai nghe Over-ear');

INSERT INTO HangSX VALUES 
('H01', 'Marshall', 'Anh'),
('H02', 'Sony', 'Nhật');

INSERT INTO SanPham (MaSP, TenSP, MaLoai, MaHang, MoTa, ThoiGianBaoHanh) VALUES 
('SP01', 'Marshall Stanmore III', 'L01', 'H01', 'Loa decor cực đẹp', 12),
('SP02', 'Sony WH-1000XM5', 'L02', 'H02', 'Chống ồn đỉnh cao', 12);

INSERT INTO PhienBanSP (MaPhienBan, MaSP, MauSac, CongSuat, Pin, KetNoi, GiaNhap, GiaBan, SoLuongTon) VALUES 
('PB01', 'SP01', 'Kem (Cream)', '80W', 'N/A', 'Bluetooth 5.2', 7000000, 9500000, 2),
('PB02', 'SP01', 'Đen (Black)', '80W', 'N/A', 'Bluetooth 5.2', 7000000, 9500000, 2);

-- Chạy lệnh này trong MySQL để sửa tên ảnh
UPDATE SanPham SET HinhAnh = 'marshall.jpg' WHERE MaSP = 'SP01';
UPDATE SanPham SET HinhAnh = 'sony xm5.jpg' WHERE MaSP = 'SP02';
-- --------------------------------------------------------

INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, Sdt) VALUES 
('NCC01', 'Marshall VN Dist', 'Q1, TP.HCM', '0283333089'),
('NCC02', 'B&O', 'Q7, TP.HCM', '0961254087'),
('NCC03', 'Bose', 'Hà Nội', '0991299099');

INSERT INTO PhieuNhap (MaPhieuNhap, MaNV, MaNCC) VALUES 
('PN01', 'NV01', 'NCC01');

INSERT INTO ChiTietPhieuNhap (MaPhieuNhap, MaPhienBan, SoLuong, DonGia) VALUES 
('PN01', 'PB01', 3, 7000000),
('PN01', 'PB02', 2, 7000000);

INSERT INTO ChiTietSP (MaImei, MaPhienBan, MaPhieuNhap, TinhTrang) VALUES 
('111222333', 'PB01', 'PN01', 'Trong kho'),
('444555666', 'PB01', 'PN01', 'Trong kho'),
('123123123', 'PB02', 'PN01', 'Trong kho'),
('456456456', 'PB02', 'PN01', 'Trong kho'),
('777888999', 'PB02', 'PN01', 'Đã bán');
-- --------------------------------------------------------

INSERT INTO KhuyenMai (MaKM, TenKM, DieuKienGiam, PhanTramGiam, NgayBatDau, NgayKetThuc) VALUES 
('KM01', 'Khai trương', 0, 10, '2025-01-01', '2030-12-31');

INSERT INTO PhieuXuat (MaPhieuXuat, MaNV, MaKH, MaKM) VALUES 
('PX01', 'NV01', 'KH01', 'KM01');

INSERT INTO ChiTietPhieuXuat (MaPhieuXuat, MaPhienBan, SoLuong, DonGia) VALUES 
('PX01', 'PB01', 1, 8550000);

UPDATE ChiTietSP 
SET TinhTrang = 'Đã bán', MaPhieuXuat = 'PX01' 
WHERE MaImei IN ('111222333', '444555666', '777888999');

-- cái này để có thêm bảo hành vào cái sản phẩm
INSERT IGNORE INTO PhieuXuat (MaPhieuXuat, MaNV, MaKH, TongTien) VALUES 
('PX01', 'NV01', 'KH01', 8550000);
-- --------------------------------------------------------

INSERT INTO BaoHanh (MaBH, MaImei, MaPhieuXuat, NgayBatDau, NgayKetThuc, TinhTrang) VALUES 
('BH01', '111222333', 'PX01', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 12 MONTH), 'Đang sửa chữa'),
('BH02', '444555666', 'PX01', '2026-01-10', '2027-01-10', 'Đã trả máy'),
('BH03', '777888999', 'PX01', '2026-02-05', '2027-02-05', 'Đang sửa chữa');


INSERT INTO ChiTietBaoHanh (MaCTBH, MaBH, NoiDung, TinhTrang) VALUES 
('CTBH01', 'BH01', 'Loa bị rè bass', 'Đang sửa chữa'),
('CTBH02', 'BH02', 'Lỗi kết nối Bluetooth chập chờn', 'Đã trả máy'),
('CTBH03', 'BH02', 'Vệ sinh chân sạc miễn phí', 'Đã trả máy'),
('CTBH04', 'BH03', 'Hỏng pin - Sạc không vào điện', 'Đang sửa chữa');


CREATE TABLE NCC_SanPham (
    MaNCC VARCHAR(20),
    MaSP VARCHAR(20),
    PRIMARY KEY (MaNCC, MaSP),
    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);

INSERT INTO NCC_SanPham VALUES ('NCC01', 'SP01');
