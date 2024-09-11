group = "com.spotride"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}

plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	id ("checkstyle")
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	//checkstyle
	checkstyle("com.puppycrawl.tools:checkstyle:8.42")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
