buildscript{
    dependencies {
        classpath ("com.google.gms:google-services:4.3.15")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.46" apply false
}