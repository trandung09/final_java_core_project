# Simple 2D Pixel Game - Java Core Project

## 📖 Mô tả dự án

Đây là đồ án kết thúc môn học **Java Core** của tôi. Nó là một trò chơi 2D pixel đơn giản được xây dựng bằng Java với JDK 17, sử dụng Maven làm công cụ quản lý build. Trò chơi cho phép người chơi điều khiển nhân vật để tránh chướng ngại vật, thu thập các 
vật phẩm (coin, mana...), tiêu diệt các quái vật trong mỗi màn chơi và ghi điểm cho tới khi hoàn thành màn chơi cuối cùng và giành chiến thắng.

Dự án này nhằm mục đích củng cố các kiến thức đã học trong môn Java Core, đồng thời mở rộng khả năng thực hành trong lập trình đồ họa và xử lý sự kiện.

---

## 🛠️ Công nghệ sử dụng

- **Ngôn ngữ**: Java 17
- **Build Tool**: Maven
- **Thư viện hỗ trợ**:
  - `javax.swing` và `java.awt` để xây dựng giao diện đồ họa.
  - `Lombok` để giảm code lặp.

---

## 🕹️ Gameplay

### Kịch bản
Người chơi điều khiển một nhân vật 2D pixel-style, di chuyển trong môi trường đơn giản, tránh các chướng ngại vật, tiêu diệt các quái vật trong mỗi màn chơi
để được cộng kinh nghiệm, tăng sức mạnh. Thu thập các vật phẩm có sẵn trên bản đồ và các vẩt phẩm từ việc tiêu diệt các quái vật phục vụ cho người chơi như 
tăng tốc chạy, coin, mana... Người chơi phải vượt qua tất cả các màn chơi để giành được chiến thắng.

### Các yếu tố chính:
- **Nhân vật chính**: Người chơi điều khiển nhân vật bằng bàn phím.
- **Chướng ngại vật**: Xuất hiện ngẫu nhiên trong mỗi màn chơi
- **Quái vật**: hành được được tạo một các ngẫu nhiên, ưu tiên tìm tới người chơi để tấn công

### Cách điều khiển:
- **Di chuyển**: Phím `W`, `A`, `S`, `D`.
- **Tạm dừng/tiếp tục**: Nhấn `P`.
- **Trỏ tới các menu item:** Nhấn `↑`, `↓`
- **Thay đổi hiệu ứng ánh sáng**: Phím `L`
---

## 📋 Mục tiêu của dự án

- Áp dụng kiến thức Java Core: 
  - Lập trình hướng đối tượng (OOP).
  - Xử lý sự kiện (Event Handling).
  - Quản lý luồng (Threads).
  - ...
- Hiểu cách tổ chức dự án sử dụng Maven.
- Tích hợp kỹ thuật vẽ và cập nhật đồ họa trong Java.

---
## 📚 Nguồn tài nguyên

Trong quá trình thực hiện dự án, tôi đã sử dụng các tài nguyên sau đây với sự cho phép của tác giả:

1. **Các hoạt ảnh nhân vật, title, vật phẩm, âm thanh**:
   - RyiSnow - [Image and audio resources](https://drive.google.com/drive/folders/1OBRM8M3qCNAfJDCaldg62yFMiyFaKgYx) Nguồn tài nguyên có sẵn được tạo sẵn và cho phép sử dụng với các dự án cá nhân không mở bởi **RyiSnow**.
     Dưới đây là lời ngỏ bởi tác giả: [Guidelines for using resource assets](https://docs.google.com/document/d/1qcafOofpXYd_QPr95qbgfb1GYxXKgSZb/edit ) (Vui lòng tuân thủ các yêu cầu của tác giả).


3. **Hướng dẫn và tài liệu tham khảo**:
   - Tài liệu sử dụng để tìm hiểu các lớp trong `javax.swing` và `java.awt`: [Oracle Java Documentation](https://docs.oracle.com/en/java/).
   - Các bài viết hướng dẫn làm game cơ bản với Java: [Hướng dẫn làm game cơ bản với Java](https://gameiter.blogspot.com/p/lap-trinh-game-co-ban.html?fbclid=IwAR3fRZLgK5lLMthft-czudVVKgqll2QYX4KzJi6dpo01BaHQ9h4whU2d7Ps)

5. **Công cụ hỗ trợ**:
   - Tiled Map Editor: [Piskel app](https://www.piskelapp.com/) Công cụ sử dụng để thiết kế bản đồ pixel trong trò chơi.

**Lưu ý**: Các tài nguyên trên đều tuân thủ giấy phép miễn phí hoặc có ghi nhận quyền sở hữu (nếu cần).

---
## ⚙️ Cách cài đặt và chạy

### Yêu cầu:
- **JDK**: Phiên bản 17 hoặc cao hơn.
- **Maven**: Phiên bản 3.8 hoặc cao hơn.
- **IDE**: Intellij IDE, Eclipse hoặc VsCode.

### Hướng dẫn:
1. Clone repository:
   ```bash
   git clone https://github.com/trandung09/final_java_core_project.git
2. Mở project trên ide và khởi chạy ứng dụng
