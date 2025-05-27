#!/usr/bin/env bash
PROJECT_ROOT="/home/ubuntu/efub5-toy-project"
# 실제 JAR 파일 경로를 직접 지정
JAR_PATH="$PROJECT_ROOT/build/libs/efubtoy-0.0.1-SNAPSHOT.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# 이전 빌드/복사 로직은 삭제 (CodeDeploy가 이미 build/libs에 JAR을 배포했으므로)
# 또는 JAR_PATH가 프로젝트 루트로 오도록 cp 명령을 변경할 수도 있습니다.
# 하지만 가장 간단한 것은 JAR_PATH를 실제 위치로 설정하는 것.

# jar 파일 실행
echo "$TIME_NOW > $JAR_PATH 파일 실행" >> $DEPLOY_LOG
nohup java -jar $JAR_PATH > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_PATH)
echo "$TIME_NOW > 실행된 프로세스의 아이디는 $CURRENT_PID 입니다." >> $DEPLOY_LOG