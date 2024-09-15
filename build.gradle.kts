group = "com.spotride"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}

plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.github.spotbugs") version "6.0.22"
	id("checkstyle")
}

java {
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	//database
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.liquibase:liquibase-core:4.29.2")
	implementation("org.postgresql:postgresql")

	//test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.h2database:h2:2.3.232")

	//metrics
	implementation("io.micrometer:micrometer-registry-prometheus:1.12.0")

	//checkstyle
	checkstyle("com.puppycrawl.tools:checkstyle:8.42")

	//openapi
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	//spotbugs plugin dependencies
	spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.13.0")
	spotbugsPlugins("com.mebigfatguy.sb-contrib:sb-contrib:7.6.4")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.spotbugsMain {
	reports.create("html") {
		required.set(true)
		outputLocation.set(file("${project.projectDir}/build/reports/spotbugs.html"))
		setStylesheet("fancy-hist.xsl")
	}
}
