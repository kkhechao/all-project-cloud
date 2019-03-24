FROM registry.cn-hangzhou.aliyuncs.com/prod-env/jdk1.8:v1
MAINTAINER ZQKHYL
COPY microservice-wallet.jar /opt/apps/app.jar
COPY start.sh /opt/script/start.sh
RUN chmod +x /opt/script/start.sh
ENV minx 256m
ENV max 512m
ENV envs prod
ENV port 7005
EXPOSE 7005
CMD ["/opt/script/start.sh"]
