plugins {
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    //id "org.asciidoctor.convert" version "1.5.9.2";
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("java")
}

val asciidoctorExtensions: Configuration by configurations.creating

group = "io.github.key-del-jeeinho"
version = "0.0.1-SNAPSHOT"

configurations {
    compileOnly {
        extendsFrom(configurations["testImplementation"])
    }
}

repositories {
    mavenCentral()
}
// 의존성 관리
dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-jdbc:2.5.6")
    implementation ("org.springframework.boot:spring-boot-starter-security:2.5.6")
    implementation ("org.springframework.boot:spring-boot-starter-validation:2.5.6")
    implementation ("org.springframework.boot:spring-boot-starter-web:2.5.6")
    implementation ("io.github.key-del-jeeinho:golabab-v2-rosetta-lib:1.0.0-RELEASE") //Golabab Rosetta Library
    compileOnly ("org.projectlombok:lombok:1.18.22")
    runtimeOnly ("mysql:mysql-connector-java:8.0.25")
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor:2.5.6")
    annotationProcessor ("org.projectlombok:lombok:1.18.22")
    testImplementation ("org.springframework.boot:spring-boot-starter-test:2.5.6")
    testImplementation ("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.5.RELEASE")
    testImplementation ("org.springframework.security:spring-security-test:5.5.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    asciidoctorExtensions("org.springframework.restdocs:spring-restdocs-asciidoctor")
}


tasks {
    val snippetsDir = file("$buildDir/generated-snippets")

    clean {
        delete("src/main/resources/static/docs")
    }

    test {
        systemProperty("org.springframework.restdocs.outputDir", snippetsDir)
        outputs.dir(snippetsDir)
    }

    build {
        dependsOn("copyDocument")
    }

    asciidoctor {
        dependsOn(test)

        attributes(
            mapOf("snippets" to snippetsDir)
        )
        inputs.dir(snippetsDir)

        doFirst {
            delete("src/main/resources/static/docs")
        }
    }

    register<Copy>("copyDocument") {
        dependsOn(asciidoctor)

        destinationDir = file(".")
        from(asciidoctor.get().outputDir) {
            into("src/main/resources/static/docs")
        }
    }

    bootJar {
        dependsOn(asciidoctor)

        from(asciidoctor.get().outputDir) {
            into("BOOT-INF/classes/static/docs")
        }
    }
}