plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    id("kotlin-kapt")
    kotlin("plugin.parcelize")
}

android {
    namespace = "com.osprey.config"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            buildConfigField("String", "BASE_URL", "\"https://api-chipi.starnestsolution.com/\"")
            buildConfigField("String", "BASE_BACKUP_URL", "\"https://api-aichat.starnestsolution.com/\"")
            buildConfigField("String", "BASE_AUTH", "\"https://performentmarketing.ddnsgeek.com/\"")
            buildConfigField("String", "KID", "\"36ccfe00-78fc-4cab-9c5b-5460b0c78513\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "BASE_AUTH", "\"https://performentmarketing.ddnsgeek.com/\"")
            buildConfigField("String", "BASE_URL", "\"https://api-chipi.starnestsolution.com/\"")
            buildConfigField("String", "BASE_BACKUP_URL", "\"https://api-aichat.starnestsolution.com/\"")
            buildConfigField("String", "KID", "\"36ccfe00-78fc-4cab-9c5b-5460b0c78513\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
// Androidx Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.constraintlayout)

    // Material
    implementation(libs.material)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.extensions)

//     Starnest Core

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Hilt
    implementation(libs.hilt.android)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Gson
    implementation(libs.gson)

    // Glide
    implementation(libs.glide)
    implementation(libs.okhttp3.integration)
    kapt(libs.compiler)
}