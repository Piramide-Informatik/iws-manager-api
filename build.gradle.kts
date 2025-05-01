plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.sonarqube") version "4.4.1.3373"
    jacoco
	`java-library`
}

group = "com.iws-manager"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
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
	
	// Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// Developer tools
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	
	// Database
	runtimeOnly("org.postgresql:postgresql")

	// Testing
	// testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
	// testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
	testImplementation("org.mockito:mockito-core:5.16.0")
	testImplementation("org.mockito:mockito-junit-jupiter:5.16.0")
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
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
        
        // Extra configurations
        property("sonar.java.source", "21")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.qualitygate.wait", "true")
        property("sonar.scm.provider", "git")
        
        // Delete duplicity
        property("sonar.gradle.skipCompile", "true")

        // Analysis exclussion
        property("sonar.exclusions", """
            **/config/**,
            **/exception/**,
        """.trimIndent())
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true) 
        html.required.set(true)
    }
	classDirectories.setFrom(files(classDirectories.files.map {
        fileTree(it).apply {
            exclude(
                "**/config/**",
                "**/exception/**",
            )
        }
    }))
	executionData.setFrom(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))
}

tasks.register("validateDependencies") {
    doLast {
        println("Validando dependencias usando verification-metadata.xml")
    }
}

gradle.taskGraph.whenReady {
    if (hasTask(":strictDependencyVerification")) {
        System.setProperty("org.gradle.dependency.verification", "strict")
    }
}