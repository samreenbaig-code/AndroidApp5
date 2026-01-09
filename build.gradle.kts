plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false

    // ✅ REQUIRED for Kotlin 2.0 + Compose
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false

    id("com.google.devtools.ksp") version "2.0.21-1.0.26" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21" apply false
}
