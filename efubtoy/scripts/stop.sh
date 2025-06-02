#!/usr/bin/env bash

# 프로젝트의 루트 경로 설정
PROJECT_ROOT="/home/ubuntu/efub5-toy-project"

# 로그 파일 경로 설정
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

# 현재 시간 기록
TIME_NOW=$(date +%c)

# build/libs 디렉토리에서 가장 최근에 생성된 .jar 파일 찾기 (동적 탐색)
# find 명령을 사용하여 정확한 JAR 파일을 찾습니다.
# CodeDeploy가 파일을 복사한 후 바로 실행되므로, build/libs에 있는 유일한 JAR 파일을 찾습니다.
JAR_FILE=$(find "$PROJECT_ROOT/build/libs" -name "*.jar" | head -n 1)

# JAR 파일이 존재하는지 확인
if [ -z "$JAR_FILE" ]; then
  echo "$TIME_NOW > build/libs 디렉토리에서 JAR 파일을 찾을 수 없습니다. 종료할 애플리케이션이 없습니다." >> "$DEPLOY_LOG"
  exit 0 # JAR 파일이 없으면 종료할 프로세스도 없으므로 스크립트 종료
fi

# 현재 구동 중인 애플리케이션 PID 확인
# 찾은 JAR 파일의 이름으로 실행 중인 프로세스를 찾습니다.
# basename을 사용하여 JAR 파일 이름만 추출하여 pgrep에 전달합니다.
JAR_NAME=$(basename "$JAR_FILE")
CURRENT_PID=$(pgrep -f "$JAR_NAME")

# 프로세스가 켜져 있으면 종료
if [ -z "$CURRENT_PID" ]; then
  echo "$TIME_NOW > 현재 실행 중인 애플리케이션이 없습니다." >> "$DEPLOY_LOG"
else
  echo "$TIME_NOW > 실행 중인 $CURRENT_PID 애플리케이션 종료" >> "$DEPLOY_LOG"
  kill -15 "$CURRENT_PID" # SIGTERM 시그널로 정상 종료 시도
  sleep 5 # 프로세스가 종료될 때까지 잠시 기다림

  # 그래도 종료되지 않으면 강제 종료 (SIGKILL)
  if kill -0 "$CURRENT_PID" > /dev/null 2>&1; then
    echo "$TIME_NOW > $CURRENT_PID 애플리케이션이 종료되지 않아 강제 종료합니다." >> "$DEPLOY_LOG"
    kill -9 "$CURRENT_PID"
  fi
fi