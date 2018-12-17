BUCKET=acon2

aws s3 cp encrypted.txt s3://${BUCKET}/ --sse aws:kms --sse-kms-key-id 1036ab84-450d-4152-b300-1c92074eb2d9

