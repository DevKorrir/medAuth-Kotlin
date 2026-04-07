# MedAuth - Counterfeit Medication Verifier

MedAuth is a production-grade, offline-first Android application designed to secure the pharmaceutical supply chain. Built with a premium, medical-grade UI and robust modern architecture, the app allows users to scan medication barcodes, immediately verify product authenticity, and report suspicious counterfeits.

## 🌟 Key Features

*   **Real-time Scanning:** Deeply integrated CameraX paired with Google ML Kit Barcode Scanning for instant product recognition.
*   **Offline-First & Background Sync:** Fully powered by Room Database and WorkManager. Scan histories and counterfeit reports are stored securely offline and silently synced when network connectivity is available.
*   **Security & Privacy:** Leverages Android BiometricPrompt for secure login verification.
*   **Premium Design System:** A meticulously crafted custom Material 3 schema supporting dynamic Dark and Light themes.
*   **Type-Safe Navigation:** Built on the bleeding-edge **Navigation 3** (`NavDisplay`) utilizing reactive `SnapshotStateList` and `@Serializable` routes for zero-overhead, highly modular navigation matrices.

## 🛠 Tech Stack

**Architecture**
*   **Kotlin 2.0+**
*   **Clean Architecture & MVI/MVVM** organized heavily by feature packages.
*   **Hilt** for robust Dependency Injection.
*   **Coroutines & Flows** for asynchronous un-idirectional data streams.

**UI Framework**
*   **Jetpack Compose** full integration.
*   **Navigation 3 (`androidx.navigation3`)** for deeply declarative, state-driven composable graphs.
*   **Material 3** customized design tokens.
*   Animations utilizing `AnimatedVisibility` and Infinite Transitions (shimmer logic).

**Data & Hardware**
*   **Room Database** for local persistence.
*   **WorkManager** for guaranteed background operations.
*   **CameraX** & **ML Kit Barcode Detection**
*   **Retrofit & OkHttp** setup within repository implementations (currently simulated for easy backend hookup).

## 📁 Project Structure

The codebase rigidly adheres to a feature-driven MVVM hierarchy:
```
dev.korryr.medauth/
├── core/
│   ├── designsystem/  # Core Material 3 Colors, Shapes, Typography & Themes
│   ├── ui/            # Reusable agnostic buttons, cards, status badges
│   ├── scanner/       # ML Kit ImageAnalysis analyzers
│   └── workers/       # Background sync operations
├── data/
│   ├── local/         # Room DB, Entities, DAOs
│   └── repository/    # Abstractions for API integration (VerificationRepository)
├── di/                # Hilt Modules (DatabaseModule, AppModule)
├── navigation/        # Navigation 3 Type-safe destinations & AppNavigation graph
└── presentation/
    └── features/
        ├── auth/      # Login & Onboarding (Biometrics integrated)
        ├── history/   # Local scan history & offline UI
        ├── report/    # Suspect counterfeit multi-step reporting flow
        ├── scan/      # Camera overlay logic
        └── verification/ # Animated Success/Error verification overlays
```

## 🚀 Getting Started

### Prerequisites
*   Android Studio Ladybug (or newer)
*   JDK 21
*   Android Device/Emulator running Android 8.0 (API level 24) or higher (Camera required for scanning features).

### Building the Project
1. Clone the repository.
2. Open the project in Android Studio.
3. Allow Gradle to synchronize dependencies.
4. Run the `app` configuration on a physical device for the best camera experience.

## 🔌 API & Backend
The app is currently configured using a **simulated VerificationRepository**. You can immediately connect this application to a live backend by updating the network interface and providing real Retrofit endpoint URLs within the Data module.
