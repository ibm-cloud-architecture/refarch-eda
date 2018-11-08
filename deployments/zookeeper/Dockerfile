FROM debian:jessie

ENV ZOOKEEPER_VERSION=3.4.13 \
ZK_HOME=/opt/zookeeper \
ZK_USER=zookeeper

RUN apt-get update && apt-get install -y openjdk-7-jre-headless wget netcat-openbsd
ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64

RUN wget -q http://mirror.vorboss.net/apache/zookeeper/zookeeper-${ZOOKEEPER_VERSION}/zookeeper-${ZOOKEEPER_VERSION}.tar.gz && \
wget -q https://www.apache.org/dist/zookeeper/KEYS && \
wget -q https://www.apache.org/dist/zookeeper/zookeeper-${ZOOKEEPER_VERSION}/zookeeper-${ZOOKEEPER_VERSION}.tar.gz.asc && \
wget -q https://www.apache.org/dist/zookeeper/zookeeper-${ZOOKEEPER_VERSION}/zookeeper-${ZOOKEEPER_VERSION}.tar.gz.md5

#Verify download
RUN md5sum -c zookeeper-${ZOOKEEPER_VERSION}.tar.gz.md5 && \
gpg --import KEYS && \
gpg --verify zookeeper-${ZOOKEEPER_VERSION}.tar.gz.asc

#Install
RUN tar -xzf zookeeper-${ZOOKEEPER_VERSION}.tar.gz -C /opt \
  && mv /opt/zookeeper-${ZOOKEEPER_VERSION} $ZK_HOME
COPY zoo.cfg $ZK_HOME/conf/zoo.cfg
COPY zookeeper-ready $ZK_HOME/bin/zookeeper-ready
RUN  useradd $ZK_USER \
   && mkdir -p /var/lib/zookeeper/data /var/lib/zookeeper/log \
   && chown -R "$ZK_USER:$ZK_USER" $ZK_HOME /var/lib/zookeeper/data /var/lib/zookeeper/log \
   && ln -s /opt/zookeeper/bin/* /usr/bin


#Clean
RUN rm -rf /opt/zookeeper/CHANGES.txt \
    /opt/zookeeper/README.txt \
    /opt/zookeeper/NOTICE.txt \
    /opt/zookeeper/CHANGES.txt \
    /opt/zookeeper/README_packaging.txt \
    /opt/zookeeper/build.xml \
    /opt/zookeeper/contrib \
    /opt/zookeeper/dist-maven \
    /opt/zookeeper/docs \
    /opt/zookeeper/ivy.xml \
    /opt/zookeeper/ivysettings.xml \
    /opt/zookeeper/recipes \
    /opt/zookeeper/src \
    /opt/zookeeper/zookeeper-${ZOOKEEPER_VERSION}.jar.asc \
    /opt/zookeeper/zookeeper-${ZOOKEEPER_VERSION}.jar.md5 \
    /opt/zookeeper/zookeeper-${ZOOKEEPER_VERSION}.jar.sha1




EXPOSE 2181 2888 3888
WORKDIR $ZK_HOME

CMD $ZK_HOME/bin/zkServer.sh start-foreground
