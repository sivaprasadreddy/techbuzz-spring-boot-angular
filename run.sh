#!/bin/bash

declare dc_infra=docker/docker-compose.yml
declare dc_app=docker/docker-compose-app.yml

function build_image_jib() {
    ./mvnw -pl techbuzz clean package -DskipTests jib:dockerBuild -Dimage=sivaprasadreddy/techbuzz-boot-angular
}

function build_apps_buildpacks() {
    ./mvnw -pl techbuzz clean spring-boot:build-image -Dspring-boot.build-image.imageName=sivaprasadreddy/techbuzz-boot-angular
}

function build_apps() {
    ./mvnw -pl techbuzz spotless:apply
    build_apps_buildpacks
    #build_image_jib
}

function start_infra() {
    echo "Starting dependent docker containers...."
    docker-compose -f "${dc_infra}" up --build --force-recreate -d
    docker-compose -f "${dc_infra}" logs -f
}

function stop_infra() {
    echo "Stopping dependent docker containers...."
    docker-compose -f "${dc_infra}" stop
    docker-compose -f "${dc_infra}" rm -f
}

function restart_infra() {
    stop_infra
    sleep 5
    start_infra
}

function start_app() {
    echo "Starting app...."
    build_apps
    docker-compose -f "${dc_infra}" -f "${dc_app}" up --build --force-recreate -d
    docker-compose -f "${dc_infra}" -f "${dc_app}" logs -f
}

function stop_app() {
    echo 'Stopping app....'
    docker-compose -f "${dc_infra}" -f "${dc_app}" stop
    docker-compose -f "${dc_infra}" -f "${dc_app}" rm -f
}

function restart_app() {
    stop_app
    sleep 3
    start_app
}

action="start_app"

if [[ "$#" != "0"  ]]
then
    action=$*
fi

eval "${action}"
