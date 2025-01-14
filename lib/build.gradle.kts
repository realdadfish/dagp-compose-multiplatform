plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.dagp)
}

kotlin {
    jvm("desktop")
    androidTarget()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.material)
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material3)
        }
    }
}

android {
    namespace = "com.example.multiplatform.lib"
    compileSdk = 35
    defaultConfig {
        minSdk = 30
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
