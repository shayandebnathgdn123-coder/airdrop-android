# GitHub Actions Setup Guide for APK Downloads

This guide will help you set up GitHub Actions workflows to automatically build and download APKs.

## 📋 Files to Create

Create these three files in your repository:

### 1. `.github/workflows/build-apk.yml`

```yaml
name: Build APK

on:
  push:
    branches: [ main, develop, setup/initial-project ]
  pull_request:
    branches: [ main, develop ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3

      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
          cache: gradle

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew

      - name: Build Debug APK
        run: ./gradlew assembleDebug --stacktrace

      - name: Build Release APK
        run: ./gradlew assembleRelease --stacktrace

      - name: Upload Debug APK
        uses: actions/upload-artifact@v3
        with:
          name: airdrop-debug.apk
          path: app/build/outputs/apk/debug/app-debug.apk
          retention-days: 30

      - name: Upload Release APK
        uses: actions/upload-artifact@v3
        with:
          name: airdrop-release.apk
          path: app/build/outputs/apk/release/app-release.apk
          retention-days: 30
```

### 2. `.github/workflows/lint-test.yml`

```yaml
name: Lint & Test

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main, develop ]

jobs:
  lint:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
          cache: gradle
      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
      - name: Run Lint
        run: ./gradlew lint

  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
          cache: gradle
      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
      - name: Run Unit Tests
        run: ./gradlew test
```

### 3. `.github/workflows/release.yml`

```yaml
name: Release APK

on:
  release:
    types: [published]
  workflow_dispatch:

jobs:
  release:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
          cache: gradle

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew

      - name: Build Release APK
        run: ./gradlew assembleRelease

      - name: Upload Release APK
        uses: softprops/action-gh-release@v1
        with:
          files: app/build/outputs/apk/release/app-release.apk
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

## 🚀 Setup Instructions

### Step 1: Clone and Switch Branch
```bash
git clone https://github.com/shayandebnathgdn123-coder/airdrop-android.git
cd airdrop-android
git checkout setup/initial-project
```

### Step 2: Create Workflow Directory
```bash
mkdir -p .github/workflows
```

### Step 3: Create the Workflow Files

Copy the YAML content from above and create the three files:
- `.github/workflows/build-apk.yml`
- `.github/workflows/lint-test.yml`
- `.github/workflows/release.yml`

### Step 4: Commit and Push
```bash
git add .github/
git commit -m "Add GitHub Actions workflows for automated APK builds and releases"
git push origin setup/initial-project
```

### Step 5: Create Pull Request
1. Go to https://github.com/shayandebnathgdn123-coder/airdrop-android
2. Click "Pull requests" → "New pull request"
3. Set base: `main`, compare: `setup/initial-project`
4. Click "Create pull request"
5. Merge the PR

## 📥 How to Download APKs

### From GitHub Actions (Every Build)
1. Navigate to **Actions** tab
2. Select **Build APK** workflow
3. Click the latest run
4. Scroll to **Artifacts** section
5. Download:
   - `airdrop-debug.apk` - For testing
   - `airdrop-release.apk` - For production

### From Releases (Tagged Versions)
1. Create a release tag:
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```
2. Go to **Releases** tab
3. Select the version
4. Download the APK from release assets

### Manual Build
```bash
# Debug APK
./gradlew assembleDebug
# Location: app/build/outputs/apk/debug/app-debug.apk

# Release APK
./gradlew assembleRelease
# Location: app/build/outputs/apk/release/app-release.apk
```

## 🎯 Workflow Features

### build-apk.yml
- ✅ Builds both Debug and Release APKs
- ✅ Runs on every push to main/develop
- ✅ Manually triggerable via workflow_dispatch
- ✅ Stores APKs for 30 days
- ✅ Can be downloaded from Actions tab

### lint-test.yml
- ✅ Runs lint checks
- ✅ Executes unit tests
- ✅ Validates code quality
- ✅ Runs on PRs

### release.yml
- ✅ Builds release APK when tag is created
- ✅ Automatically uploads to release assets
- ✅ Makes APK available for download on Releases page

## 🔗 Useful Links

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Android Gradle Plugin](https://developer.android.com/studio/releases/gradle-plugin)
- [softprops/action-gh-release](https://github.com/softprops/action-gh-release)

## 📝 Notes

- APKs are retained for 30 days on GitHub Actions
- Debug APK: Includes debugging symbols, not optimized
- Release APK: Optimized for production
- Both APKs are installable on Android devices

---

**Once you complete these steps, your users will be able to download APKs from the Releases page!** 🎉
