
# TRAVIST-Spark

A big data service for TRAVIST made up of Apache Spark on bitnami Docker


## Roadmap

- Add RDD


## Run Locally

Prefer Powershell for following command

Start the service (in detached mode)

```bash
  docker-compose up -d
```

#### Spark Admin

```http
  http://localhost:8080/
```

Access Spark's shell with Spark installation

```bash
  cd spark-3.2.1-bin-hadoop3.2/bin
  ./spark-shell $spark_master_url
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `spark_master_url` | `string` | **Required**. Your spark master url for spark admin. Example: spark://5bbbc7e372fa:7077 |

Access Spark's shell from inside container

```bash
  docker exec -it $spark_conatiner_id_or_name /bin/bash
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `spark_conatiner_id_or_name` | `string` | **Required**. Your spark master container id or container name. Example: 5bbbc7e372fa or spark-master |


## Docker Compose Reference

| Service Name | Port     | Description                |
| :-------- | :------- | :------------------------- |
| `spark-master` | `8080,7070` | **Required**. Spark container with Spark Admin |
| `spark-worker` | `8081` | **Required**. Spark worker number 1 |
## Acknowledgements

 - [Apache Spark packaged by Bitnami](https://hub.docker.com/r/bitnami/spark/)
 - [Apache Spark packaged by Bitnami Github](https://github.com/bitnami/bitnami-docker-spark)

