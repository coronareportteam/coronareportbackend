FROM openjdk:11-jre

# Arguments passed via Maven scripts
ARG userId
ARG artifact

# Add User
RUN adduser --system --uid ${userId} user
USER user
WORKDIR /home/user

# TODO: We should have a prod profile that is active in prod (after MVP)
ENV JAVA_OPTS="-Dspring.profiles.active=default"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]

# This statement is the last one because it changes frequently
COPY target/${artifact}.jar app.jar
