import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<BootJar>("bootJar") {
    enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

dependencies{
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.2.4")
    runtimeOnly("org.postgresql:postgresql")
}