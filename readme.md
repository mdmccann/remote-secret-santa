# Remote Secret Santa

Remote Secret Santa is a Java backend service for managing [Secret Santa](https://en.wikipedia.org/wiki/Secret_Santa) parties. The service supports creating parties, adding party members to the party, and finally running the Secret Santa to exchange participants details with another random party member.

## Installation 

Follow the steps below

```bash
git clone https://github.com/mmccann94/remote-secret-santa.git

cd remote-secret-santa\target

java -jar remote-secret-santa-0.0.01-SNAPSHOT.jar
```

## Example Usage

Here are some example REST calls to demo the application with:

#### Create a Secret Santa party
Request:
```
POST /api/secretSantas HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
	"maxGiftCost": 12.00,
	"giftDispatchDeadline": "2030-12-23",
	"maxPartyMembers": 12
}
```

Response:
```
{
    "maxGiftCost": 12,
    "giftDispatchDeadline": "2030-12-23",
    "maxPartyMembers": 12,
    "status": "CREATED",
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/secretSantas/f7b3c328-0ff0-4ff7-b744-0e18772f9e05"
        },
        "secretSanta": {
            "href": "http://localhost:8080/api/secretSantas/f7b3c328-0ff0-4ff7-b744-0e18772f9e05"
        },
        "partyMembers": {
            "href": "http://localhost:8080/api/secretSantas/f7b3c328-0ff0-4ff7-b744-0e18772f9e05/partyMembers"
        }
    }
}
```

#### Create a Party Member
Request:
```
POST /api/partyMembers HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
	"name": "Joe Bloggs",
	"emailAddress": "jbloggs@gmail.com",
	"address": {
		"line1": "Flat 1/4",
		"line2": "12 High Street",
		"city": "London",
		"postcode": "S1 EAW"
	}
}
```

Response:
```
{
    "name": "Joe Bloggs",
    "emailAddress": "jbloggs@gmail.com",
    "address": {
        "line1": "Flat 1/4",
        "line2": "12 High Street",
        "city": "London",
        "postcode": "S1 EAW"
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/partyMembers/47ce05ab-9bef-4fd5-9d77-cc9d30916245"
        },
        "partyMember": {
            "href": "http://localhost:8080/api/partyMembers/47ce05ab-9bef-4fd5-9d77-cc9d30916245"
        },
        "secretSanta": {
            "href": "http://localhost:8080/api/partyMembers/47ce05ab-9bef-4fd5-9d77-cc9d30916245/secretSanta"
        },
        "recipient": {
            "href": "http://localhost:8080/api/partyMembers/47ce05ab-9bef-4fd5-9d77-cc9d30916245/recipient"
        }
    }
}
```

#### Attach a Party Member to a Secret Santa party
Request:
```
PUT /api/partyMembers/47ce05ab-9bef-4fd5-9d77-cc9d30916245/secretSanta HTTP/1.1
Host: localhost:8080
Content-Type: text/uri-list

http://localhost:8080/api/secretSantas/f7b3c328-0ff0-4ff7-b744-0e18772f9e05
```

Response:
```
204 No Content
```

#### Run a Secret Santa party

Exchange names and addresses amongst party members

Request:
```
POST /api/secretSantas/f7b3c328-0ff0-4ff7-b744-0e18772f9e05/run HTTP/1.1
Host: localhost:8080
```

Response:
```
{
    "id": "f7b3c328-0ff0-4ff7-b744-0e18772f9e05",
    "maxGiftCost": 10,
    "giftDispatchDeadline": "2020-12-23",
    "maxPartyMembers": 5,
    "status": "COMPLETE"
}
```

##Tools Used

The service has been built using the following tools/frameworks:
- Java 11
- Spring Boot / Rest
- Liquibase
- Lombok
- Greenmail
- Hamcrest
- Maven

## Security

The service is not currently designed with any specific security measures in place. I would NOT recommend this as a production-ready application to store user details.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.