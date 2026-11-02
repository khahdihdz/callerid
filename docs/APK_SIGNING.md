# APK signing

Dự án dùng bốn GitHub Actions secrets được chuẩn hoá bởi [khahdihdz/apksign](https://github.com/khahdihdz/apksign):

- ANDROID_SIGNING_KEYSTORE_BASE64
- ANDROID_SIGNING_STORE_PASSWORD
- ANDROID_SIGNING_KEY_ALIAS
- ANDROID_SIGNING_KEY_PASSWORD

Workflow Android giải mã keystore trong runner tạm thời, build release, sau đó chạy Android SDK apksigner để xác minh chữ ký. Không lưu keystore trong Git.

Repository apksign có workflow đồng bộ secrets trung tâm. Sau khi repository mới được tạo, chạy workflow **Sync Android signing to all repositories** tại apksign để đồng bộ secrets nếu repository chưa được nhận diện tự động. Release tag dạng vX.Y.Z sẽ kích hoạt build ký APK.

Lệnh phát hành: `git tag v0.1.0` rồi `git push origin v0.1.0`.

Không thay đổi applicationId hoặc signing key sau khi phát hành phiên bản đầu tiên.
