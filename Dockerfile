# 도커 이미지의 베이스로 사용할 이미지 선택
FROM openjdk:17-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 그레이들을 통해 JAR 파일을 빌드하여 `/app` 디렉토리로 복사
COPY build/libs/*.jar app.jar

# 애플리케이션 포트 노출 (필요에 따라 수정)
EXPOSE 8080

# 애플리케이션 실행 명령어 설정
CMD ["java", "-jar", "app.jar"]
