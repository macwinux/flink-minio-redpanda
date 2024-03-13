# See here for image contents: https://github.com/microsoft/vscode-dev-containers/tree/v0.177.0/containers/java-8/.devcontainer/base.Dockerfile

FROM mcr.microsoft.com/vscode/devcontainers/java:0-8

# [Option] Install Maven
ARG INSTALL_MAVEN="false"
ARG MAVEN_VERSION=""
# [Option] Install Gradle
ARG INSTALL_GRADLE="false"
ARG GRADLE_VERSION=""
RUN if [ "${INSTALL_MAVEN}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""; fi \
    && if [ "${INSTALL_GRADLE}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle \"${GRADLE_VERSION}\""; fi

# [Option] Install Node.js
ARG INSTALL_NODE="true"
ARG NODE_VERSION="lts/*"
RUN if [ "${INSTALL_NODE}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/share/nvm/nvm.sh && nvm install ${NODE_VERSION} 2>&1"; fi

# [Optional] Uncomment this section to install additional OS packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends <your-package-list-here>

# [Optional] Uncomment this line to install global node packages.
# RUN su vscode -c "source /usr/local/share/nvm/nvm.sh && npm install -g <your-package-here>" 2>&1
USER root
# install flink
RUN wget -qO- https://dlcdn.apache.org/flink/flink-1.17.2/flink-1.17.2-bin-scala_2.12.tgz | tar -xzf -
RUN mv flink-1.17.2 /opt
#ENV PATH "$PATH:/flink-1.17.2/bin"
RUN mkdir /opt/flink
RUN cp -R /opt/flink-1.17.2/* /opt/flink
ADD flink-connector-kafka-1.17.2.jar /opt/flink/lib/flink-connector-kafka-1.17.2.jar
ADD kafka-clients-3.2.3.jar /opt/flink/lib/kafka-clients-3.2.3.jar
RUN mv -v /opt/flink/opt/flink-python*.jar /opt/flink/lib/
RUN mkdir -p /opt/flink/plugins/s3-fs-presto /opt/flink/plugins/s3-fs-hadoop
RUN mv -v /opt/flink/opt/flink-s3-fs-presto-*.jar /opt/flink/plugins/s3-fs-presto
RUN mv -v /opt/flink/opt/flink-s3-fs-hadoop-*.jar /opt/flink/plugins/s3-fs-hadoop
RUN mv -v opt/flink/opt/flink-*.jar /opt/flink/plugins/
ADD flink-conf.yaml /opt/flink/conf/flink-conf.yaml
ADD core-site.xml /opt/flink/conf/core-site.xml
RUN chown -R vscode:vscode /opt/flink/plugins/
RUN chown -R vscode:vscode /opt/flink/lib/
RUN chown -R vscode:vscode /opt/flink/log/
ENV PATH "$PATH:/opt/flink/bin"