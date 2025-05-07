@file:Suppress("SpellCheckingInspection")

import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("java")
    id("com.github.johnrengelman.shadow").version("7.1.2")
}

group = "dev.lone64.LoneLib"
version = "1.0.1"

dependencies {
    implementation(project(":modules:bukkit:common"))
    implementation("com.iridium:IridiumColorAPI:1.0.9")
    compileOnly("org.spigotmc:spigot-api:1.21.5-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveFileName.set("LoneLib-${version}.jar")
    }

    withType<ProcessResources> {
        from("src/main/resources") {
            include("plugin.yml")
            filter<ReplaceTokens>("tokens" to mapOf("version" to project.version))
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }
}