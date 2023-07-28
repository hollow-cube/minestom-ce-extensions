plugins {
    `java-library`

    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version("1.3.0")
}

group = "net.hollowcube.minestom"
version = System.getenv("TAG_VERSION") ?: "dev"
description = "Extensions for minestom-ce, added externally as a library"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    compileOnly("dev.hollowcube:minestom-ce:a981bd78ff")
    testImplementation("dev.hollowcube:minestom-ce:a981bd78ff")
    implementation("com.github.Minestom:DependencyGetter:v1.0.1")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("ch.qos.logback:logback-classic:1.4.5")
}

java {
    withSourcesJar()
    withJavadocJar()

    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    withType<Zip> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    test {
        useJUnitPlatform()
    }

    nexusPublishing {
        useStaging.set(true)
        this.packageGroup.set("dev.hollowcube")

        repositories.sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

            if (System.getenv("SONATYPE_USERNAME") != null) {
                username.set(System.getenv("SONATYPE_USERNAME"))
                password.set(System.getenv("SONATYPE_PASSWORD"))
            }
        }
    }

    publishing.publications.create<MavenPublication>("maven") {
        groupId = "dev.hollowcube"
        artifactId = "minestom-ce-extensions"
        version = project.version.toString()

        from(project.components["java"])

        pom {
            name.set("minestom-ce-extensions")
            description.set("Extensions for minestom-ce, added externally as a library")
            url.set("https://github.com/hollow-cube/minestom-ce-extensions")

            licenses {
                license {
                    name.set("Apache 2.0")
                    url.set("https://github.com/hollow-cube/minestom-ce-extensions/blob/main/LICENSE")
                }
            }

            developers {
                developer {
                    id.set("Minestom Contributors")
                }
            }

            issueManagement {
                system.set("GitHub")
                url.set("https://github.com/hollow-cube/minestom-ce-extensions/issues")
            }

            scm {
                connection.set("scm:git:git://github.com/hollow-cube/minestom-ce-extensions.git")
                developerConnection.set("scm:git:git@github.com:hollow-cube/minestom-ce-extensions.git")
                url.set("https://github.com/hollow-cube/minestom-ce-extensions")
                tag.set("HEAD")
            }

            ciManagement {
                system.set("Github Actions")
                url.set("https://github.com/hollow-cube/minestom-ce-extensions/actions")
            }
        }
    }

    signing {
        isRequired = System.getenv("CI") != null

        val privateKey = System.getenv("GPG_PRIVATE_KEY")
        val keyPassphrase = System.getenv()["GPG_PASSPHRASE"]
        useInMemoryPgpKeys(privateKey, keyPassphrase)

        sign(publishing.publications)
    }
}
