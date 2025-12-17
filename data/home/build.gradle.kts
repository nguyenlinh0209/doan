plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.parcelize")
}

android {
    namespace = "com.study.data.home"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Module dependencies
    implementation(project(":app:resources"))
    implementation(project(":domain:home"))
    implementation(project(":data:base"))
    implementation(project(":config"))
    implementation(project(":app:common"))
    implementation(project(":data:common"))
    implementation(project(":app:core"))
    implementation(project(":data:remote"))

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // ===== ROOM (use KSP) =====
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.room.compiler)

    // ===== HILT (use KAPT) =====
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)

    // ===== Paging =====
    implementation(libs.androidx.paging.runtime)
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")

    // Firebase (BOM)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.database)

    // ===== Eventbus =====
    implementation(libs.eventbus)

    // ===== Testing =====
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}