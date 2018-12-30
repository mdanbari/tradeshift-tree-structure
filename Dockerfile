FROM openjdk:8
LABEL maintainer="Mohammad Anbari"
ADD target/tree-structure.jar tree-structure.jar
ENTRYPOINT ["java", "-jar", "tree-structure.jar"]
