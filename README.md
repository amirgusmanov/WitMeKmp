# This is a Kotlin Multiplatform project targeting Android, iOS.

WitBook is a multiplatform-platform mobile application built with Kotlin Multiplatform, designed to track your reading session efficiently.

## 🚀 Download the App

[![Google Play](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/details?id=kz.witme.project)
[![App Store](https://img.shields.io/badge/App_Store-0D96F6?style=for-the-badge&logo=app-store&logoColor=white)](https://apps.apple.com/kz/app/witbook/id6742798201)

## 🛠️ Technologies Used

- **Kotlin Multiplatform**: For sharing business logic across platforms
- **Compose Multiplatform**: For Android & iOS UI
- **Kotlin Coroutines**: For asynchronous programming
- **KotlinX Serialization**: For data serialization
- **Ktor & Ktorfit**: For networking
- **Voyager**: For navigation
- **Coil**: For image loading
- **Koin**: For dependency injection

## 🏗️ Architecture

The project follows a clean architecture approach with the following structure:

```
├── core/
│   ├── common/       # Shared business logic
│   ├── common-ui/    # Shared UI components
│   ├── data/         # Data layer implementation
│   └── navigation/   # Navigation logic
├── feature/          # Feature modules
│   ├── auth/         # Authentication
│   ├── dashboard/    # Main dashboard
│   ├── profile/      # User profile
│   └── ...          # Other features
├── service/          # Service modules
│   ├── auth/         # Authentication service
│   ├── profile/      # Profile service
│   └── ...          # Other services
├── composeApp/       # App entry point
└── iosApp/          # iOS-specific code
```
