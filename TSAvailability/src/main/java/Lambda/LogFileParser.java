package Lambda;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileParser implements RequestHandler<Map<String, String>, ArrayList<String>> {

    private int convertToSecInt(String hr, String min, String sec, String mil) {
        return ((Integer.parseInt(hr) * 3600000) + (Integer.parseInt(min) * 60000) + (Integer.parseInt(sec) * 1000) + Integer.parseInt(mil));
    }

    public ArrayList<String> handleRequest(Map<String,String> input, Context context) {

        ArrayList<String> logs = new ArrayList<>();
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIARLOHEGPV2SDYI2FZ", "GDdxw0buyGIS6UlGQXfTwYRSQxcfWMJsnNx8nMZZ");
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("us-east-1")
                .build();

        InputStream is = s3Client.getObject("cloud-hw-3", "logfile.txt").getObjectContent();

        Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name());

        String text = scanner.useDelimiter("\\A").next();

        String[] lines  = text.split("\n");

        String md5 = null;
        try {
            md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String timestamp = input.get("timestamp");
        String dt = input.get("dt");
        int time = convertToSecInt(timestamp.substring(0,2), timestamp.substring(3,5), timestamp.substring(6,8), timestamp.substring(9,12));
        int left = 0;
        int right = lines.length - 1;

        int mid;
        while (left <= right) {
            mid = (left + right) / 2;
            if (time == convertToSecInt(lines[mid].substring(0,2), lines[mid].substring(3,5), lines[mid].substring(6,8), lines[mid].substring(9,12))) {
                int diff = convertToSecInt(dt.substring(0,2), dt.substring(3,5), dt.substring(6,8), dt.substring(9,12));
                int lowerRange = time - diff;
                int upperRange = time + diff;
                Pattern r = Pattern.compile("([a-c][e-g][0-3]|[A-Z][5-9][f-w]){5,15}");
                while (mid >= 0) {
                    String line = lines[mid--];
                    String[] lineArr = line.split(" ");
                    if (convertToSecInt(lineArr[0].substring(0,2), lineArr[0].substring(3,5), lineArr[0].substring(6,8), lineArr[0].substring(9,12)) <= lowerRange) {
                        break;
                    }
                    Matcher m = r.matcher(lineArr[lineArr.length-1]);
                    if(m.find()) {
                        logs.add(line);
                    }
                }

                mid = (left + right) / 2;

                while (mid < lines.length) {
                    String line = lines[mid++];
                    String[] lineArr = line.split(" ");
                    if (convertToSecInt(lineArr[0].substring(0,2), lineArr[0].substring(3,5), lineArr[0].substring(6,8), lineArr[0].substring(9,12)) >= upperRange) {
                        break;
                    }
                    Matcher m = r.matcher(lineArr[lineArr.length-1]);
                    if(m.find()) {
                        logs.add(line);
                    }
                }
                return logs;
            }
            mid = (left + right) / 2;
            if (time < convertToSecInt(lines[mid].substring(0,2), lines[mid].substring(3,5), lines[mid].substring(6,8), lines[mid].substring(9,12))) {
                right = mid - 1;
            }
            else {
                left = mid + 1;
            }
        }
        logs.add(md5);
        return logs;
    }
}