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
    HinhAnh VARCHAR(255),
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

CREATE TABLE NCC_SanPham (
    MaNCC VARCHAR(20),
    MaSP VARCHAR(20),
    PRIMARY KEY (MaNCC, MaSP),
    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
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
    DECLARE v_phan_tram DOUBLE DEFAULT 0;

    -- Lấy % giảm giá từ bảng KhuyenMai dựa vào MaKM của phiếu xuất hiện tại
    SELECT COALESCE(km.PhanTramGiam, 0) INTO v_phan_tram
    FROM PhieuXuat px
    LEFT JOIN KhuyenMai km ON px.MaKM = km.MaKM
    WHERE px.MaPhieuXuat = NEW.MaPhieuXuat;

    -- Cập nhật lại tổng tiền = (Tổng giá gốc) * (1 - % giảm)
    UPDATE PhieuXuat
    SET TongTien = (
        SELECT SUM(ThanhTien) * (1 - v_phan_tram / 100)
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

ALTER TABLE KhuyenMai MODIFY COLUMN TrangThai INT DEFAULT 1;


-- INDEX ĐỂ TỐI ƯU HÓA 
CREATE INDEX idx_sp_ten ON SanPham(TenSP);
CREATE INDEX idx_pn_ngay ON PhieuNhap(NgayNhap);
CREATE INDEX idx_px_ngay ON PhieuXuat(NgayXuat);
CREATE INDEX idx_chitietsp_tinhtrang ON ChiTietSP(TinhTrang);
CREATE INDEX idx_connguoi_sdt ON ConNguoi(SDT);

-- --------------------------------------------------------


-- DỮ LIỆU
-- Bổ sung dữ liệu Chức năng để hiện lên bảng Phân quyền
INSERT IGNORE INTO ChucNang (MaChucNang, TenChucNang, MoTa) VALUES 
('BANHANG', 'Bán hàng', 'Quản lý giao dịch bán hàng'),
('NHAPHANG', 'Nhập hàng', 'Quản lý nhập kho sản phẩm'),
('SANPHAM', 'Sản phẩm', 'Quản lý thông tin và phiên bản sản phẩm'),
('KHACHHANG', 'Khách hàng', 'Quản lý thông tin khách hàng'),
('NHANVIEN', 'Nhân viên', 'Quản lý nhân sự và chức vụ'),
('NHACUNGCAP', 'Nhà cung cấp', 'Quản lý đối tác cung ứng'),
('PHIEUNHAP', 'Phiếu nhập', 'Quản lý lịch sử nhập hàng'),
('PHIEUXUAT', 'Phiếu xuất', 'Quản lý hóa đơn xuất hàng'),
('KHUYENMAI', 'Khuyến mãi', 'Quản lý chương trình giảm giá'),
('BAOHANH', 'Bảo hành', 'Quản lý thiết bị bảo hành'),
('THONGKE', 'Thống kê', 'Xem báo cáo doanh thu và tồn kho'),
('PHANQUYEN', 'Phân quyền', 'Thiết lập quyền hạn cho nhóm người dùng');

INSERT INTO NhomQuyen VALUES 
('NQ01', 'Quản lý cửa hàng', 'Full quyền'),
('NQ02', 'Nhân viên bán hàng', 'Chỉ bán và xem kho'),
('NQ03', 'Nhân viên kho', 'Chỉ nhập hàng');

INSERT IGNORE INTO ChiTietQuyen (MaNhomQuyen, MaChucNang, HanhDong)
SELECT 'NQ01', MaChucNang, act
FROM ChucNang
CROSS JOIN (SELECT 'read' AS act UNION SELECT 'create' UNION SELECT 'update' UNION SELECT 'delete') AS actions;


INSERT INTO ChiTietQuyen (MaNhomQuyen, MaChucNang, HanhDong) VALUES 
('NQ02', 'BANHANG', 'read'), 
('NQ02', 'BANHANG', 'create'), 
('NQ02', 'BANHANG', 'update'),
('NQ02', 'PHIEUXUAT', 'read'), 
('NQ02', 'PHIEUXUAT', 'create'),
('NQ02', 'KHACHHANG', 'read'), 
('NQ02', 'KHACHHANG', 'create'), 
('NQ02', 'KHACHHANG', 'update'),
('NQ02', 'SANPHAM', 'read'), 
('NQ02', 'BAOHANH', 'read'), 
('NQ02', 'BAOHANH', 'create'),
('NQ03', 'NHAPHANG', 'read'), 
('NQ03', 'NHAPHANG', 'create'),
('NQ03', 'PHIEUNHAP', 'read'), 
('NQ03', 'PHIEUNHAP', 'create'),
('NQ03', 'SANPHAM', 'read'), 
('NQ03', 'SANPHAM', 'create'), 
('NQ03', 'SANPHAM', 'update'),
('NQ03', 'NHACUNGCAP', 'read'), 
('NQ03', 'NHACUNGCAP', 'create');

INSERT INTO ConNguoi (ID, HoTen, SDT, DiaChi) VALUES 
('NV01', 'Trương Phúc', '0909123456', 'Đà Nẵng'),
('NV02', 'Lê Văn Nam', '0909123457', 'Hà Nội'),
('NV03', 'Nguyễn Văn Kho', '0909999888', 'Hải Phòng'),
('KH01', 'Nguyễn Khách', '0912345678', 'TP.HCM'),
('KH02', 'Trần VIP', '0987654321', 'Cần Thơ');

INSERT INTO NhanVien (ID, ChucVu, Email, Luong) VALUES 
('NV01', 'Quản lý', 'phuc@sw.com', 20000000),
('NV02', 'Nhân viên bán hàng', 'nam@sw.com', 10000000),
('NV03', 'Nhân viên kho', 'kho@sw.com', 12000000);

INSERT INTO KhachHang (ID) VALUES 
('KH01'),
('KH02');

INSERT INTO TaiKhoan (TenDangNhap, MatKhau, MaNV, MaNhomQuyen, TrangThai) VALUES 
('admin', '123456', 'NV01', 'NQ01', 1), 
('nhanvien', '123456', 'NV02', 'NQ02', 1),
('kho', '123456', 'NV03', 'NQ03', 1);
-- --------------------------------------------------------

INSERT INTO LoaiSP VALUES 
('L01', 'Loa Bluetooth'),
('L02', 'Tai nghe Over-ear'),
('L03', 'Phụ kiện âm thanh');

INSERT INTO HangSX VALUES 
('H01', 'Marshall', 'Anh'),
('H02', 'Sony', 'Nhật'),
('H03', 'JBL', 'Mỹ');

INSERT INTO SanPham (MaSP, TenSP, MaLoai, MaHang, MoTa, ThoiGianBaoHanh, TrangThai, HinhAnh) VALUES 
('SP01', 'Marshall Stanmore III', 'L01', 'H01', 'Loa decor cực đẹp', 12, TRUE, 'marshall.jpg'),
('SP02', 'Sony WH-1000XM5', 'L02', 'H02', 'Chống ồn đỉnh cao', 12, TRUE, 'sony_xm5.jpg'),
('SP03', 'JBL Boombox 3', 'L01', 'H03', 'Loa di động công suất lớn, kháng nước IP67', 12, TRUE, 'jbl_boombox3.jpg'),
('SP04', 'Marshall Emberton II', 'L01', 'H01', 'Loa cầm tay nhỏ gọn, pin 30h', 12, TRUE, 'marshall_emberton_2.jpg'),
('SP05', 'Marshall Middleton', 'L01', 'H01', 'Loa Bluetooth 4 loa cực mạnh', 12, TRUE, 'marshall_middleton.jpg'),
('SP06', 'Sony SRS-XE300', 'L01', 'H02', 'Loa chống nước, âm thanh rộng', 12, TRUE, 'sony_xe300.jpg'),
('SP07', 'Sony WH-CH720N', 'L02', 'H02', 'Tai nghe chống ồn giá rẻ', 12, TRUE, 'sony_ch720n.jpg'),
('SP08', 'JBL Charge 5', 'L01', 'H03', 'Loa Bluetooth kiêm sạc dự phòng', 12, TRUE, 'jbl_charge5.jpg'),
('SP09', 'JBL PartyBox Encore', 'L01', 'H03', 'Loa kèm 2 Micro hát karaoke', 12, TRUE, 'jbl_encore.jpg'),
('SP10', 'JBL Flip 6', 'L01', 'H03', 'Loa di động âm thanh 2 đường tiếng', 12, TRUE, 'jbl_flip6.jpg'),

('SP11', 'JBL Go 3', 'L01', 'H03', 'Loa mini chống nước', 12, TRUE, 'jbl_go3.jpg'),
('SP12', 'Apple AirPods Pro 2', 'L02', 'H03', 'Chống ồn chủ động', 12, TRUE, 'airpods_pro.jpg'),
('SP13', 'Sennheiser HD 450BT', 'L02', 'H02', 'Âm thanh chi tiết, pin 30h', 24, TRUE, 'sennheiser_hd450.jpg'),
('SP14', 'Bose QuietComfort 45', 'L02', 'H03', 'Khử tiếng ồn đỉnh cao', 12, TRUE, 'bose_qc45.jpg'),
('SP15', 'JBL Tune 510BT', 'L02', 'H03', 'Bass mạnh, giá sinh viên', 12, TRUE, 'jbl_510bt.jpg'),
('SP16', 'Sony WH-1000XM4', 'L02', 'H02', 'Bản nâng cấp huyền thoại', 12, TRUE, 'sony_xm4.jpg'),
('SP17', 'Marshall Major IV', 'L02', 'H01', 'Pin 80h, sạc không dây', 12, TRUE, 'marshall_major4.jpg'),
('SP18', 'AirPods Max', 'L02', 'H03', 'Tai nghe Over-ear cao cấp', 12, TRUE, 'airpods_max.jpg'),
('SP19', 'Micro Shure SM58', 'L03', 'H03', 'Micro vocal chuyên nghiệp', 12, TRUE, 'shure_sm58.jpg'),
('SP20', 'Cáp Audio 3.5mm Ugreen', 'L03', 'H02', 'Dây bọc dù chống nhiễu', 6, TRUE, 'ugreen_35.jpg'),
('SP21', 'Giá treo tai nghe Gỗ', 'L03', 'H01', 'Decor gỗ Walnut sang trọng', 0, TRUE, 'stand_wood.jpg'),
('SP22', 'Bao da Marshall Willen', 'L03', 'H01', 'Bao bảo vệ silicon', 3, TRUE, 'case_willen.jpg'),
('SP23', 'Micro JBL PBM100', 'L03', 'H03', 'Micro karaoke có dây', 12, TRUE, 'jbl_pbm100.jpg'),
('SP24', 'Cáp sạc Type-C bọc thép', 'L03', 'H02', 'Sạc siêu bền cho loa', 6, TRUE, 'cable_c.jpg'),
('SP25', 'Bộ vệ sinh tai nghe 3 in 1', 'L03', 'H02', 'Dạng bút vệ sinh tiện lợi', 0, TRUE, 'cleaning_kit.jpg'),
('SP26', 'Jack chuyển 6.35mm sang 3.5mm', 'L03', 'H02', 'Mạ vàng cao cấp', 6, TRUE, 'jack_convert.jpg'),
('SP27', 'Túi đựng loa JBL Charge', 'L03', 'H03', 'Chống sốc EVA cao cấp', 3, TRUE, 'bag_jbl.jpg');


INSERT INTO PhienBanSP VALUES 
('PB01', 'SP01', 'Kem (Cream)', '80W', 'N/A', 'Bluetooth 5.2', 7000000, 9500000, 10, TRUE, 'marshall.jpg'),
('PB02', 'SP02', 'Đen (Black)', 'N/A', '30h', 'Bluetooth 5.2', 7000000, 9500000, 10, TRUE, 'sony_xm5.jpg'),
('PB03', 'SP03', 'Đen (Black)', '180W', '24h', 'Bluetooth 5.3', 8500000, 11900000, 10, TRUE, 'jbl_boombox3.jpg'),
('PB04', 'SP04', 'Đen Brass', '20W', '30h', 'Bluetooth 5.1', 3200000, 4500000, 10, TRUE, 'marshall_emberton_2.jpg'),
('PB05', 'SP05', 'Đen (Black)', '60W', '20h', 'Bluetooth 5.1', 6500000, 8500000, 10, TRUE, 'marshall_middleton.jpg'),
('PB06', 'SP06', 'Xám (Grey)', '30W', '24h', 'Bluetooth 5.2', 2800000, 3900000, 10, TRUE, 'sony_xe300.jpg'),
('PB07', 'SP07', 'Xanh (Blue)', 'N/A', '35h', 'Bluetooth 5.2', 1800000, 2500000, 15, TRUE, 'sony_ch720n.jpg'),
('PB08', 'SP08', 'Đen (Black)', '40W', '20h', 'Bluetooth 5.1', 3000000, 3900000, 12, TRUE, 'jbl_charge5.jpg'),
('PB09', 'SP09', 'Đen (Black)', '100W', '10h', 'Bluetooth 5.1', 5800000, 7500000,10 , TRUE, 'jbl_encore.jpg'),
('PB10', 'SP10', 'Đỏ (Red)', '20W', '12h', 'Bluetooth 5.1', 2200000, 2900000, 20, TRUE, 'jbl_flip6.jpg'),
('PB11', 'SP11', 'Xanh quân đội', '4.2W', '5h', 'Bluetooth 5.1', 800000, 1050000, 20, TRUE, 'jbl_go3.jpg'),
('PB12', 'SP12', 'Trắng', 'N/A', '6h', 'Bluetooth 5.3', 4500000, 5900000, 15, TRUE, 'airpods_pro.jpg'),
('PB13', 'SP13', 'Đen', 'N/A', '30h', 'Bluetooth 5.0', 2800000, 3500000, 10, TRUE, 'sennheiser_hd450.jpg'),
('PB14', 'SP14', 'Trắng', 'N/A', '24h', 'Bluetooth 5.1', 5500000, 7200000, 8, TRUE, 'bose_qc45.jpg'),
('PB15', 'SP15', 'Hồng', 'N/A', '40h', 'Bluetooth 5.0', 700000, 1200000, 30, TRUE, 'jbl_510bt.jpg'),
('PB16', 'SP16', 'Bạc', 'N/A', '30h', 'Bluetooth 5.0', 5000000, 6500000, 12, TRUE, 'sony_xm4.jpg'),
('PB17', 'SP17', 'Nâu da', 'N/A', '80h', 'Bluetooth 5.0', 3000000, 4200000, 10, TRUE, 'marshall_major4.jpg'),
('PB18', 'SP18', 'Xanh Sky', 'N/A', '20h', 'Bluetooth 5.0', 10000000, 13500000, 5, TRUE, 'airpods_max.jpg'),
('PB19', 'SP19', 'Xám', 'N/A', 'N/A', 'XLR', 2100000, 2800000, 10, TRUE, 'shure_sm58.jpg'),
('PB20', 'SP20', 'Đen', 'N/A', 'N/A', '3.5mm', 150000, 250000, 100, TRUE, 'ugreen_35.jpg'),
('PB21', 'SP21', 'Gỗ Walnut', 'N/A', 'N/A', 'N/A', 300000, 550000, 15, TRUE, 'stand_wood.jpg'),
('PB22', 'SP22', 'Đen', 'N/A', 'N/A', 'Silicon', 100000, 190000, 50, TRUE, 'case_willen.jpg'),
('PB23', 'SP23', 'Đen', 'N/A', 'N/A', 'Cáp 3m', 700000, 950000, 20, TRUE, 'jbl_pbm100.jpg'),
('PB24', 'SP24', 'Đỏ', '60W', 'N/A', 'Type-C', 120000, 220000, 80, TRUE, 'cable_c.jpg'),
('PB25', 'SP25', 'Nhiều màu', 'N/A', 'N/A', 'N/A', 50000, 95000, 200, TRUE, 'cleaning_kit.jpg'),
('PB26', 'SP26', 'Vàng', 'N/A', 'N/A', 'Mạ vàng', 80000, 150000, 60, TRUE, 'jack_convert.jpg'),
('PB27', 'SP27', 'Đen', 'N/A', 'N/A', 'Vải Canvas', 200000, 350000, 40, TRUE, 'bag_jbl.jpg');
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
('777888999', 'PB01', 'PN01', 'Trong kho'), 
('840100001', 'PB01', 'PN01', 'Trong kho'), 
('840100002', 'PB01', 'PN01', 'Trong kho'),
('840100003', 'PB01', 'PN01', 'Trong kho'),
('123123123', 'PB02', 'PN01', 'Trong kho'),
('456456456', 'PB02', 'PN01', 'Trong kho'),
('999888777', 'PB02', 'PN01', 'Đã bán'),
('333111001', 'PB03', 'PN01', 'Trong kho');
-- ------------------------------------------------------

INSERT INTO KhuyenMai (MaKM, TenKM, DieuKienGiam, PhanTramGiam, NgayBatDau, NgayKetThuc, TrangThai) VALUES 
('KM01', 'Khai trương', 0, 10, '2025-01-01', '2030-12-31', 1), 
('KM02', 'Siêu sale Black Friday', 5000000, 20, '2026-11-20', '2026-11-30', 1),
('KM03', 'Hè rực rỡ', 0, 5, '2026-06-01', '2026-08-31', 1),
('KM04', 'Chào năm mới 2027', 2000000, 15, '2026-12-25', '2027-01-05', 1),
('KM05', 'Tri ân khách hàng VIP', 10000000, 25, '2026-01-01', '2026-12-31', 1),
('KM06', 'Giảm giá cuối tháng', 1000000, 8, '2026-03-25', '2026-03-31', 1);

INSERT INTO PhieuXuat (MaPhieuXuat, MaNV, MaKH, MaKM) VALUES 
('PX01', 'NV01', 'KH01', 'KM01');

INSERT INTO ChiTietPhieuXuat (MaPhieuXuat, MaPhienBan, SoLuong, DonGia) VALUES 
('PX01', 'PB01', 1, 8550000);

UPDATE ChiTietSP 
SET TinhTrang = 'Trong kho', MaPhieuXuat = NULL 
WHERE MaImei IN ('111222333', '444555666', '777888999');

UPDATE ChiTietSP 
SET TinhTrang = 'Đã bán', MaPhieuXuat = 'PX01' 
WHERE MaImei = '111222333';

INSERT IGNORE INTO PhieuXuat (MaPhieuXuat, MaNV, MaKH, TongTien) VALUES 
('PX01', 'NV01', 'KH01', 8550000);
-- --------------------------------------------------------

INSERT INTO PhieuXuat (MaPhieuXuat, NgayXuat, MaNV, MaKH, TongTien) VALUES 
('PX02', '2026-02-20 10:30:00', 'NV02', 'KH02', 30900000);

UPDATE ChiTietSP SET TinhTrang = 'Đã bán', MaPhieuXuat = 'PX01' WHERE MaImei IN ('111222333', '444555666', '777888999');
UPDATE ChiTietSP SET TinhTrang = 'Đã bán', MaPhieuXuat = 'PX02' WHERE MaImei IN ('123123123', '456456456', '333111001');

INSERT INTO BaoHanh (MaBH, MaImei, MaPhieuXuat, NgayBatDau, NgayKetThuc, TinhTrang) VALUES 
('BH01', '111222333', 'PX01', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 12 MONTH), 'Đang sửa chữa'),
('BH02', '444555666', 'PX01', '2026-01-10', '2027-01-10', 'Đã trả máy'),
('BH03', '777888999', 'PX01', '2026-02-05', '2027-02-05', 'Đang sửa chữa'), 
('BH04', '123123123', 'PX02', '2026-02-22', '2027-02-22', 'Hoàn thành'),
('BH05', '456456456', 'PX02', '2026-02-24', '2027-02-24', 'Đang sửa chữa'), 
('BH06', '333111001', 'PX02', '2026-02-25', '2027-02-25', 'Đang sửa chữa');


INSERT INTO ChiTietBaoHanh (MaCTBH, MaBH, NoiDung, TinhTrang) VALUES 
('CTBH01', 'BH01', 'Loa bị rè bass', 'Đang sửa chữa'),
('CTBH02', 'BH02', 'Lỗi kết nối Bluetooth chập chờn', 'Đã trả máy'),
('CTBH03', 'BH02', 'Vệ sinh chân sạc miễn phí', 'Đã trả máy'),
('CTBH04', 'BH03', 'Hỏng pin - Sạc không vào điện', 'Đang sửa chữa'),
('CTBH05', 'BH04', 'Khách báo chống ồn ANC hoạt động chập chờn', 'Đang sửa chữa'),
('CTBH06', 'BH04', 'Cập nhật lại Firmware phiên bản mới nhất', 'Hoàn thành'),
('CTBH07', 'BH05', 'Hỏng da đệm tai (Earpads)', 'Đang sửa chữa'),
('CTBH08', 'BH06', 'Loa không lên nguồn', 'Đang sửa chữa'),
('CTBH09', 'BH06', 'Kiểm tra pin và mạch sạc', 'Đang sửa chữa');
INSERT INTO NCC_SanPham VALUES 
('NCC01', 'SP01'), 
('NCC03', 'SP03'),
('NCC01', 'SP04'), 
('NCC01', 'SP05'), 
('NCC02', 'SP06'), 
('NCC02', 'SP07'), 
('NCC03', 'SP08'), 
('NCC03', 'SP09'), 
('NCC03', 'SP10'),
('NCC03', 'SP11'), 
('NCC03', 'SP12'), 
('NCC02', 'SP13'), 
('NCC03', 'SP14'), 
('NCC03', 'SP15'), 
('NCC02', 'SP16'), 
('NCC01', 'SP17'), 
('NCC03', 'SP18'), 
('NCC03', 'SP19'), 
('NCC02', 'SP20'), 
('NCC01', 'SP21'), 
('NCC01', 'SP22'), 
('NCC03', 'SP23'), 
('NCC02', 'SP24'), 
('NCC02', 'SP25'), 
('NCC02', 'SP26'), 
('NCC03', 'SP27');

