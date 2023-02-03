#!/bin/bash

declare dc_infra=docker/docker-compose.yml
declare dc_app=docker/docker-compose-app.yml

function build_image_jib() {
    ./mvnw -pl techbuzz-api-boot clean package -DskipTests jib:dockerBuild -Dimage=sivaprasadreddy/techbuzz-api-boot
}

function build_apps_buildpacks() {
    ./mvnw -pl techbuzz-api-boot clean spring-boot:build-image -Dspring-boot.build-image.imageName=sivaprasadreddy/techbuzz-api-boot
}

function build_apps() {
    ./mvnw -pl techbuzz-api-boot spotless:apply
    build_image_jib
}

function start_infra() {
    echo "Starting dependent docker containers...."
    docker-compose --env-file .env --profile local -f "${dc_infra}" up --build --force-recreate -d
    docker-compose --env-file .env --profile local -f "${dc_infra}" logs -f
}

function stop_infra() {
    echo "Stopping dependent docker containers...."
    docker-compose --env-file .env --profile local -f "${dc_infra}" stop
    docker-compose --env-file .env --profile local -f "${dc_infra}" rm -f
}

function restart_infra() {
    stop_infra
    sleep 5
    start_infra
}

function start_app() {
    echo "Starting app...."
    build_apps
    docker-compose --env-file .env --profile local -f "${dc_app}" up --build --force-recreate -d
    docker-compose --env-file .env --profile local -f "${dc_app}" logs -f
}

function stop_app() {
    echo 'Stopping all....'
    docker-compose --env-file .env --profile local -f "${dc_app}" stop
    docker-compose --env-file .env --profile local -f "${dc_app}" rm -f
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
