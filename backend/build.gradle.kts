@file:OptIn(ExperimentalPathApi::class)

import com.google.protobuf.gradle.*
import kotlin.io.path.*
import kotlin.io.path.Path
import kotlin.io.path.deleteRecursively

val protobufVersion = "3.25.1"
val grpcVersion = "1.65.0"
val grpcKotlinVersion = "1.4.1"
val grpcSpringBootVersion = "5.1.5"
val protoSrcDir = "../proto"
val protoDstDir = "src/main/proto"

plugins {
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	id("idea")
	id("com.google.protobuf") version "0.9.4"
}

group = "com.engjoy"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("io.github.lognet:grpc-spring-boot-starter:$grpcSpringBootVersion")
	implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
	implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

tasks.register("cleanProtoDir") {
	doLast {
		Path(protoDstDir).deleteRecursively()
	}
}

tasks.register<Copy>("copyProtoFiles") {
	from(protoSrcDir)
	into(protoDstDir)
	dependsOn("cleanProtoDir")
}

tasks.named("generateProto") {
	dependsOn("copyProtoFiles")
}

tasks.named("processResources") {
	dependsOn("copyProtoFiles")
}
