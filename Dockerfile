FROM amazoncorretto:21.0.2
RUN mkdir "tools"
MAINTAINER  'JiaWei'
WORKDIR /tools
ADD build/libs/* ./
EXPOSE 8080

CMD java -jar tools*.jar