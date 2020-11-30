# Spring and Kafka

The following example is a Producer for sending messages through a topic using Kafka and Spring.

## Producer

The producer is developed using Java Spring Boot. We are going to send messages about temperature emulating a Sensor.

The following code sends an **Id** (integer), a **Datetime** (Timestamp) and a Random value as celsius **Degrees**. 

You can locate the java file in spring-kafka/src/main/java/cl/microservices/kafka/

     @SpringBootApplication
     public class KafkaApplication implements CommandLineRunner {

          private static Logger logger = LoggerFactory.getLogger(KafkaApplication.class);

          @Autowired
          private KafkaTemplate<String, Temperature> kafkaTemplate;

          public static void main(String[] args) {
               SpringApplication.run(KafkaApplication.class, args).close();
          }

          @Override
          public void run(String... args) throws Exception {
               Random random = new Random();
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

               for (int i = 0; i < 1000; i++) {
                        int id = i+1;
                        String now = LocalDateTime.now().format(formatter);
                        float degrees = (float) random.nextInt(30);
                        logger.info("Sending DATA to TOPIC <</data>> Message : DATA ID : <<"+i+">>");

                        this.kafkaTemplate.send("data", new Temperature(id, now,degrees));
               }
          }
      }

Producer will send 1000 JSONs through **data** topic. For this, we use **KafkaTemplate<K,V>** to create a Spring Kafka Message Producer 

     
          ....
          @Autowired
          private KafkaTemplate<String, Temperature> kafkaTemplate;
          ....
          ...

The Temperature JSON is defined by the Temperature class (spring-kafka/src/main/java/cl/microservices/data/).

     public class Temperature {
          private int id;
          private String datetime;
          private float degrees;

          public Temperature(int id, String datetime, float degrees) {
               this.id = id;
               this.datetime = datetime;
               this.degrees = degrees;
          }
          public int getId() {
               return id;
          }
          public void setId(int id) {
               this.id = id;
          }
          public String getDatetime() {
              return datetime;
          }
          public void setDatetime(String datetime) {
               this.datetime = datetime;
          }
          public float getDegrees() {
               return degrees;
          }
          public void setDegrees(float degrees) {
               this.degrees = degrees;
          }
     }

The JSON has the following structure.

     {
       id : 1
       datetime : '2020-11-29 16:13:19.216'
       degrees : 33
     }

For sending messages (Temperature JSON) we use:

     ....
     ...
     this.kafkaTemplate.send("data", new Temperature(id, now,degrees));
     ....
     ...

## Configuring the Producer

You can check the configuration of the producer inside the **application.properties** file.

     $ vi spring-kafka/src/main/resources/application.properties

This configuration uses two environment variables **$KAFKA_SERVER** and **$KAFKA_PORT**. You must to configure both environments vars:

     $ export KAFKA_SERVER=localhost
     $ export KAFKA_PORT=9092


Once you have built the jar, you can execute:

     $ java -jar build/libs/kafka-1.0.0.jar 

       .   ____          _            __ _ _
      /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
     ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
      \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
       '  |____| .__|_| |_|_| |_\__, | / / / /
      =========|_|==============|___/=/_/_/_/
      :: Spring Boot ::                (v2.4.0)

     2020-11-29 16:13:15.705  INFO 7312 --- [           main] cl.microservices.kafka.KafkaApplication  : Starting KafkaApplication using Java 1.8.0_151 on 
     .....
     ....
     2020-11-29 20:41:14.670  INFO 11744 --- [           main] cl.microservices.kafka.KafkaApplication  : Sending DATA to TOPIC <</data>> Message : DATA ID : <<1>>
     2020-11-29 20:41:14.670  INFO 11744 --- [           main] cl.microservices.kafka.KafkaApplication  : Sending DATA to TOPIC <</data>> Message : DATA ID : <<2>>
     2020-11-29 20:41:14.671  INFO 11744 --- [           main] cl.microservices.kafka.KafkaApplication  : Sending DATA to TOPIC <</data>> Message : DATA ID : <<3>>
 
