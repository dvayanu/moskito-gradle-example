FROM adoptopenjdk/openjdk11:alpine-slim
LABEL maintainer Alexander Kauerz "alexander.kauerz@nitrobox.de"
ENV workdir /opt/nitrobox/jbin
ENV bindir build/libs
ENV app service.payment-1.0.0.jar
ENV port 80
EXPOSE $port
HEALTHCHECK --interval=30s --timeout=3s CMD curl http://localhost:$port/status
WORKDIR $workdir
COPY $bindir/$app $workdir
COPY deployment/application.properties $workdir
ENTRYPOINT exec java $JAVA_OPTS -jar $app
