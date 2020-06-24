# Offer MicroService
*Author* : *Sibendra Pratap Singh ; spsingh23021991@gmail.com*

### Project Directory
#### offers-ms-apis 
This is a spring boot based maven project. It contains below APIs:

1. ***GET /offers***  - This API fetches all the available offers.

**Response Body:**
	
| Attribute | M/O | Data Type | Remarks |
|--|--|--|--|
| name | M | string | name of the product
| description | M | string | Short Description of product
| amount | M | double | Amount of product in GBP
| expiryDate | M | Date | Expiry date of offer
| createdOn | M | Date | Offer creation timestamp
| expired | M | boolean | If offer is expired

2. ***POST offers/create*** - This API create a new offer.

**Request Body -** 

| Attribute | M/O |  Data Type | Remarks
|--|--|--|--|
| name | M | string | Name of the product
| description | M | string | Short Description of product
| amount | M | double | Amount of product in GBP
| expiryDate | O | Date | If not provided defaults to Today's 
Date + 3 months (Time default to : 23:59:59)

**Response Body:**

| Attribute | M/O | Data Type | Remarks |
|--|--|--|--|
| name | M | string | name of the product
| description | M | string | Short Description of product
| amount | M | double | Amount of product in GBP
| expiryDate | M | Date | Expiry date of offer
| createdOn | M | Date | Offer creation timestamp
| expired | M | boolean | If offer is expired

3. ***DELETE /offers/{offerId}/cancel -*** This API expire the offer instantly .

**Response Body:**
	
| Attribute | M/O | Data Type | Remarks |
|--|--|--|--|
| name | M | string | name of the product
| description | M | string | Short Description of product
| amount | M | double | Amount of product in GBP
| expiryDate | M | Date | Expiry date of offer
| createdOn | M | Date | Offer creation timestamp
| expired | M | boolean | If offer is expired

**Build Application:**
go to /offers-ms-apis 
run ***mvn clean install***

**Start Application**
 1. Go to /offers-ms-apis 
 2. Run mvnw spring-boot:run (window machine)
 3. Access APIs on http://localhost:8091/ via REST Client e.g. Postman
 
 
### Dependencies/Prerequisites required to run the application:
 1.  Java 1.8+ should be installed.
 2.  Maven must be installed.
 3.  Add execution permission to start-up script file(if required).
 4. Corporate proxy should be taken care of


