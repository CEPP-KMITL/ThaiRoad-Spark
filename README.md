# TRAVIST-Spark

A big data service for TRAVIST made up of Apache Spark on bitnami Docker

## Roadmap

- Add Advanced Example of Spark Programs

## Run Locally

Prefer Powershell for following command

Start the service (in detached mode)

```bash
  docker-compose up -d --scale spark-worker=$number_of_worker_node
```

| Parameter               | Type  | Description                                                |
|:------------------------|:------|:-----------------------------------------------------------|
| `number_of_worker_node` | `int` | **Required** Number of worker that will be in the cluster. |

Spark Admin

```http
  http://localhost:8080/
```

Access Spark's shell with Spark installation

```bash
  cd spark-3.2.1-bin-hadoop3.2/bin
  ./spark-shell $spark_master_url
```

| Parameter          | Type     | Description                                                                            |
|:-------------------|:---------|:---------------------------------------------------------------------------------------|
| `spark_master_url` | `string` | **
Required** Your spark master url for spark admin. Example: spark://5bbbc7e372fa:7077 |

Access Spark's shell from inside container

```bash
  docker exec -it $spark_conatiner_id_or_name bash
```

| Parameter                    | Type     | Description                                                                                           |
|:-----------------------------|:---------|:------------------------------------------------------------------------------------------------------|
| `spark_conatiner_id_or_name` | `string` | **
Required** Your spark master container id or container name. Example: 5bbbc7e372fa or spark-master. |

Submit an application to the cluster for processing (Need to perform inside spark master container)

```bash
  spark-submit $path_to_file
```

| Parameter      | Type     | Description                                                  |
|:---------------|:---------|:-------------------------------------------------------------|
| `path_to_file` | `string` | **Required** Absolute path to file which you want to submit. |

## Docker Compose Reference

### Container Specification

| Container Name | Port        | Description                                           |
|:---------------|:------------|:------------------------------------------------------|
| `spark-master` | `8080,7077` | **Required** Spark Master Container with Spark Admin. |
| `spark-worker` | `-`         | **Required** Spark Worker.                            |

![](./doc_resources/TRAVIST_Spark_Architecture.jpeg)

### Worker Specification

| Specification Parameter | Value |
|:------------------------|:------|
| `SPARK_WORKER_MEMORY`   | `1G`  |
| `SPARK_WORKER_CORES`    | `1`   |

## Acknowledgements

- [Spark Application | Setup IntelliJ IDE with SBT](https://www.youtube.com/watch?v=ACp2ioiTwQk&t=442s)
- [Apache Spark packaged by Bitnami](https://hub.docker.com/r/bitnami/spark/)
- [Apache Spark packaged by Bitnami Github](https://github.com/bitnami/bitnami-docker-spark)
- [Apache SPARK Up and Running FAST with Docker](https://www.youtube.com/watch?v=Zr_FqYKC6Qc)
- [Apache Spark local with Docker](https://medium.com/@sarunyouwhangbunyapirat/apache-spark-%E0%B8%84%E0%B8%B7%E0%B8%AD%E0%B8%AD%E0%B8%B0%E0%B9%84%E0%B8%A3-%E0%B8%A7%E0%B8%B4%E0%B8%98%E0%B8%B5%E0%B8%95%E0%B8%B4%E0%B8%94%E0%B8%95%E0%B8%B1%E0%B9%89%E0%B8%87%E0%B9%81%E0%B8%A5%E0%B8%B0%E0%B8%97%E0%B8%94%E0%B8%A5%E0%B8%AD%E0%B8%87%E0%B8%9A%E0%B8%99-local-with-docker-f40c281bae8e)
- [Apache SPARK Quick Start](https://spark.apache.org/docs/latest/quick-start.html)
- [Spark Submit Command Explained with Examples](https://sparkbyexamples.com/spark/spark-submit-command/)
- [Running Apache Spark Applications](https://docs.cloudera.com/HDPDocuments/HDP3/HDP-3.1.0/running-spark-applications/content/running_sample_spark_2_x_applications.html)
- [Running Scala Spark jobs on Bitnami Docker images](https://www.youtube.com/watch?v=RQFyLfNBrCc)
- [Spark Client Mode vs Cluster Mode Differences](https://www.youtube.com/watch?v=uvup4DIzVZ8&t=30s)
- [Spark Client Mode Vs Cluster Mode](https://www.youtube.com/watch?v=RCyPU7fbxko)
- [Group: Apache Spark](https://mvnrepository.com/artifact/org.apache.spark)
