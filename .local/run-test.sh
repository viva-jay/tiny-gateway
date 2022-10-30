echo "--------------------------------------------------------------------------------------"
echo "Start Load testing http://localhost:8086/"
echo "--------------------------------------------------------------------------------------"
docker-compose run --rm k6 run \
--no-connection-reuse \
--insecure-skip-tls-verify \
--no-teardown \
--no-thresholds \
--no-setup \
--no-usage-report \
--quiet \
--verbose \
--linger \
-e REQ_DURATION_THRESHOLDS=300 \
/scripts/script.js