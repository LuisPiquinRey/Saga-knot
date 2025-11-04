#!/bin/bash
set -e
set -o pipefail

CONNECT_URL="http://localhost:8083/connectors"

echo "🛑 Step 1: Delete connectors..."
curl -s -X DELETE ${CONNECT_URL}/product-connector || true
curl -s -X DELETE ${CONNECT_URL}/product-connector-sink || true

sleep 3

echo "🗑️ Step 2: Delete ALL offset and history topics..."
TOPICS=(
    "connect-offsets"
    "connect-status"
    "connect-configs"
    "schemahistory.product"
    "dbserver1.product_master.product"
)

for t in "${TOPICS[@]}"; do
    docker exec kafka1 kafka-topics --bootstrap-server localhost:19092 --delete --topic $t 2>/dev/null || true
done

echo "⏳ Waiting for topics to be deleted..."
sleep 10

echo "🔄 Step 3: Restart Kafka Connect to clear in-memory state..."
docker restart kconnect

echo "⏳ Waiting for Kafka Connect to be ready..."
sleep 15

until curl -s ${CONNECT_URL} > /dev/null 2>&1; do
    echo "Waiting for Kafka Connect..."
    sleep 2
done

echo "✅ Kafka Connect is ready!"

echo "🚀 Step 4: Creating Source Connector with schema_only_recovery..."
curl -s -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" ${CONNECT_URL} -d '{
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
    "snapshot.mode": "schema_only_recovery",
    "database.allowPublicKeyRetrieval": "true"
  }
}'

sleep 10

echo -e "\n📊 Source Connector Status:"
curl -s ${CONNECT_URL}/product-connector/status | jq

echo -e "\n🧩 Step 5: Creating Sink Connector..."
curl -s -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" ${CONNECT_URL} -d '{
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
    "table.name.format": "product",
    "schema.evolution": "basic",
    "hibernate.dialect": "org.hibernate.dialect.MySQLDialect",
    "database.time_zone": "UTC"
  }
}'

sleep 5

echo -e "\n📊 Final Status:"
echo "Source:"
curl -s ${CONNECT_URL}/product-connector/status | jq
echo -e "\nSink:"
curl -s ${CONNECT_URL}/product-connector-sink/status | jq

echo -e "\n📦 Topics:"
docker exec kafka1 kafka-topics --bootstrap-server localhost:19092 --list