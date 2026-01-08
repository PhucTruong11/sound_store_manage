# 📘 HƯỚNG DẪN SỬ DỤNG GIT CHO NHÓM ĐỒ ÁN

**Dự án:** Quản lý cửa hàng Laptop (Java Swing - 3 Tier Architecture)
**Repository:** `https://github.com/PhucTruong11/laptop_store_manage.git`

---

## 1. THIẾT LẬP BAN ĐẦU (Chỉ làm 1 lần)

Mỗi thành viên thực hiện các bước sau để đưa code về máy cá nhân:

1. Mở thư mục trên máy muốn chứa code.
2. Chuột phải chọn **Open in Terminal** (hoặc mở VS Code nhấn `Ctrl + ~`).
3. Chạy các lệnh sau:
```bash
# Clone dự án về máy
git clone https://github.com/PhucTruong11/laptop_store_manage.git

# Di chuyển vào thư mục dự án
cd laptop_store_manage

# Cấu hình danh tính (Nếu chưa làm trong quá trình cài đặt git)
git config --global user.name "Họ Tên Của Bạn"
git config --global user.email "email_cua_ban@gmail.com"

```

---

## 2. PHÂN CHIA NHÁNH THEO NHIỆM VỤ

Để tránh đè code lên nhau, tuyệt đối **KHÔNG** code trên nhánh `main`. Mỗi người tạo một nhánh riêng theo nhiệm vụ đã phân công:

| Thành viên | Nhiệm vụ | Lệnh tạo nhánh |
| --- | --- | --- |
| **Bạn 1** | Hóa đơn bán hàng + Chi tiết | `git checkout -b feature-hoadon-ban` |
| **Bạn 2** | Hóa đơn nhập hàng + Chi tiết | `git checkout -b feature-hoadon-nhap` |
| **Bạn 3** | Nhân viên + Khách hàng | `git checkout -b feature-nhansu` |
| **Bạn 4** | Quản lý Sản phẩm | `git checkout -b feature-sanpham` |
| **Bạn 5** | Khuyến mãi + Đăng nhập | `git checkout -b feature-auth` |

---

## 3. QUY TRÌNH CODE VÀ BACKUP HÀNG NGÀY

Sau khi đã ở đúng nhánh nhiệm vụ của mình, hãy thực hiện chu trình này để lưu code:

**Bước 1: Lưu code vào máy (Local)**
Thực hiện sau khi viết xong một hàm hoặc một giao diện nhỏ:

```bash
git add .
git commit -m "Mô tả việc vừa làm (ví dụ: xong giao diện sản phẩm)"

```

**Bước 2: Đẩy code lên GitHub (Cloud)**
Thực hiện để backup code, phòng trường hợp hỏng máy hoặc để Leader thấy tiến độ:

```bash
git push origin <tên-nhánh-của-bạn>

```

---

## 4. GỘP CODE VÀO NHÁNH CHÍNH (MAIN)

Khi bạn đã hoàn thành 100% nhiệm vụ của mình và code chạy ổn định:

1. Lên link GitHub của dự án.
2. Bấm vào nút **"Compare & pull request"** hiện lên ở đầu trang.
3. Viết ghi chú ngắn gọn những gì đã làm và bấm **Create Pull Request**.
4. **Leader** sẽ kiểm tra code và bấm **Merge** để đưa code vào bản chính `main`.

---

## 5. ĐỒNG BỘ CODE MỚI TỪ BẠN BÈ

Khi Leader báo "Đã cập nhật Main", tất cả thành viên phải kéo code mới nhất về để dự án của mình không bị cũ:

```bash
# 1. Chuyển về nhánh main
git checkout main

# 2. Kéo code mới nhất về
git pull origin main

# 3. Quay lại nhánh nhiệm vụ của mình
git checkout <tên-nhánh-của-bạn>

# 4. Gộp code mới vào nhánh của mình để tiếp tục làm
git merge main

```

---

## ⚠️ LƯU Ý QUAN TRỌNG (SỐNG CÒN)

* **Không bao giờ sửa file của người khác** trừ khi đã trao đổi trước.
* **File `.gitignore**`: Đã được thiết lập để bỏ qua các file rác Java (`.class`, `target/`). Đừng xóa file này.
* **Conflict (Xung đột)**: Nếu khi `merge` hiện thông báo màu đỏ (Conflict), hãy mở file đó trong VS Code, chọn đoạn code đúng, lưu lại rồi `commit` như bình thường.
* **Luôn `git status**`: Để biết mình đang ở nhánh nào trước khi gõ lệnh.

---
