FROM ghcr.io/graalvm/graalvm-community:21
RUN mkdir "tools"
MAINTAINER  'JiaWei'
WORKDIR /tools
ADD build/libs/* ./
EXPOSE 8080

CMD java -jar tools*.jar