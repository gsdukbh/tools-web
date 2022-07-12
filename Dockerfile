FROM amazoncorretto:17.0.3
RUN mkdir "tools"
MAINTAINER  ''
WORKDIR /tools
ADD build/libs/tools-web-0.0.1-releases.jar ./
EXPOSE 8080

CMD java -jar tools-web-0.0.1-releases.jar