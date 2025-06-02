#!/usr/bin/env bash

# 프로젝트의 루트 경로 설정
PROJECT_ROOT="/home/ubuntu/efub5-toy-project"

# 로그 파일 경로 설정
APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

# 현재 시간 기록
TIME_NOW=$(date +%c)

# build/libs 디렉토리에서 가장 최근에 생성된 .jar 파일 찾기 (동적 탐색)
# find 명령을 사용하여 정확한 JAR 파일을 찾습니다.
# CodeDeploy가 파일을 복사한 후 바로 실행되므로, build/libs에 있는 유일한 JAR 파일을 찾습니다.
JAR_FILE=$(find "$PROJECT_ROOT/build/libs" -name "*.jar" | head -n 1)

# JAR 파일이 존재하는지 확인
if [ -z "$JAR_FILE" ]; then
  echo "$TIME_NOW > build/libs 디렉토리에서 JAR 파일을 찾을 수 없습니다. 애플리케이션을 시작할 수 없습니다." >> "$DEPLOY_LOG"
  exit 1 # JAR 파일이 없으면 시작할 수 없으므로 에러 코드와 함께 스크립트 종료
fi

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> "$DEPLOY_LOG"
nohup java -jar "$JAR_FILE" > "$APP_LOG" 2> "$ERROR_LOG" &

# 실행된 프로세스의 PID 확인
# basename을 사용하여 JAR 파일 이름만 추출하여 pgrep에 전달합니다.
JAR_NAME=$(basename "$JAR_FILE")
CURRENT_PID=$(pgrep -f "$JAR_NAME")

if [ -z "$CURRENT_PID" ]; then
  echo "$TIME_NOW > 애플리케이션 시작 실패: PID를 찾을 수 없습니다." >> "$DEPLOY_LOG"
else
  echo "$TIME_NOW > 실행된 프로세스의 아이디는 $CURRENT_PID 입니다." >> "$DEPLOY_LOG"
fi