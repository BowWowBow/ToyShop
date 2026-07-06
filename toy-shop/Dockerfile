# =========================
# 1단계 : Gradle 빌드
# =========================
FROM gradle:8.14-jdk17 AS builder

WORKDIR /app

COPY . .

RUN gradle clean bootJar --no-daemon

# =========================
# 2단계 : 실행
# =========================
FROM eclipse-temurin:17-jre

WORKDIR /app

# 업로드 폴더 생성
RUN mkdir -p /app/upload

# 빌드된 jar 복사
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]