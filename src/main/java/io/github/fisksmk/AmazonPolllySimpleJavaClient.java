package io.github.fisksmk;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;

import java.io.*;

/**
 * Created by Adrian Smykowski on 21.01.2017.
 */
public class AmazonPolllySimpleJavaClient {

    private static final String accessKey = "";
    private static final String secretKey = "";

    public static AWSCredentials awsCredentials;

    public static void main(String[] args) throws IOException {
        awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest();
        synthesizeSpeechRequest.setOutputFormat(OutputFormat.Mp3);
        synthesizeSpeechRequest.setVoiceId("Maja");
        synthesizeSpeechRequest.setText("Cześc jestem Amazon Polly");

        AmazonPollyClient client = new AmazonPollyClient(awsCredentials);
        client.setEndpoint("polly.eu-west-1.amazonaws.com");
        SynthesizeSpeechResult synthesizeSpeechResult = client.synthesizeSpeech(synthesizeSpeechRequest);
        InputStream audioStream = synthesizeSpeechResult.getAudioStream();

        OutputStream outputStream = new FileOutputStream(new File("/temp/aws-test.mp3"));
        System.out.println(synthesizeSpeechResult.toString());

        byte[] buffer = new byte[2 * 1024];
        int readBytes;

        while ((readBytes = audioStream.read(buffer)) > 0) {
            // In the example we are only printing the bytes counter,
            // In real-life scenario we would operate on the buffer
            System.out.println(" received bytes: " + readBytes);
            outputStream.write(buffer, 0, readBytes);
        }
    }
}
