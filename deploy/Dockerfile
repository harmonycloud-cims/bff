FROM openshift/jboss-webserver31-tomcat8-openshift:1.2

COPY *.war /opt/webserver/webapps/ROOT.war

EXPOSE 80
CMD ["/opt/webserver/bin/catalina.sh","run"]