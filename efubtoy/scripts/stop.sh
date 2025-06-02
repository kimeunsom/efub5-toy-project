#!/usr/bin/env bash

# 프로젝트의 루트 경로 설정
PROJECT_ROOT="/home/ubuntu/efub5-toy-project"

# 실제 JAR 파일의 정확한 경로와 이름 설정
# JAR_FILE 변수를 build/libs 내의 실제 JAR 파일 경로로 직접 지정합니다.
JAR_FILE="$PROJECT_ROOT/build/libs/efubtoy-0.0.1-SNAPSHOT.jar"

# 로그 파일 경로 설정
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

# 현재 시간 기록
TIME_NOW=$(date +%c)

# 현재 구동 중인 애플리케이션 PID 확인
# 실제 JAR 파일 이름으로 실행 중인 프로세스를 찾습니다.
CURRENT_PID=$(pgrep -f "$JAR_FILE") # "$JAR_FILE"을 따옴표로 감싸서 공백이나 특수 문자 문제 방지

# 프로세스가 켜져 있으면 종료
if [ -z "$CURRENT_PID" ]; then # "$CURRENT_PID"를 따옴표로 감싸서 변수가 비어있을 때 발생하는 오류 방지
  echo "$TIME_NOW > 현재 실행 중인 애플리케이션이 없습니다." >> "$DEPLOY_LOG"
else
  echo "$TIME_NOW > 실행 중인 $CURRENT_PID 애플리케이션 종료" >> "$DEPLOY_LOG"
  kill -15 "$CURRENT_PID" # "$CURRENT_PID"를 따옴표로 감싸서 정확한 PID에 시그널 전송
  # 프로세스가 종료될 때까지 잠시 기다립니다. (선택 사항이지만 안정성 향상)
  sleep 5
  # 그래도 종료되지 않으면 강제 종료 (선택 사항)
  if kill -0 "$CURRENT_PID" > /dev/null 2>&1; then
    echo "$TIME_NOW > $CURRENT_PID 애플리케이션이 종료되지 않아 강제 종료합니다." >> "$DEPLOY_LOG"
    kill -9 "$CURRENT_PID"
  fi
fi