# Use an official Tomcat runtime as a parent image
FROM tomcat:9.0-jdk17

# Remove the default Tomcat applications to keep it clean
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your project files to the Tomcat webapps folder.
# IMPORTANT: Changing context to ROOT means your app loads at the main URL.
# If your JSP files are in a specific folder (like 'WebContent' or 'src'), change the "." below.
COPY . /usr/local/tomcat/webapps/ROOT

# Expose port 8080 (Tomcat default)
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
