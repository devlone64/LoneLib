plugins {
    id("java")
    id("maven-publish")
}

group = "dev.lone64.LoneLib.common"
version = "1.1.8"

dependencies {
    compileOnly("com.iridium:IridiumColorAPI:1.0.9")
    compileOnly("org.spigotmc:spigot-api:1.21.5-R0.1-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "dev.lone64"
            artifactId = "LoneLib"
            from(components["java"])
        }
    }

    repositories {
        maven("https://repo.repsy.io/mvn/lone64/everything/") {
            credentials {
                username = properties["mavenUsername"] as String
                password = properties["mavenPassword"] as String
            }
        }
    }
}