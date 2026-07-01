# AirDrop for Android

⚡ Lightning-fast, peer-to-peer file sharing for Android. No cables, no internet, no hassle.

## Overview

AirDrop is a high-performance file transfer application for Android that enables seamless peer-to-peer (P2P) file sharing between devices using local WiFi networks and Bluetooth connectivity.

## Features

- ✨ **Lightning-Fast Transfer**: Optimized P2P protocol for maximum speed
- 📱 **No Internet Required**: Works entirely on local networks
- 🔐 **Secure**: Encrypted peer-to-peer connections
- 🎨 **Simple UI**: Intuitive user interface for easy file sharing
- 🚀 **Cross-Device**: Share between multiple Android devices
- 📦 **Large Files**: Support for transferring files of any size

## System Requirements

- **Minimum Android SDK**: 21 (Android 5.0)
- **Target Android SDK**: 34 (Android 14)
- **Kotlin**: 1.9.0+
- **Gradle**: 8.1.0+
- **Java**: JDK 11 or later

## Installation & Setup

### Prerequisites

1. **Android Studio** 2023.1.1 or later
2. **Android SDK** API Level 34
3. **JDK 11+**

### Quick Start

#### 1. Clone Repository

```bash
git clone https://github.com/shayandebnathgdn123-coder/airdrop-android.git
cd airdrop-android
```

#### 2. Open in Android Studio

1. Launch Android Studio
2. **File → Open**
3. Select `airdrop-android` directory
4. Wait for Gradle sync

#### 3. Build Project

```bash
# Clean and build
./gradlew clean build

# On Windows
gradlew.bat clean build
```

#### 4. Install & Run

**Option A: Via Android Studio**
- Connect device or start emulator
- Click **Run → Run 'app'** (Shift + F10)
- Select target device
- Click **OK**

**Option B: Via Command Line**

```bash
# Build debug APK
./gradlew assembleDebug

# Install on device
./gradlew installDebug

# Run app
./gradlew runDebug
```

### Build Variants

```bash
# Debug build
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk

# Release build
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

## Testing

```bash
# Unit tests
./gradlew test

# Instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest
```

## Usage

### Transfer Files Between Devices

1. Install app on two Android devices
2. Ensure both on same WiFi network
3. Open AirDrop on both devices
4. Devices auto-discover each other
5. Select files to share
6. Tap recipient device
7. Transfer begins automatically

## Permissions Required

- **Network**: INTERNET, CHANGE_NETWORK_STATE, ACCESS_NETWORK_STATE
- **WiFi**: CHANGE_WIFI_STATE, ACCESS_WIFI_STATE, NEARBY_WIFI_DEVICES
- **Bluetooth**: BLUETOOTH, BLUETOOTH_ADMIN, BLUETOOTH_SCAN, BLUETOOTH_CONNECT
- **Storage**: READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, MANAGE_EXTERNAL_STORAGE
- **Location**: ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION

## Project Structure

```
airdrop-android/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/com/airdrop/android/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── ui/theme/
│   │   │   ├── res/
│   │   │   │   ├── values/
│   │   │   │   ├── xml/
│   │   │   │   └── mipmap/
│   │   │   └── AndroidManifest.xml
│   │   ├── test/
│   │   └── androidTest/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## Package Information

- **Package Name**: `com.airdrop.android`
- **App Version**: 1.0.0
- **Version Code**: 1
- **Min SDK**: 21
- **Target SDK**: 34
- **Compile SDK**: 34

## Dependencies

### Core Libraries
- `androidx.core:core-ktx:1.12.0`
- `androidx.appcompat:appcompat:1.6.1`
- `androidx.lifecycle:lifecycle-runtime-ktx:2.6.2`

### Jetpack Compose
- `androidx.compose.ui:ui:1.5.4`
- `androidx.compose.material3:material3:1.1.2`
- `androidx.activity:activity-compose:1.8.0`

### P2P & Networking
- `com.google.android.gms:play-services-nearby:18.3.0`
- `com.squareup.okhttp3:okhttp:4.11.0`
- `com.google.code.gson:gson:2.10.1`

### Async Programming
- `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3`
- `org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3`

### Utilities
- `androidx.documentfile:documentfile:1.0.1`
- `com.jakewharton.timber:timber:5.0.1`

See `app/build.gradle.kts` for complete dependency list.

## Troubleshooting

### Gradle Sync Fails
```bash
./gradlew clean
./gradlew build
```

### ANDROID_HOME Not Set
```bash
export ANDROID_HOME=/path/to/android/sdk
# Windows: setx ANDROID_HOME "C:\path\to\android\sdk"
```

### Devices Not Discovering Each Other
- Verify both devices on same WiFi
- Enable Bluetooth on both devices
- Grant all required permissions
- Restart the app

### Permission Errors
- Grant permissions in device Settings
- Tap "Allow" when prompted by app
- Ensure Android 6.0+ runtime permissions granted

## Development

### Adding Features

1. Create Kotlin files in `app/src/main/kotlin/com/airdrop/android/`
2. Update AndroidManifest.xml if needed
3. Add tests in `app/src/test/` or `app/src/androidTest/`
4. Update dependencies in `app/build.gradle.kts`

### Code Style
- Follow Kotlin official style guide
- Use meaningful variable/function names
- Add comments for complex logic

## License

MIT License - See LICENSE file for details

## Contributing

1. Fork repository
2. Create feature branch: `git checkout -b feature/YourFeature`
3. Commit changes: `git commit -m 'Add YourFeature'`
4. Push to branch: `git push origin feature/YourFeature`
5. Open Pull Request

## Support

For issues or questions:
- Open GitHub Issue
- Contact: shayandebnathgdn123-coder@github.com

## Changelog

### v1.0.0 (Initial Release)
- Complete project setup
- Gradle build configuration
- Android manifest with permissions
- Jetpack Compose UI framework
- P2P file sharing foundation
- Support for Android 5.0+

---

**Made with ❤️ by the AirDrop Team**
