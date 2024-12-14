#!/usr/bin/env bash

SCENARIO=$1
start_containers=$2


mkdir -p ./docker_results

if [ "$start_containers" = true ] ; then
    docker compose up -d
fi
sleep 5
echo "Wiping Database"
docker exec -i db mysql -uroot -ppassword deliveryservice < scripts/wipe_db.sql
echo "Running Test..."
Docker compose run --name test_client --entrypoint sh client -c "\
	java -jar ./app.jar < commands_${SCENARIO}.txt > drone_delivery_${SCENARIO}_results.txt && \
    diff -s drone_delivery_${SCENARIO}_results.txt drone_delivery_initial_${SCENARIO}_results.txt > diff_results_${SCENARIO}.txt && \
    cat diff_results_${SCENARIO}.txt"
docker cp test_client:/app/drone_delivery_${SCENARIO}_results.txt ./docker_results/
docker cp test_client:/app/diff_results_${SCENARIO}.txt ./docker_results
docker rm test_client > /dev/null

if [ "$start_containers" = true ] ; then
	docker compose down
fi


