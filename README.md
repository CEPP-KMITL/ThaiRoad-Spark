
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
  docker exec -it $spark_conatiner_id /bin/bash
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `spark_conatiner_id` | `string` | **Required**. Your spark master container id. Example: 5bbbc7e372fa |


## Docker Compose Reference

| Service Name | Port     | Description                |
| :-------- | :------- | :------------------------- |
| `spark` | `8080` | **Required**. Spark container with Spark Admin |
| `spark-worker` | `-` | **Required**. Spark worker 1 |
## Acknowledgements

 - [Apache Spark packaged by Bitnami](https://hub.docker.com/r/bitnami/spark/)
 - [Apache SPARK Up and Running FAST with Docker](https://www.youtube.com/watch?v=Zr_FqYKC6Qc&t=203s)


