# 사용할 기본 이미지를 지정합니다.
FROM eclipse-temurin:11


# 작업 디렉토리를 설정합니다.
WORKDIR /u/myapp

# 빌드된 JAR 파일을 현재 디렉토리로 복사합니다.
COPY target/application-0.0.1-SNAPSHOT.jar ./

# 환경 변수 TZ를 설정합니다.
ENV TZ=Asia/Seoul

# Spring Boot 애플리케이션을 실행하는 명령을 정의합니다.
CMD java -jar -Dspring.profiles.active=dev application-0.0.1-SNAPSHOT.jar
