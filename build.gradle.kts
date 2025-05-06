plugins {
    id("java")
}

group = "dev.lone64.LoneLib"

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://libraries.minecraft.net/")
        maven("https://repo.codemc.io/repository/nms/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
    }

    dependencies {
        compileOnly("com.mojang:authlib:6.0.54")
        compileOnly("com.googlecode.json-simple:json-simple:1.1")

        compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
        compileOnly("com.github.LoneDev6:API-ItemsAdder:3.6.1")

        compileOnly("net.kyori:adventure-api:4.13.0")
        compileOnly("net.kyori:adventure-text-serializer-legacy:4.13.0")

        compileOnly("org.projectlombok:lombok:1.18.32")
        annotationProcessor("org.projectlombok:lombok:1.18.32")
    }
}