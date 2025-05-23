object Versions {
    const val JUNIT = "5.10.1"
    const val MOCKITO = "5.16.0"
}

plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.sonarqube") version "6.1.0.5360"
    `java-library`
}

group = "com.iws-manager"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")

    
    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Developer tools
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    
    // Database
    runtimeOnly("org.postgresql:postgresql")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")

    testImplementation("org.junit.jupiter:junit-jupiter:${Versions.JUNIT}")
    testImplementation("org.mockito:mockito-core:${Versions.MOCKITO}")
    testImplementation("org.mockito:mockito-junit-jupiter:${Versions.MOCKITO}")
}

tasks.withType<Test> {
    useJUnitPlatform()

    reports {
        html.required.set(true)
        junitXml.required.set(true) 
    }

    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = false
    }
    outputs.dir("build/test-results")
}

sonar {
    properties {
        // SonarCloud Setup
        property("sonar.projectKey", "Piramide-Informatik_iws-manager-api")
        property("sonar.host.url", "https://sonarcloud.io")

        // Routing Analysis setup
        property("sonar.java.binaries", "build/classes/java/main")
        property("sonar.java.test.binaries", "build/classes/java/test")

        property("sonar.junit.reportPaths", "build/test-results/test")
//        property("sonar.java.coveragePlugin", "junit")
        property("sonar.testExecutionReportPaths", "build/test-results/test")

        property("sonar.sources", "src/main/java")
        property("sonar.tests", "src/test/java")
        
        // Extra configurations
        property("sonar.java.source", "21")
        property("sonar.sourceEncoding", "UTF-8")
//        property("sonar.qualitygate.wait", "true")
        property("sonar.scm.provider", "git")
        
        // Delete duplicity
        property("sonar.gradle.skipCompile", "true")

        // Analysis exclusion
        property("sonar.exclusions", "**/config/**,**/exception/**,**/*Application*")
    }
}

tasks.register("validateDependencies") {
    description = "Validates project dependencies using verification-metadata.xml"
    group = "Verification"
    doLast {
        println("Validating dependencies using verification-metadata.xml")
    }
}

gradle.taskGraph.whenReady {
    if (hasTask(":strictDependencyVerification")) {
        System.setProperty("org.gradle.dependency.verification", "strict")
    }
}