# flink-minio-redpanda
Visual studio configuration that allow to work with a complete environment.

There are some things that you need to do before run a flink program.

1.  You need to run this VSC devcontainer as a normal one.
2.  Then, in the terminal, you need to start the cluster:

    `sudo su`

    `./../opt/flink/bin/start-cluster.sh`

3.  In the minio dashboard (localhost:9001), enter with user `minio` and pass `minio123`.
4.  Create a bucket named `example`.
5.  Upload the example-rows.txt into it.
6.  In the redpanda console (localhost:8080), go to topics and create a new one named `flink_output`.
7.  Enter into the flink_output topic.
8.  Build the flink job with `mvn clean package`.
9.  Now, run the job in the terminal: `flink run target/FlinkDataPipeline.jar` 
10. Go to the flink dashboard (localhost:8081) and you will se the stream pipeline running.
11. In the redpanda topic dashboard you will see the data from the .txt file.