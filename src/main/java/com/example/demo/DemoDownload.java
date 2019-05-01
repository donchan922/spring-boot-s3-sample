package com.example.demo;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@Order(1)
public class DemoDownload implements CommandLineRunner {

    @Override
    public void run(String... args) {

        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

        try {
            // S3のオブジェクトを取得する
            S3Object o = s3.getObject("バケット名", "オブジェクト名");
            S3ObjectInputStream s3is = o.getObjectContent();

            // ダウンロード先のファイルパスを指定する
            FileOutputStream fos = new FileOutputStream(new File("ファイルパス"));

            // S3のオブジェクトを1024byteずつ読み込み、ダウンロード先のファイルに書き込んでいく
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }

            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }
}
