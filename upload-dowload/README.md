# s3-file-storage-example #
we can either use “aws-java-sdk” from maven

    <dependency>
        <groupId>io.awspring.cloud</groupId>
        <artifactId>spring-cloud-aws-core</artifactId>
        <version>2.3.1</version>
    </dependency>
    
or we could use spring-cloud-starter-aws.

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-aws</artifactId>
    </dependency>

since I’m using spring cloud I have used the “spring-cloud-starter-aws” for this sample app.
