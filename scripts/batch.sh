#!/usr/bin/env bash

mkdir -p ./docker_results
exec &> "./docker_results/batch_results.txt"

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

docker compose up -d

for i in {0..42}
do
  echo " ------ Test Case: $i ----"
  tc="00$i"
  "$DIR/test.sh" ${tc:(-2):2} false
done
wait
docker compose down
