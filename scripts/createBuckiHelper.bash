#! /bin/bash

rm -Rf /tmp/buildingBuckiHelper
git clone https://github.com/r3st/buckiHelper.git /tmp/buildingBuckiHelper

cd /tmp/buildingBuckiHelper
cd vue

npm install
npm run build

cd /tmp/buildingBuckiHelper

mkdir quarkus
cd quarkus

mvn io.quarkus:quarkus-maven-plugin:1.11.1.Final:create \
    -DprojectGroupId="de.bucki" \
    -DprojectArtifactId=buckiHelper \
    -DprojectVersion=0.0.1

cd buckiHelper
./mvnw quarkus:add-extension -Dextensions="smallrye-health"


rm -Rf src/main/java/*
rm -Rf src/test/java/*

cp -R ../../java/* src/main/java/
cp -R ../../static/* src/main/resources/META-INF/resources/
cp -R ../../vue/dist/* src/main/resources/META-INF/resources/

./mvnw package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
docker build -f src/main/docker/Dockerfile.native -t r3st/buckihelper:0.0.1 .


