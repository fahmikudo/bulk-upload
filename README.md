# Bulk Upload File CSV

## High-Level Architecture
![image](https://github.com/fahmikudo/bulk-upload/assets/20161826/21819cc2-a5d6-484b-82e2-790f9f9adf46)

## Explanation

### Bulk Upload Service
Bulk upload service serves as a service that will receive requests from users/clients to upload files in CSV form, which will then be forwarded to the message broker (Kafka) and then received by the consumer for processing.

### Bulk Upload Consumer
serves as a service that will receive messages from message brokers based on predetermined topics. Then it will carry out the process of creating data into the database for any data received from the message broker itself.

### How To Run
`docker-compose up -d`

### API Documentation
![image](https://github.com/fahmikudo/bulk-upload/assets/20161826/e16b3d14-0069-4d52-ba0f-6293fa46f3bb)
