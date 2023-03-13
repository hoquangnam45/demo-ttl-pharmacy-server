plugins {
  java
  id("com.diffplug.spotless") version "6.12.0"
  id("org.flywaydb.flyway") version "9.15.1"
  war
}

group = "com.hoquangnam45.ttl.pharmacy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenLocal()
  mavenCentral()
}

dependencies {
  compileOnly("org.projectlombok:lombok:1.18.26")
  implementation("com.ibm.db2:jcc:11.5.8.0")
  compileOnly("org.flywaydb:flyway-core:9.15.1")
  implementation("org.hibernate:hibernate-core:5.6.15.Final")
  implementation("com.google.inject:guice:5.1.0")
  implementation("at.favre.lib:bcrypt:0.10.2")
  annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
  annotationProcessor("org.projectlombok:lombok:1.18.26")
  implementation("org.mapstruct:mapstruct:1.5.3.Final")
  annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")
  implementation("org.apache.commons:commons-lang3:3.12.0")
  implementation("com.sun.xml.ws:jaxws-rt:2.3.6")
  implementation("org.apache.tomcat.embed:tomcat-embed-core:10.0.27")
  implementation("org.apache.tomcat.embed:tomcat-embed-jasper:10.0.27")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

// Disable generation of plain jar file along with far jar of spring
tasks.getByName<Jar>("jar") {
  enabled = false
}

tasks.register("updateGitHooks", Copy::class) {
  from("./scripts/hooks/")
  into("./.git/hooks/")
  include("*")
  fileMode = 0b111111101
}
tasks.withType<JavaCompile> {
  dependsOn("updateGitHooks")
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
  format("xml") {
    target("*.xml", "*.xsd")
    targetExclude("build/", "target/", ".gradle/", "cache/", "bin/")
    eclipseWtp(com.diffplug.spotless.extra.wtp.EclipseWtpFormatterStep.XML).configFile("spotless.xml.prefs")
  }
  format("misc") {
    // define the files to apply `misc` to
    target("*.md", ".gitignore", ".dockerignore", "Dockerfile")
    targetExclude("build/", "target/", ".gradle/", "cache/", "bin/")
    // define the steps to apply to those files
    trimTrailingWhitespace()
    indentWithSpaces(2) // or spaces. Takes an integer argument if you don't like 4
    endWithNewline()
  }
  kotlinGradle {
    target("*.kts")
    targetExclude("build/", "target/", ".gradle/", "cache/", "bin/")
    ktlint().editorConfigOverride(mapOf("indent_size" to "2", "continuation_indent_size" to "2"))
    trimTrailingWhitespace()
    endWithNewline()
  }
  // don't need to set target, it is inferred from java
  java {
    targetExclude("build/", "target/", ".gradle/", "cache/", "bin/")
    toggleOffOn() // Allow to disable spotless for specific parts of code with spotless:off
    removeUnusedImports() // removes any unused imports
    // eclipse formatter
    eclipse().configFile("./eclipse-formatter.xml")
    // fix formatting of type annotations
    formatAnnotations()
  }
}
