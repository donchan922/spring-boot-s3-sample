package com.example.demo;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Order(2)
public class DemoUpload implements CommandLineRunner {

    @Override
    public void run(String... args) {

        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

        try {
            // ファイルをS3にアップロードする
            s3.putObject("バケット名", "オブジェクト名", new File("ファイルパス"));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }
}
