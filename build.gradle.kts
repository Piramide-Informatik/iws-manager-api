plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
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
	
	// Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// Developer tools
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	
	// Database
	runtimeOnly("org.postgresql:postgresql")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
}
