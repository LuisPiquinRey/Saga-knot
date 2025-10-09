#!/bin/bash

curl -X DELETE localhost:8083/connectors/product-connector
curl -X DELETE localhost:8083/connectors/product-connector-sink

sleep 2

curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '{
  "name": "product-connector",
  "config": {
    "connector.class": "io.debezium.connector.mysql.MySqlConnector",
    "tasks.max": "1",
    "database.hostname": "mysql-master",
    "database.port": "3306",
    "database.user": "root",
    "database.password": "LuisPiquinRey",
    "database.server.id": "184054",
    "topic.prefix": "dbserver1",
    "database.include.list": "product_master",
    "table.include.list": "product_master.product",
    "schema.history.internal.kafka.bootstrap.servers": "kafka1:19092",
    "schema.history.internal.kafka.topic": "schemahistory.product",
    "include.schema.changes": "true",
    "snapshot.mode": "initial"
  }
}'

sleep 5

echo "=== Source Connector Status ==="
curl -s localhost:8083/connectors/product-connector/status | jq

curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '{
  "name": "product-connector-sink",
  "config": {
    "connector.class": "io.debezium.connector.jdbc.JdbcSinkConnector",
    "tasks.max": "1",
    "connection.url": "jdbc:mysql://mysql-slave:3306/product_slave?allowPublicKeyRetrieval=true&useSSL=false",
    "connection.username": "root",
    "connection.password": "LuisPiquinRey",
    "topics": "dbserver1.product_master.product",
    "insert.mode": "upsert",
    "delete.enabled": "true",
    "primary.key.mode": "record_key",
    "primary.key.fields": "id_product",
    "table.name.format": "${topic}",
    "schema.evolution": "basic",
    "hibernate.dialect": "org.hibernate.dialect.MySQLDialect",
    "database.time_zone": "UTC"
  }
}'

sleep 5

echo -e "\n=== Source Connector Status ==="
curl -s localhost:8083/connectors/product-connector/status | jq

echo -e "\n=== Sink Connector Status ==="
curl -s localhost:8083/connectors/product-connector-sink/status | jq

echo -e "\n=== Kafka Topics ==="
docker exec kafka1 kafka-topics --bootstrap-server localhost:19092 --list

echo -e "\n=== Messages (primeros 5) ==="
docker exec kafka1 kafka-console-consumer \
  --bootstrap-server localhost:19092 \
  --topic dbserver1.product_master.product \
  --from-beginning \
  --max-messages 5 \
  --timeout-ms 10000 2>/dev/null || echo "Not messages"

