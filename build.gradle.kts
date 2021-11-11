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
    maven("https://jitpack.io")
    maven("https://raw.githubusercontent.com/kotlin-graphics/mary/master")
    maven("https://maven.scijava.org/content/repositories/public/")
}

// Update this version to match the latest KTX release:
val ktxVersion = "1.10.0-b4"
val gdxVersion = "1.10.0"
val imguiVersion = "1.84.1.3"
val shapedrawerVersion = "2.5.0"

dependencies {
    testImplementation(kotlin("test"));

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")

    //api("com.github.kotlin-graphics.imgui:bgfx:$imguiVersion")
    //implementation("com.github.kotlin-graphics:imgui:$imguiVersion")
    //implementation("kotlin.graphics:imgui-core:latest")
    //implementation("kotlin.graphics:imgui-glfw:latest")
    //implementation("com.github.kotlin-graphics.imgui:core:42ce6d8e")

    implementation("io.github.spair:imgui-java-binding:$imguiVersion")
    implementation("io.github.spair:imgui-java-lwjgl3:$imguiVersion")
    implementation("io.github.spair:imgui-java-natives-linux:$imguiVersion")
    implementation("io.github.spair:imgui-java-natives-macos:$imguiVersion")
    implementation("io.github.spair:imgui-java-natives-windows:$imguiVersion")

    implementation("space.earlygrey:shapedrawer:$shapedrawerVersion")

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
    kotlinOptions.jvmTarget = "16"
}