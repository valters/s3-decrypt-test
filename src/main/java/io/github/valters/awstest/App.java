package io.github.valters.awstest;

import static java.lang.String.format;
import static java.lang.System.out;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

public class App {

    private static final String BUCKET = "acon2";

    private static final String CONTENT = "my test";

    /** Default is Germany/Frankfurt. */
    public static final Region DEFAULT_REGION = Region.EU_CENTRAL_1;

    final S3Client client = S3Client.builder()
            .credentialsProvider(DefaultCredentialsProvider.create())
            .region(DEFAULT_REGION)
            .build();

    public static void main(final String[] args) {
        new App().run();
    }

    private void run() {

        final var got = retrieve(BUCKET, "encrypted.txt");
        out.println("got: " + got);

        if( !got.equals(CONTENT)) {
            throw new RuntimeException(String.format("Expected '%s' but was '%s'", CONTENT, got));
        }
        else {
            out.print("OK");
        }
    }

    /**
     * Given AWS information, connects to the cloud and retrieves a content of a
     * file stored in the S3 bucket.
     */
    public String retrieve(final String bucketName, final String filePath) {

        out.println(format("region = %s, bucketName = %s, fileName = %s", DEFAULT_REGION, bucketName, filePath));

        try {
            final ResponseBytes<GetObjectResponse> res = client.getObject(GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filePath)
                    .build(),
                    ResponseTransformer.toBytes());

            return res.asUtf8String();
        }
        catch(final Exception e) {
            final String msg = String.format("problem retrieving file [%s] from S3 bucket [%s]", filePath, bucketName);
            out.println(msg + e);
            throw new RuntimeException(msg, e);
        }
    }

}
