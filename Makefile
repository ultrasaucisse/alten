run:
	sudo docker compose -p alten up -d

stop:
	sudo docker compose -p alten down

test:
	sudo docker run -v $$PWD/postman:/etc/newman --network alten -t postman/newman:6.1.3-alpine run back.collection.json