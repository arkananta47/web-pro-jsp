# Kita pakai Tomcat 9 (support javax) tapi dengan Java 21 (versi terbaru)
FROM tomcat:9.0-jdk21

# Hapus aplikasi default
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file JSP/HTML
COPY src/main/webapp/ /usr/local/tomcat/webapps/ROOT/

# Copy hasil compile Java
COPY build/classes/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/

EXPOSE 8080
CMD ["catalina.sh", "run"]
