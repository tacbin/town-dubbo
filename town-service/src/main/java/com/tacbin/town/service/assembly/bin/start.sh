#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..

DEPLOY_DIR=`pwd`
LOGS_DIR=$DEPLOY_DIR/logs
if [ ! -d "$LOGS_DIR" ]; then
  mkdir $LOGS_DIR
fi
log_file=$LOGS_DIR/log."$(date +%Y%m%d%H%M%S)"
echo "Log: $log_file "
#exec 1>>$log_file
echo "$log_file"
echo DEPLOY_DIR=$DEPLOY_DIR
echo BIN_DIR=$BIN_DIR
echo LOGS_DIR=$LOGS_DIR

JAR_COUNT=`find *.jar|wc -l`
JAR_FILE=`find *.jar`
if [ "$JAR_COUNT" -gt 1 ]; then
    echo -e "More then one jar file!"
    exit 1
fi
if [ "$JAR_FILE" = "" ]; then
    echo -e "Can not find jar file!"
    exit 1
fi

PIDS=`ps aux | grep java | grep "$JAR_FILE" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The App already started!"
    echo "PID: $PIDS"
    exit 1
fi

# JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "

JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
    JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
fi

JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
    JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
fi

JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server -Xmx2g -Xms2g -Xmn256m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

STDOUT_FILE=$LOGS_DIR/stdout.log

pwd
echo -e "Starting ...\c"
# nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -jar $JAR_FILE > $STDOUT_FILE 2>&1 &
#
nohup java  -jar $JAR_FILE > $STDOUT_FILE 2>&1

echo "wait the server starting..."

COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
    COUNT=`ps aux | grep java | grep "$JAR_FILE" | awk '{print $2}' | wc -l`
    if [ $COUNT -gt 0 ]; then
        break
    fi
done

echo "Start OK!"
PIDS=`ps aux | grep java | grep "$JAR_FILE" | awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"

#while  true
#do
#    echo -e ".\c"
#    sleep 10000
#
#done