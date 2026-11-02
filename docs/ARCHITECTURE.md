# Kiến trúc

Ứng dụng theo hướng offline-first và không có backend mạng bắt buộc.

- Presentation: Jetpack Compose.
- Domain: các use-case và quy tắc nghiệp vụ Kotlin (đang mở rộng).
- Data: Room/SQLite, DAO và repository.
- Platform: Android Telephony integration qua CallScreeningService.

Luồng tra cứu: UI -> PhoneRepository -> PhoneDao -> Room -> SQLite.

CallScreeningService ở bản nền tảng luôn cho phép cuộc gọi. Chỉ bật chặn tự động sau khi có dữ liệu uy tín, quy tắc minh bạch và tuỳ chọn người dùng.

Bản đầu tiên có bảng phone_numbers. Các bảng báo cáo, blocklist, allowlist và lịch sử sẽ được bổ sung cùng migration có kiểm thử.
