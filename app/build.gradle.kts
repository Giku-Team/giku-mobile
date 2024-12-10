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
val authPrefs: String = properties.getProperty("AUTH_PREFERENCES") ?: "default_url"
val loginStatus: String = properties.getProperty("LOGIN_STATUS") ?: "default_url"
val token: String = properties.getProperty("TOKEN") ?: "default_url"
val forgotPassword: String = properties.getProperty("FORGOT_PASSWORD_URL") ?: "default_url"
val validateResetCode: String = properties.getProperty("VALIDATE_RESET_CODE_URL") ?: "default_url"
val resetPassword: String = properties.getProperty("RESET_PASSWORD_URL") ?: "default_url"

val isSigningConfigured = System.getenv("KEYSTORE_FILE") != null &&
        System.getenv("KEYSTORE_PASSWORD") != null &&
        System.getenv("KEY_ALIAS") != null &&
        System.getenv("KEY_PASSWORD") != null

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

    if (isSigningConfigured) {
        signingConfigs {
            create("release") {
                storeFile = file(System.getenv("KEYSTORE_FILE"))
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "LOGIN_URL", "\"$loginUrl\"")
            buildConfigField("String", "REGISTER_URL", "\"$registerUrl\"")
            buildConfigField("String", "AUTH_PREFERENCES", "\"$authPrefs\"")
            buildConfigField("String", "LOGIN_STATUS", "\"$loginStatus\"")
            buildConfigField("String", "TOKEN", "\"$token\"")
            buildConfigField("String", "FORGOT_PASSWORD_URL", "\"$forgotPassword\"")
            buildConfigField("String", "VALIDATE_RESET_CODE_URL", "\"$validateResetCode\"")
            buildConfigField("String", "RESET_PASSWORD_URL", "\"$resetPassword\"")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            if (isSigningConfigured) {
                signingConfig = signingConfigs.getByName("release")
            }
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "LOGIN_URL", "\"$loginUrl\"")
            buildConfigField("String", "REGISTER_URL", "\"$registerUrl\"")
            buildConfigField("String", "AUTH_PREFERENCES", "\"$authPrefs\"")
            buildConfigField("String", "LOGIN_STATUS", "\"$loginStatus\"")
            buildConfigField("String", "TOKEN", "\"$token\"")
            buildConfigField("String", "FORGOT_PASSWORD_URL", "\"$forgotPassword\"")
            buildConfigField("String", "VALIDATE_RESET_CODE_URL", "\"$validateResetCode\"")
            buildConfigField("String", "RESET_PASSWORD_URL", "\"$resetPassword\"")
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
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.coil)
    implementation(libs.barcode.scanning)
    implementation(libs.androidx.viewpager2)
    implementation(libs.view)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)
    implementation(libs.androidx.camera.camera2)
}

tasks.register("printVersionName") {
    doLast {
        println(android.defaultConfig.versionName)
    }
}