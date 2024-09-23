FROM amd64/buildpack-deps:buster-curl as installer

ENV JAVA_OPTS='-Dserver.port=8718 -Dcsp.sentinel.dashboard.server=localhost:8718'

RUN set -x \
    && curl -SL --output /home/sentinel-dashboard.jar https://github.com/chenbihao/couponDemo/raw/refs/heads/main/ch4-Spring%20Cloud%20%E8%BF%9B%E9%98%B6%EF%BC%88Sentinel%20%E6%9C%8D%E5%8A%A1%E5%AE%B9%E9%94%99%EF%BC%89/middleware/sentinel-dashboard/my-nacos-jar/sentinel-dashboard-1.8.8-nacos.jar

FROM openjdk:8-jre-slim

# copy sentinel jar
COPY --from=installer ["/home/sentinel-dashboard.jar", "/home/sentinel-dashboard.jar"]


RUN chmod +x /home/sentinel-dashboard.jar

EXPOSE 8718
EXPOSE 8719

CMD java ${JAVA_OPTS} -jar /home/sentinel-dashboard.jar
