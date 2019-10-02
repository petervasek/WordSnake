# WordSnake
Funny little java / kafka streams app


1. after running docker-compose go into kafka connect container and create topics:  
kafka-topics --create --zookeeper zookeeper:32181 --replication-factor 1 --partitions 3 --topic raw-sentence  
kafka-topics --create --zookeeper zookeeper:32181 --replication-factor 1 --partitions 3 --topic processed-sentence  

2. start app and generate sentences calling "curl -X GET http://localhost:8080/"
