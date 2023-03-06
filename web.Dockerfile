#Version 1.0
#ARG baseImageName
#ARG baseImageVersion
#FROM ${baseImageName}:${baseImageVersion}
#FROM cxms-docker.pkg.coding.net/cxms/wh/springbootimage:1.0
FROM maven:3.5.3
# JAR运行目录
ENV WORK_PATH /home/app
# 日志数据目录
ENV WORK_DATA /home/log

RUN mkdir $WORK_PATH
RUN mkdir $WORK_DATA

COPY . .

COPY /etc/.m2/ ./.m2/

ARG JAVA_OPTS='-server -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$WORK_DATA/logs/gc_%p.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=30m -XX:+HeapDumpOnOutOfMemoryError'
RUN echo $(ls) & mvn install -Dmaven.test.skip=true -Dmaven.repo.local=./.m2 --settings ./settings.xml

RUN mv town-web/target/app.jar $WORK_PATH/app.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

EXPOSE 8080
#
CMD java ${JAVA_OPTS} -jar $WORK_PATH/app.jar