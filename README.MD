<div align="center">
</br>
<img src="https://github.com/binayshaw7777/Knowtify/assets/62587060/1cea48ea-8669-4c98-8b3c-88755bac4fde" width="200" height="200" />
</div>

# NoteByKMP - A Compose Multiplatform TaskManager App
This project is a powerful and versatile TaskManager app built using Compose, Navigation, Jetpack ViewModel, and Room. It allows you to create a Task based on category and DateTime with Bookmark, complete and edit a Task across desktops (macOS), Android, and iOS.


<p align="center">
  <img alt="API" src="https://img.shields.io/badge/Api%2021+-50f270?logo=android&logoColor=black&style=for-the-badge"/></a>

  <a href="https://kotlinlang.org">
      <img src="https://img.shields.io/badge/Kotlin-1.9.23-blue.svg?style=for-the-badge&logo=kotlin"/>
  </a>

  <a href="https://github.com/binayshaw7777/Knowtify/stargazers">
      <img src="https://img.shields.io/github/stars/binayshaw7777/Knowtify?color=ffff00&style=for-the-badge"/>
  </a>

  <a href="https://hits.sh/github.com/binayshaw7777/Knowtify/">
      <img alt="Hits" src="https://hits.sh/github.com/binayshaw7777/Knowtify.svg?style=for-the-badge&label=Views&extraCount=0&color=ff3f6f"/></a>
  </a>

  <img alt="GitHub code size in bytes" src="https://img.shields.io/github/languages/code-size/binayshaw7777/Knowtify?style=for-the-badge">

  </br>
</p>

## Key Features:
- Multiplatform Support: Enjoy a consistent dictionary experience on various devices, including desktops, Android, and iOS.
- Room Database for Offline Storage: Save and Bookmark Tasks.
- Kotlin date-Time to create a beautiful custom calendar without using any library
- Compose Navigation to handle screens
- Use jetpack library to implement the ViewModel
- Beautiful Compose UI: Create an intuitive and visually appealing user interface with Compose.

## Libraries Used:
- [🔥 Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [💉 Koin](https://insert-koin.io/)
- [📚 Room Database](https://developer.android.com/kotlin/multiplatform/room)
- [📦 SQLite](https://developer.android.com/kotlin/multiplatform/sqlite)
- [🗺 Navigation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html)
- [🏗 ViewModel](https://www.jetbrains.com/help/kotlin-multiplatform-dev/whats-new-compose-eap.html#lifecycle-library)
- [💎 KotlinX Serialization](https://kotlinlang.org/docs/serialization.html)
- [🏃‍♂️ Coroutines](https://discuss.kotlinlang.org/t/coroutines-with-multiplatform-projects/18006)
- [📝 Napier](https://github.com/AAkira/Napier)

## Architecture:
![image](https://github.com/binayshaw7777/Knowtify/assets/62587060/942d977c-151f-49a1-be46-ffade986dfd1)

## Getting Started:

Prerequisites:
- Android Studio with Compose plugins installed or Fleet IDE.
- Basic knowledge of Kotlin, Compose, AndroidX libs like and Room, Navigation and etc.


## Clone the Project:
```Bash
git clone https://github.com/SaeedNoshadi89/NoteByKMP.git
```

- Running the App:
Open the project in your preferred IDE.
Build and run the project for your target platform (Android, Desktop, or iOS) following the specific instructions for your IDE and platform.

## Understanding the Code Structure:
- <b>commonMain</b>: Contains platform-agnostic code, including data models, and core business logic.
- <b>androidMain</b>: Specific code for the Android platform, such as UI components using Compose.
- <b>desktopMain</b>: Specific code for desktop platforms (macOS), likely using Compose for desktop as well.
- <b>iosMain</b>: Specific code for the iOS platform, potentially using Compose for iOS.

## Technologies Used:
- Compose: A cutting-edge UI framework for building modern, declarative UIs.
- Room: An SQLite wrapper for local data persistence with an intuitive API.
- Kotlin: A concise and expressive language for developing Android and other cross-platform applications.
- Koin: A DI library to handle Dependency Injection


Screenshot:
![Banner](https://github.com/binayshaw7777/Knowtify/assets/62587060/b48dddc1-236c-441a-9593-37225aee9331)

Recording:
https://github.com/binayshaw7777/Knowtify/assets/62587060/f8d3ca1d-efdc-4345-90c5-2be1370c920a