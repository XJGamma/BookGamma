FROM totran/bndroid:latest

MAINTAINER Jingkai Tang <jingkaitang@gmail.com>

# args
ARG release_flag
ARG travis_tag
ARG keystore_pass
ARG alias_name
ARG alias_pass

ENV RELEASE_FLAG $release_flag
ENV TRAVIS_TAG $travis_tag
ENV KEYSTORE_PASS $keystore_pass
ENV ALIAS_NAME $alias_name
ENV ALIAS_PASS $alias_pass

# attach project
ENV PROJECT /project
RUN mkdir $PROJECT
WORKDIR $PROJECT
ADD . $PROJECT

RUN echo "sdk.dir=$ANDROID_HOME" > local.properties

CMD start-emulator -b "./gradlew assembleDebug test" -a "./gradlew cAT"
