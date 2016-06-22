FROM gfx2015/android:latest

MAINTAINER Jingkai Tang <jingkaitang@gmail.com>

# add sys img to docker
ENV ANDROID_TARGET android-23
ENV ANDROID_ABI armeabi-v7a
ENV ANDROID_SYS_IMAGE sys-img-$ANDROID_ABI-$ANDROID_TARGET
RUN echo y | android update sdk --no-ui --all --filter "$ANDROID_SYS_IMAGE"

# create avd
RUN echo no | android create avd --force -n test -t "$ANDROID_TARGET" --abi "$ANDROID_ABI"

# attach project
ENV PROJECT /project
RUN mkdir $PROJECT
WORKDIR $PROJECT
ADD . $PROJECT

RUN echo "sdk.dir=$ANDROID_HOME" > local.properties

ADD android-wait-for-emulator /usr/local/bin/
ADD start-emulator /usr/local/bin/

CMD start-emulator "./gradlew assembleDebug test cAT"
