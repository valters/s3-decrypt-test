# s3-decrypt-test
AWS SDK v2 S3 retrieve test with decryption.

To run, you will need to go into AWS KMS and note down an encryption key.
Then edit upload.sh script, to specify a bucket, and your key id.
Run the script, verify the file is uploaded to S3 and is encrypted with aws:kms SSE.

Then edit App.java and change bucket constant there as well.

You can run from IDE or execute `mvn clean package exec:java` to run.

Observe the program fails with "Caused by: java.io.UncheckedIOException: Cannot encode string." error.
In pom.xml, you can change version to 2.1.3. Run again, it does not fail.
There was some kind of regression introduced in 2.1.3 -> 2.1.4.
