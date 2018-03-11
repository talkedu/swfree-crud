#!/bin/bash

tag=$(git symbolic-ref HEAD 2>/dev/null | awk -F '/' '{print $NF}')
sh mvnw clean package docker:build -DdockerImageTags=$tag
docker run -p 8080:8080 --rm -ti swfree-crud:$tag