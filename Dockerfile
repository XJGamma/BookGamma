FROM totran/bndroid:latest

MAINTAINER Jingkai Tang <jingkaitang@gmail.com>

# attach project
ENV PROJECT /project
RUN mkdir $PROJECT
WORKDIR $PROJECT
ADD . $PROJECT

RUN echo "sdk.dir=$ANDROID_HOME" > local.properties

CMD start-emulator "./gradlew assembleDebug test cAT"
