import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
}

val properties = Properties().apply {
    load(file("local.properties").inputStream())
}

val major = System.getenv("MAJOR_VERSION")?.toIntOrNull() ?: 1
val minor = System.getenv("MINOR_VERSION")?.toIntOrNull() ?: 0
val patch = System.getenv("PATCH_VERSION")?.toIntOrNull() ?: 0

val versionCodeBase = 10000
val versionCode = versionCodeBase + (major * 10000) + (minor * 100) + patch
val versionName = "$major.$minor.$patch"

val baseUrl: String = properties.getProperty("BASE_URL") ?: "default_url"
val loginUrl: String = properties.getProperty("LOGIN_URL") ?: "default_url"
val registerUrl: String = properties.getProperty("REGISTER_URL") ?: "default_url"

android {
    namespace = "com.mobile.giku"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mobile.giku"
        minSdk = 29
        targetSdk = 34
        versionCode = versionCode
        versionName = versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "LOGIN_URL", "\"$loginUrl\"")
            buildConfigField("String", "REGISTER_URL", "\"$registerUrl\"")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "LOGIN_URL", "\"$loginUrl\"")
            buildConfigField("String", "REGISTER_URL", "\"$registerUrl\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.datastore.preferences)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.coil)
}

tasks.register("printVersionName") {
    doLast {
        println(android.defaultConfig.versionName)
    }
}