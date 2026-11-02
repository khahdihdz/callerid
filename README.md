# Caller ID & Spam Shield

Ứng dụng Android nhận diện cuộc gọi và quản lý số spam theo hướng offline-first. Backend nghiệp vụ chạy trực tiếp trên thiết bị bằng Kotlin; Room/SQLite là cơ sở dữ liệu cục bộ. Không yêu cầu VPS, Firebase hoặc Cloudflare.

## Trạng thái

Đang triển khai nền tảng Android native. Caller ID sử dụng Android `CallScreeningService` khi người dùng cấp vai trò sàng lọc cuộc gọi. Nhận diện số chỉ chính xác khi có dữ liệu cục bộ; ứng dụng không tuyên bố tra cứu danh tính thuê bao toàn cầu.

## Build

Mở project bằng Android Studio hoặc chạy `gradle assembleDebug` với JDK 17, Gradle 8.9 và Android SDK Platform 35.

## APK signing

GitHub Actions sử dụng bốn repository secrets được đồng bộ từ `khahdihdz/apksign`: `ANDROID_SIGNING_KEYSTORE_BASE64`, `ANDROID_SIGNING_STORE_PASSWORD`, `ANDROID_SIGNING_KEY_ALIAS`, `ANDROID_SIGNING_KEY_PASSWORD`. Không commit keystore hoặc secrets vào Git.

## Privacy

Danh bạ và nhật ký cuộc gọi không được tải lên máy chủ. Xem [Privacy Policy](PRIVACY_POLICY.md).

## License

MIT.