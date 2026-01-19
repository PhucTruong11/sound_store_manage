-- Tạo Database tên là 'quanlylaptop' (nếu chưa có) --
CREATE DATABASE IF NOT EXISTS quanlyamthanh CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE quanlyamthanh;



-- ==========================================
-- Nhóm Con Người (Kế thừa)
-- ==========================================
CREATE TABLE ConNguoi (
    ID VARCHAR(20) NOT NULL, -- Định dạng: NV001 hoặc KH001
    HoTen VARCHAR(100) NOT NULL,
    SDT VARCHAR(20),
    DiaChi VARCHAR(255)
);

CREATE TABLE NhanVien (
    ID VARCHAR(20) NOT NULL,
    ChucVu VARCHAR(50),
    Email VARCHAR(100),
    Luong DOUBLE
);

CREATE TABLE KhachHang (
    ID VARCHAR(20) NOT NULL
);

-- ==========================================
-- Sản phẩm
-- ==========================================
CREATE TABLE SanPham (
    MaSP VARCHAR(20) NOT NULL,
    TenSP VARCHAR(100) NOT NULL,
    MaLoai VARCHAR(20),
    MaHang VARCHAR(20),
    MauSac VARCHAR(50),      -- Thêm trực tiếp
    CongSuat VARCHAR(50),    -- Thêm trực tiếp
    Pin VARCHAR(50),         -- Thêm trực tiếp
    KetNoi VARCHAR(100),     -- Thêm trực tiếp
    GiaNhap DOUBLE,
    GiaBan DOUBLE,
    SoLuongTon INT DEFAULT 0,
    TonTai BOOLEAN DEFAULT TRUE
);

CREATE TABLE Imei (
    MaImei VARCHAR(50) NOT NULL PRIMARY KEY,
    MaSP VARCHAR(20),
    TinhTrang VARCHAR(50) DEFAULT 'Trong kho' -- 'Trong kho', 'Đã bán', 'Bảo hành'
);

CREATE TABLE LoaiSP ( 
    MaLoai VARCHAR(20) NOT NULL, 
    TenLoai VARCHAR(50) NOT NULL
);


CREATE TABLE HangSX ( 
    MaHang VARCHAR(20) NOT NULL, 
    TenHang VARCHAR(50) NOT NULL 
);


-- ==========================================
-- Nhập hàng
-- ==========================================
CREATE TABLE HoaDonNhapHang (
    MaHDNhap VARCHAR(20) NOT NULL,
    NgayNhap DATETIME DEFAULT CURRENT_TIMESTAMP,
    MaNV VARCHAR(20),
    MaNCC VARCHAR(20),
    TongTien DOUBLE DEFAULT 0
);

CREATE TABLE ChiTietHDNhap ( 
    MaHDNhap VARCHAR(20) NOT NULL, 
    MaSP VARCHAR(20) NOT NULL, 
    SoLuong INT, 
    ThanhTien DOUBLE 
);

CREATE TABLE NhaCungCap ( 
    MaNCC VARCHAR(20) NOT NULL, 
    TenNCC VARCHAR(100), 
    DiaChi VARCHAR(255), 
    Sdt VARCHAR(20) 
);

-- ==========================================
-- Bán hàng
-- ==========================================
CREATE TABLE HoaDonBanHang (
    MaHDBan VARCHAR(20) NOT NULL,
    NgayBan DATETIME DEFAULT CURRENT_TIMESTAMP,
    MaNV VARCHAR(20),
    MaKH VARCHAR(20),
    MaKM VARCHAR(20),
    TongTien DOUBLE DEFAULT 0
);

CREATE TABLE ChiTietHDBan ( 
    MaHDBan VARCHAR(20) NOT NULL, 
    MaSP VARCHAR(20) NOT NULL, 
    SoLuong INT, 
    ThanhTien DOUBLE 
);

CREATE TABLE KhuyenMai (
    MaKM VARCHAR(20) NOT NULL, 
    TenKM VARCHAR(100), 
    DieuKienGiam DOUBLE, 
    PhanTramGiam INT, 
    NgayBatDau DATE, 
    NgayKetThuc DATE 
);

CREATE TABLE BaoHanh ( 
    MaBH VARCHAR(20) NOT NULL, 
    MaSP VARCHAR(20), 
    MaHDBan VARCHAR(20), 
    NgayKetThuc DATE 
);

CREATE TABLE ChiTietBaoHanh ( 
    MaCTBH VARCHAR(20) NOT NULL, 
    MaBH VARCHAR(20), 
    NoiDung TEXT, 
    TinhTrang VARCHAR(50) 
);

-- ==========================================
-- THIẾT LẬP TRIGGER CẬP NHẬT TỒN KHO
-- ==========================================
DELIMITER //

-- 1. Tự động TĂNG tồn kho khi NHẬP hàng
CREATE TRIGGER trg_UpdateStockAfterImport
AFTER INSERT ON ChiTietHDNhap
FOR EACH ROW
BEGIN
    UPDATE SanPham 
    SET SoLuongTon = SoLuongTon + NEW.SoLuong 
    WHERE MaSP = NEW.MaSP;
END//

-- 2. Tự động GIẢM tồn kho khi BÁN hàng
CREATE TRIGGER trg_UpdateStockAfterExport
AFTER INSERT ON ChiTietHDBan
FOR EACH ROW
BEGIN
    UPDATE SanPham 
    SET SoLuongTon = SoLuongTon - NEW.SoLuong 
    WHERE MaSP = NEW.MaSP;
END//

DELIMITER ;

-- KHÓA CHÍNH (PRIMARY KEY)
ALTER TABLE ConNguoi ADD PRIMARY KEY (ID);
ALTER TABLE KhachHang ADD PRIMARY KEY (ID);
ALTER TABLE NhanVien ADD PRIMARY KEY (ID);
ALTER TABLE SanPham ADD PRIMARY KEY (MaSP);
ALTER TABLE Imei ADD PRIMARY KEY (MaImei);
ALTER TABLE LoaiSP ADD PRIMARY KEY (MaLoai);
ALTER TABLE HangSX ADD PRIMARY KEY (MaHang);
ALTER TABLE NhaCungCap ADD PRIMARY KEY (MaNCC);
ALTER TABLE KhuyenMai ADD PRIMARY KEY (MaKM);
ALTER TABLE HoaDonBanHang ADD PRIMARY KEY (MaHDBan);
ALTER TABLE ChiTietHDBan ADD PRIMARY KEY (MaHDBan, MaSP);
ALTER TABLE HoaDonNhapHang ADD PRIMARY KEY (MaHDNhap);
ALTER TABLE ChiTietHDNhap ADD PRIMARY KEY (MaHDNhap, MaSP);
ALTER TABLE BaoHanh ADD PRIMARY KEY (MaBH);
ALTER TABLE ChiTietBaoHanh ADD PRIMARY KEY (MaCTBH);

-- KHÓA NGOẠI (FOREIGN KEY)
ALTER TABLE KhachHang ADD CONSTRAINT FK_KH_Person FOREIGN KEY (ID) REFERENCES ConNguoi(ID);
ALTER TABLE NhanVien ADD CONSTRAINT FK_NV_Person FOREIGN KEY (ID) REFERENCES ConNguoi(ID);

ALTER TABLE SanPham ADD CONSTRAINT FK_SP_Loai FOREIGN KEY (MaLoai) REFERENCES LoaiSP(MaLoai);
ALTER TABLE SanPham ADD CONSTRAINT FK_SP_Hang FOREIGN KEY (MaHang) REFERENCES HangSX(MaHang);
ALTER TABLE Imei ADD CONSTRAINT FK_Imei_SP FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

ALTER TABLE HoaDonBanHang ADD CONSTRAINT FK_HDB_NV FOREIGN KEY (MaNV) REFERENCES NhanVien(ID);
ALTER TABLE HoaDonBanHang ADD CONSTRAINT FK_HDB_KH FOREIGN KEY (MaKH) REFERENCES KhachHang(ID);
ALTER TABLE HoaDonBanHang ADD CONSTRAINT FK_HDB_KM FOREIGN KEY (MaKM) REFERENCES KhuyenMai(MaKM);
ALTER TABLE ChiTietHDBan ADD CONSTRAINT FK_CTB_HD FOREIGN KEY (MaHDBan) REFERENCES HoaDonBanHang(MaHDBan);
ALTER TABLE ChiTietHDBan ADD CONSTRAINT FK_CTB_SP FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

ALTER TABLE HoaDonNhapHang ADD CONSTRAINT FK_HDN_NV FOREIGN KEY (MaNV) REFERENCES NhanVien(ID);
ALTER TABLE HoaDonNhapHang ADD CONSTRAINT FK_HDN_NCC FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC);
ALTER TABLE ChiTietHDNhap ADD CONSTRAINT FK_CTN_HD FOREIGN KEY (MaHDNhap) REFERENCES HoaDonNhapHang(MaHDNhap);
ALTER TABLE ChiTietHDNhap ADD CONSTRAINT FK_CTN_SP FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);

ALTER TABLE BaoHanh ADD CONSTRAINT FK_BH_SP FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP);
ALTER TABLE BaoHanh ADD CONSTRAINT FK_BH_HD FOREIGN KEY (MaHDBan) REFERENCES HoaDonBanHang(MaHDBan);
ALTER TABLE ChiTietBaoHanh ADD CONSTRAINT FK_CTBH_BH FOREIGN KEY (MaBH) REFERENCES BaoHanh(MaBH);


-- ==========================================
-- 6. DỮ LIỆU MẪU (DUMMY DATA)
-- ==========================================

-- Nhân viên (6)
INSERT INTO ConNguoi VALUES 
  ('NV01','Phúc Trương','0901','Đà Nẵng'),
  ('NV02','Văn Nam','0902','Hà Nội'),
  ('NV03','Hoàng Bảo','0903','HCM'),
  ('NV04','Minh Hưng','0904','Cần Thơ'),
  ('NV05','Ngọc Ân','0905','Huế'),
  ('NV06','Thanh Tùng','0906','Hải Phòng');

INSERT INTO NhanVien VALUES 
  ('NV01','Quản lý','phuc@sw.com',15000),
  ('NV02','Bán hàng','nam@sw.com',8000),
  ('NV03','Kho','bao@sw.com',9000),
  ('NV04','Bán hàng','hung@sw.com',8000),
  ('NV05','Kỹ thuật','an@sw.com',10000),
  ('NV06','Bán hàng','tung@sw.com',8000);

-- Khách hàng (10)
INSERT INTO ConNguoi VALUES 
  ('KH001','Anh Tuấn','0801','Quận 1'),
  ('KH002','Bảo Ngọc','0802','Quận 3'),
  ('KH003','Cẩm Tú','0803','Quận 5'),
  ('KH004','Duy Mạnh','0804','Quận 7'),
  ('KH005','Elena','0805','Quận 10'),
  ('KH006','Hoàng Long','0806','Bình Thạnh'),
  ('KH007','Khánh Linh','0807','Gò Vấp'),
  ('KH008','Minh Triết','0808','Thủ Đức'),
  ('KH009','Như Ý','0809','Phú Nhuận'),
  ('KH010','Quốc Việt','0810','Tân Bình');

INSERT INTO KhachHang (ID) SELECT ID FROM ConNguoi WHERE ID LIKE 'KH%';

-- Loại SP & Hãng (5)
INSERT INTO LoaiSP VALUES 
  ('L01','Loa Bluetooth'),
  ('L02','Tai nghe In-ear'),
  ('L03','Tai nghe Over-ear'),
  ('L04','Amply'),
  ('L05','Microphone');

INSERT INTO HangSX VALUES 
  ('H01','Marshall'),
  ('H02','Sony'),
  ('H03','JBL'),
  ('H04','Bose'),
  ('H05','Sennheiser');

-- Nhà cung cấp & Khuyến mãi (5)
INSERT INTO NhaCungCap VALUES 
  ('NCC01','Marshall VN','Hà Nội','0911'),
  ('NCC02','Sony Store','HCM','0922'),
  ('NCC03','JBL Official','Đà Nẵng','0933'),
  ('NCC04','Bose VN','Cần Thơ','0944'),
  ('NCC05','Sennheiser Dist','Hải Phòng','0955');

INSERT INTO KhuyenMai VALUES 
  ('KM001','Chào hè',5000,10,'2025-06-01','2025-08-31'),
  ('KM002','Âm thanh đỉnh',10000,15,'2025-09-01','2025-10-30'),
  ('KM003','Black Friday',20000,20,'2025-11-20','2025-11-30'),
  ('KM004','Giáng sinh',5000,5,'2025-12-15','2025-12-31'),
  ('KM005','Tết âm',8000,10,'2026-01-01','2026-02-15');

-- Sản phẩm (10)
INSERT INTO SanPham VALUES 
  ('SP001','Marshall Stanmore III','L01','H01','Kem','80W','N/A','Bluetooth 5.2',7000,9500,20,1),
  ('SP002','Sony WH-1000XM5','L03','H02','Đen','N/A','30h','Bluetooth',6000,8490,15,1),
  ('SP003','JBL Charge 5','L01','H03','Xanh','40W','20h','Bluetooth 5.1',3000,3990,30,1),
  ('SP004','Bose QC Ultra','L03','H04','Trắng','N/A','24h','Wireless',8000,10500,10,1),
  ('SP005','Sennheiser M4','L02','H05','Đen','N/A','60h','Bluetooth 5.2',7000,9200,12,1),
  ('SP006','Marshall Emberton II','L01','H01','Đen','20W','30h','Bluetooth',3500,4500,25,1),
  ('SP007','Sony WF-C500','L02','H02','Xanh dương','N/A','10h','TWS',1200,1800,40,1),
  ('SP008','JBL PartyBox 110','L01','H03','Đen','160W','12h','Bluetooth',8000,11000,8,1),
  ('SP009','Bose SoundLink','L01','H04','Bạc','5W','12h','Bluetooth',4000,5200,18,1),
  ('SP010','Sony SRS-XE200','L01','H02','Cam','10W','16h','Bluetooth',2000,2900,22,1);

INSERT INTO Imei (MaImei, MaSP, TinhTrang) VALUES 
('MS1-001', 'SP001', 'Đã bán'),
('MS1-002', 'SP001', 'Trong kho'),
('SN2-001', 'SP002', 'Đã bán');

-- Hóa đơn Nhập (10)
INSERT INTO HoaDonNhapHang (MaHDNhap, MaNV, MaNCC, TongTien) VALUES 
  ('HDN001','NV03','NCC01',70000),
  ('HDN002','NV03','NCC02',60000),
  ('HDN003','NV03','NCC03',30000),
  ('HDN004','NV03','NCC04',80000),
  ('HDN005','NV03','NCC05',70000),
  ('HDN006','NV03','NCC01',35000),
  ('HDN007','NV03','NCC02',12000),
  ('HDN008','NV03','NCC03',80000),
  ('HDN009','NV03','NCC04',40000),
  ('HDN010','NV03','NCC05',20000);

INSERT INTO ChiTietHDNhap SELECT MaHDNhap, 'SP001', 10, TongTien FROM HoaDonNhapHang;

-- Hóa đơn Bán (10)
INSERT INTO HoaDonBanHang (MaHDBan, MaNV, MaKH, MaKM, TongTien) VALUES 
  ('HDB001','NV02','KH001','KM001',8550),
  ('HDB002','NV04','KH002',NULL,8490),
  ('HDB003','NV06','KH003','KM001',3591),
  ('HDB004','NV02','KH004','KM002',8925),
  ('HDB005','NV04','KH005',NULL,9200),
  ('HDB006','NV06','KH006','KM003',3600),
  ('HDB007','NV02','KH007',NULL,1800),
  ('HDB008','NV04','KH008','KM005',9900),
  ('HDB009','NV06','KH009',NULL,5200),
  ('HDB010','NV02','KH010','KM001',2610);

INSERT INTO ChiTietHDBan SELECT MaHDBan, 'SP001', 1, TongTien FROM HoaDonBanHang;