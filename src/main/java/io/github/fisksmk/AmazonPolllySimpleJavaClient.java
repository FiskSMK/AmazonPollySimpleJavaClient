package io.github.fisksmk;

import com.amazonaws.auth.*;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by Adrian Smykowski on 21.01.2017.
 */
public class AmazonPolllySimpleJavaClient {

    private static final String accessKey = "";
    private static final String secretKey = "";
    private static final String region = "eu-west-1";

    public static void synthesize(String voiceName, String text, String filepath) throws IOException {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest();
        synthesizeSpeechRequest.setOutputFormat(OutputFormat.Mp3);
        synthesizeSpeechRequest.setVoiceId(voiceName);
        synthesizeSpeechRequest.setText(text);

        AmazonPollyClientBuilder clientBuilder = AmazonPollyClientBuilder.standard();
        clientBuilder.setCredentials(new AWSStaticCredentialsProvider(awsCredentials));
        clientBuilder.setRegion(region);
        AmazonPolly client = clientBuilder.build();
        SynthesizeSpeechResult synthesizeSpeechResult = client.synthesizeSpeech(synthesizeSpeechRequest);
        InputStream audioStream = synthesizeSpeechResult.getAudioStream();

        OutputStream outputStream = new FileOutputStream(new File(filepath));
        IOUtils.copy(audioStream, outputStream);
        audioStream.close();
        outputStream.close();
    }

    public static void main(String[] args) throws IOException {
        AmazonPolllySimpleJavaClient.synthesize(
                "Justin", "Hi, I am Justin. Amazon Polly voice.", "/temp/aws-test.mp3");
    }
}
