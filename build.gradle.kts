import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.internal.os.OperatingSystem


plugins {
    kotlin("jvm") version "1.5.31"
}

group = "com.dpeter99"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/kotlin-dev")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    //maven("https://jitpack.io")
    maven("https://raw.githubusercontent.com/kotlin-graphics/mary/master")
    maven("https://maven.scijava.org/content/repositories/public/")
}

// Update this version to match the latest KTX release:
val ktxVersion = "1.10.0-b4"
val gdxVersion = "1.10.0"
val imguiVersion = "876ba2648e37cb03464e806c1d1ad2ddb28c7bfc"//"master-SNAPSHOT"//"ca150b4905132dfb66e2a12cdc279e087e56d0a5"

dependencies {
    testImplementation(kotlin("test"));

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    //api("com.github.kotlin-graphics.imgui:bgfx:$imguiVersion")
    //implementation("com.github.kotlin-graphics:imgui:$imguiVersion")
    //implementation("kotlin.graphics:imgui-core:latest")
    //implementation("kotlin.graphics:imgui-glfw:latest")
    //implementation("com.github.kotlin-graphics.imgui:core:42ce6d8e")

    implementation("kotlin.graphics:glm:0.9.9.1-3+27")


    implementation("com.badlogicgames.gdx:gdx-tools:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion")


    api(group = "io.github.libktx", name = "ktx-app", version = ktxVersion)

    api("com.google.guava","guava", "21.0")


}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}