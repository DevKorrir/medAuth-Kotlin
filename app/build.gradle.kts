plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")

    id("com.google.devtools.ksp")

}

android {
    namespace = "dev.korryr.medauth"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.korryr.medauth"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose.android)
    implementation(libs.androidx.navigation.runtime.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //extend icons
    implementation("androidx.compose.material:material-icons-core:1.7.8")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    //coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    // DataStore
    implementation ("androidx.datastore:datastore-preferences:1.1.7")
    implementation("androidx.datastore:datastore-preferences-core:1.1.7")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.57")
    ksp("com.google.dagger:hilt-compiler:2.57")

    // Hilt Navigation Compose (recommended for Compose + Hilt)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Local Database (Room) - for offline storage
    implementation("androidx.room:room-runtime:2.7.2")
    implementation("androidx.room:room-ktx:2.7.2")
    //kapt("androidx.room:room-compiler:2.6.1")

    // Work Manager (for background tasks)
    implementation("androidx.work:work-runtime-ktx:2.10.3")
    implementation("androidx.hilt:hilt-work:1.2.0")

    // Security & Encryption
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // Animation
    implementation("androidx.compose.animation:animation:1.8.3")
    implementation("androidx.compose.animation:animation-graphics:1.8.3")

    // Camera & QR Code Scanning
    implementation("androidx.camera:camera-camera2:1.4.2")
    implementation("androidx.camera:camera-lifecycle:1.4.2")
    implementation("androidx.camera:camera-view:1.4.2")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")

    // Biometric Authentication
    implementation("androidx.biometric:biometric:1.1.0")

    // Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.37.3")

    // Network & API
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.squareup.okhttp3:okhttp:5.1.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")

    // JSON Parsing
    implementation("com.google.code.gson:gson:2.13.1")

    // Date & Time
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

}
