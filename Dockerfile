# Pull base image.
FROM openjdk:8
MAINTAINER Ivam Luz <ivam.santos@gmail.com>

# Install.
RUN \
  apt-get update &&\ 
  apt-get install -y curl git

#Install maven
ENV MVN_VERSION 3.5.0
RUN \
   wget http://www.us.apache.org/dist/maven/maven-3/${MVN_VERSION}/binaries/apache-maven-${MVN_VERSION}-bin.zip -P /tmp/ &&\
   mkdir -p /usr/local/apache-maven &&\
   unzip /tmp/apache-maven-${MVN_VERSION}-bin.zip -d /usr/local/apache-maven/ &&\
   rm -rf /tmp/apache-maven-${MVN_VERSION}-bin.zip 

ENV M2_HOME /usr/local/apache-maven/apache-maven-${MVN_VERSION}
ENV M2 $M2_HOME/bin

#Install appengine java sdk
ENV GAE_SDK_VERSION 1.9.53
RUN \
   wget http://storage.googleapis.com/appengine-sdks/featured/appengine-java-sdk-${GAE_SDK_VERSION}.zip -P /tmp/ &&\
   mkdir -p /usr/local/google/appengine-java-sdk &&\
   unzip /tmp/appengine-java-sdk-${GAE_SDK_VERSION}.zip -d /usr/local/google/appengine-java-sdks/ &&\
   rm -rf /tmp/appengine-java-sdk-${GAE_SDK_VERSION}.zip

ENV PATH ${M2}:/usr/local/google/appengine-java-sdks/appengine-java-sdk-${GAE_SDK_VERSION}/bin:${PATH}

CMD ["bash"]