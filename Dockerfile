FROM amazoncorretto:17.0.3
RUN mkdir "tools"
MAINTAINER  ''
WORKDIR /tools
ADD build/libs/* ./
EXPOSE 8080

CMD java -jar tools*.jar