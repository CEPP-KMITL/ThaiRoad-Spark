
# TRAVIST-Spark

A big data service for TRAVIST made up of Apache Spark on bitnami Docker


## Roadmap

- Add RDD


## Run Locally

Prefer Powershell for following command

Start the service (in detached mode)

```bash
  docker-compose up -d --scale spark-worker=$number_of_worker_node
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `number_of_worker_node` | `int` | **Required**. Number of worker that will be in the cluster. |

Spark Admin

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
  docker exec -it $spark_conatiner_id_or_name bash
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `spark_conatiner_id_or_name` | `string` | **Required**. Your spark master container id or container name. Example: 5bbbc7e372fa or spark-master. |

Submit an application to the cluster for processing

```bash
  spark-submit $path_to_file
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `path_to_file` | `string` | **Required**. Absolute path to file which you want to submit. |


## Docker Compose Reference
### Container Specification

| Container Name | Port     | Description                |
| :-------- | :------- | :------------------------- |
| `spark-master` | `8080` | **Required**. Spark master container with Spark admin. |
| `spark-worker` | `-` | **Required**. Spark worker number 1. |
| `spark-livy` | `8998` | Service that enables easy interaction with a Spark cluster over a REST interface. |

### Worker Specification

| Specification Parameter | Value |
| :-------- | :------- |
| `SPARK_WORKER_MEMORY` | `1G` |
| `SPARK_WORKER_CORES` | `1` | 
## Acknowledgements

 - [Apache Spark packaged by Bitnami](https://hub.docker.com/r/bitnami/spark/)
 - [Apache Spark packaged by Bitnami Github](https://github.com/bitnami/bitnami-docker-spark)
 - [Apache SPARK Up and Running FAST with Docker](https://www.youtube.com/watch?v=Zr_FqYKC6Qc)
