plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("androidx.navigation.safeargs")
}

android {
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        minSdk = Config.MIN_SDK_VERSION
        targetSdk = Config.TARGET_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
        javaCompileOptions {
            annotationProcessorOptions {
                // Refer https://developer.android.com/jetpack/androidx/releases/room#compiler-options
                arguments(
                    mapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
        }
        buildConfigField("String","NYNEWS_API_KEY","\"1J9DCbH2mBPH3gnJ4TsbWZ7nDSdvUBDQ\"")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            buildConfigField("String","NYNEWS_API_KEY","\"1J9DCbH2mBPH3gnJ4TsbWZ7nDSdvUBDQ\"")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "NYNEWS_API_KEY", "\"1J9DCbH2mBPH3gnJ4TsbWZ7nDSdvUBDQ\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        android.buildFeatures.viewBinding = true
        android.buildFeatures.dataBinding = true
        buildConfig = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
        animationsDisabled = true
    }
    testBuildType = "debug"
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Legacy.legacy_support)
    implementation(Legacy.legacy_support_core)

    //App Compat, layout, Core
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraint_layout)
    implementation(AndroidX.recyclerView)
    implementation(AndroidX.ktx_core)

    //Material
    implementation(Google.material)

    //Room
    implementation(Room.runtime)
    implementation(Room.ktx)
    kapt(Room.compiler)

    // Activity KTX
    implementation(AndroidX.ktx_activity)
    implementation(AndroidX.ktx_fragment)

    // Lifecycle
    implementation(Lifecycle.lifeCycleLiveData)
    implementation(Lifecycle.viewmodel)
    implementation(Lifecycle.lifeCycleRunTime)
    implementation(Lifecycle.lifeCycleViewModelState)

    // Retrofit
    implementation(Retrofit.main)
    implementation(Retrofit.converterGSON)
    implementation(Retrofit.coroutinesAdapter)

    // OkHTTP
    implementation(OkHttp.main)
    implementation(OkHttp.logging_interceptor)

    // Coroutines
    implementation(Coroutines.core)
    implementation(Coroutines.android)

    //Dagger - Hilt
    implementation(Hilt.android)
    kapt(Hilt.android_compiler)

    //Navigation
    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationKtx)

    //Coil
    implementation(Coil.coil)

    //Swipe Refresh Layout
    implementation(AndroidX.SwipeRefreshLayout)

    //Paging
    implementation(Paging.paging)

    //Loging
    implementation(Timber.timber)

    //Binding
    implementation(ViewBinding.viewbinding)

    //Firebase
    implementation(Analytics.firebase)
    implementation(Analytics.firebase_crashlytics_ktx)
    implementation(Analytics.firebase_analytics_ktx)

    //Test
    testImplementation(Test.junit)
    androidTestImplementation(Test.junit_ext)
    androidTestImplementation(Espresso.core)
}