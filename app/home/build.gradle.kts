plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    kotlin("plugin.parcelize")
}

android {
    namespace = "com.study.home"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
        compose = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.database)

    implementation(libs.coil.compose)

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

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.room.room.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.firebase.dynamic.links.ktx)
    implementation(libs.foundation)
    implementation(libs.androidx.room.room.ktx2)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.compose.ui.unit)
    kapt(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation("com.google.firebase:firebase-dynamic-links-ktx")

    // Gson
    implementation(libs.gson)

    // Glide
    implementation(libs.glide)
    implementation(libs.okhttp3.integration)
    kapt(libs.hilt.android.compiler)
    //flex box
    implementation (libs.flexbox)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.number.picker)
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-camera2:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    // EventBus
    implementation(libs.eventbus)
    // Project modules
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.material:material:1.6.0")
    //weather
    // Location services
    implementation("com.google.android.gms:play-services-location:21.0.1")
// Retrofit cho API call
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(project(":app:common"))
    implementation(project(":app:resources"))
    implementation(project(":domain:remote"))
    implementation(project(":domain:home"))
    implementation(project(":domain:base"))
    implementation(project(":domain:main"))
    implementation(project(":domain:user"))
    implementation(project(":data:home"))
    implementation(project(":config"))
    implementation(project(":domain:common"))
    implementation(project(":app:core"))
    implementation("com.google.mlkit:text-recognition:16.0.0")
    // Camera
    implementation("androidx.camera:camera-core:1.2.2")
    implementation("androidx.camera:camera-lifecycle:1.2.2")
    implementation("androidx.camera:camera-view:1.2.2")
// Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")

}