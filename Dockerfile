FROM tomcat:9.0-jdk17

# Hapus aplikasi default Tomcat agar bersih
RUN rm -rf /usr/local/tomcat/webapps/*

# 1. Copy file JSP dan WEB-INF dari folder src/main/webapp ke folder utama server
COPY src/main/webapp/ /usr/local/tomcat/webapps/ROOT/

# 2. Copy file class Java (Servlets/Models) yang sudah dicompile
# Agar logika Java (Login, Database) tetap berjalan
COPY build/classes/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/

# Buka port 8080
EXPOSE 8080

# Jalankan Tomcat
CMD ["catalina.sh", "run"]
