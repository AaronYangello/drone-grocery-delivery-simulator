# Drone Grocery Delivery Simulator

Contributors: Aaron Yangello, Casey Keyser, and Andrew Collins  
_Git repository was migrated from student accounts and git history was lost._

The Drone Grocery Delivery Simulator is a software project designed to prototype and simulate a drone-based grocery delivery service. It models the behavior of drones tasked with delivering grocery orders from a central hub to customer locations. The project focuses on implementing and testing delivery logistics, including route optimization, drone capabilities, and delivery efficiency under various conditions. Built as a standalone application, the simulator demonstrates the feasibility and challenges of autonomous delivery systems in real-world scenarios.

## Running Manually

### start server/client
```sh
docker compose up --build
# in another terminal:
docker compose build client
docker compose run client
# ctrl-c to exit
```

### connect to db (optional)
```sh
docker exec -it db mysql -uroot -ppassword deliveryservice
```

### stop
```sh
docker compose down
```

Open [http://127.0.0.1:8080/stores](http://127.0.0.1:8080/stores) in browser after creating a store. the first time should prompt you to login -> admin:admin

## Running with Test Scripts

### Individual Test Cases

To run a single test case, use:

```sh
./scripts/test.sh {scenario-number} {start-other-containers}
```

Example:

```sh
./scripts/test.sh 00 true
```

will run `docker compose up`, run the commands in ./test_scenarios/commands_00.txt and then run `docker compose down` after the test is complete

### Batch Test Cases

To run all of the test cases, use:

```sh
./scripts/batch.sh
```

### Output

In either case, the results of the test will be saved to ./docker_results/

### Test File Descriptions

./test_scenarios/commands_XX.txt & ./test_results/drone_delivery_initial_XX_results.txt
 - 00-11 Original A3 Test Files
 - 12-39 Additional Test files used in grading A3
 - 40 	 Focuses on error handling
 - 41	 Focuses on configurability (`config list`, `config show`, `config set`)
 - 42	 Focuses on Authentication and Authorization


## Actions 

### Basic Actions 

All valid actions from A3 are supported in this application plus the following

- `login,username,password`

### Config Commands

| Setting         | Description                                                                                                  | Set Command                                                       | Show Command                                   |
|-----------------|-------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------|------------------------------------------------|
| list            | Display list of `config` commands.                                                                          |                                                                   | `config list`                                  |
| show            | Display current value of the setting.                                                                       |                                                                   | `config show [setting] [args]`                 |
| error-message   | The text displayed to the user when an error is encountered                                                 | `config set error-message [error] [error-message-text]`           | `config show error-message`                    |
| pilot-probation | Number of trips required to be completed by a pilot before they can operate drones with more than one order | `config set pilot-probation [store-name] [num-trips-to-complete]` | `config show pilot-probation [store-name]`     |
| pilot-vacation  | Scheduled time off for pilots                                                                               | `config set pilot-vacation [pilot-account] [yyyy-mm-dd]`          | `config show pilot-vacation [store-name]`      |
| min-rating      | Minimum required rating for a customer to place an order at a given store                                   | `config set min-rating [store-name] [min-rating-required]`        | `config show min-rating [store-name]`          |
| min-credit      | Minimum required credit for a customer to place an order at a given store                                   | `config set min-credit [store-name] [min-credit-required]`        | `config show min-credit [store-name]`          |
| drone-alert     | Number of trips remaining at which a warning will be issued that maintenance is soon required               | `config set drone-alert [store-name] [number-of-trips]`           | `config show drone-alert [store-name]`         |
| favorite-order  | A list of favorite orders saved by a customer for later review                                              | `config set favorite-order [customer-account] [order]`            | `config show favorite-order [customer-account] |
| price-alert     | Items for which customers will be notified of a price change                                                | `config set price-alert [customer-account] [store-name] [item]`   | `config show price-alert [customer-account]    |

### REST API Usage
- Create Store  
Method: `POST`  
Path: `/stores`  
Request:
```json
{
  "name": "kroger",
  "revenue": 1000,
  "droneMaintenanceAlertThreshold": 5,
  "minRequiredCredit": 0,
  "minRequiredRating": 0
}
```
- Get Stores  
Method: `GET`  
Path: `/stores`  
Response:  
```json
[
  {
    "name": "kroger",
    "revenue": 1000,
    "droneMaintenanceAlertThreshold": 5,
    "minRequiredCredit": 0,
    "minRequiredRating": 0
  }
]
```
- Sell Item  
Method: `POST`  
Path: `/stores/{storeName}/items`  
Request:
```json
{
  "name": "coffee",
  "weight": 3
}
```
- Get Items  
Method: `GET`  
Path: `/stores/{storeName}/items`  
Response:
```json
[
  {
    "id": 2,
    "store": {
      "name": "kroger",
      "revenue": 1000,
      "droneMaintenanceAlertThreshold": 5,
      "minRequiredCredit": 0,
      "minRequiredRating": 0
    },
    "name": "coffee",
    "weight": 3
  }
]
```
- Create Drone  
Method: `POST`  
Path: `/stores/{storeName}/drones`  
Request:
```json
{
  "id": 1,
  "totalCapacity": 40,
  "remainingTrips": 3
}
```
- Get Drones For Store  
Method: `GET`  
Path: `/stores/{storeName}/drones`  
Response:
```json
[
  {
    "id": 1,
    "totalCapacity": 40,
    "remainingTrips": 3,
    "store": {
      "name": "kroger",
      "revenue": 1000,
      "droneMaintenanceAlertThreshold": 5,
      "minRequiredCredit": 0,
      "minRequiredRating": 0
    },
    "orders": [],
    "pilot": null
  }
]
```
- Create Pilot  
Method: `POST`  
Path: `/pilots`  
Request:
```json
{
  "account": "ffig8",
  "firstName": "Finneas",
  "lastName": "Fig",
  "phoneNumber": "888-888-8888",
  "taxIdentifier": "890-12-3456",
  "licenseID": "panam_10",
  "experience": 33
}
```
- Get Pilots  
Method: `GET`  
Path: `/pilots`  
Response:
```json
[
  {
    "firstName": "Finneas",
    "lastName": "Fig",
    "phoneNumber": "888-888-8888",
    "taxIdentifier": "890-12-3456",
    "account": "ffig8",
    "licenseID": "panam_10",
    "experience": 33,
    "drone": null,
    "fullName": "Finneas Fig"
  }
]
```
- Assign Pilot to Drone  
Method: `POST`  
Path: `/pilots/{pilotAccount}/assignDrone`  
Request:
```json
{
  "storeName": "kroger",
  "identifier": 1
}
```
- Create Customer  
Method: `POST`  
Path: `/customers`  
Request:
```json
{
  "account": "aapple2",
  "firstName": "Alana",
  "lastName": "Apple",
  "phoneNumber": "222-222-2222",
  "rating": 4,
  "credit": 100
}
```
- Get Customers  
Method: `GET`  
Path: `/customers`  
Response:
```json
[
  {
    "firstName": "Alana",
    "lastName": "Apple",
    "phoneNumber": "222-222-2222",
    "account": "aapple2",
    "rating": 4,
    "credit": 100,
    "orders": [],
    "fullName": "Alana Apple"
  }
]
```
- Create Order  
Method: `POST`  
Path: `/stores/{storeName}/orders`  
Request:
```json
{
  "name": "purchaseA",
  "storeName": "kroger",
  "droneIdentifier": 1,
  "customerAccount": "aapple2"
}
```
- Get Orders  
Method: `GET`  
Path: `/stores/{storeName}/orders`  
Response:
```json
[
  {
    "name": "purchaseA",
    "store": {
      "name": "kroger",
      "revenue": 1000,
      "droneMaintenanceAlertThreshold": 5,
      "minRequiredCredit": 0,
      "minRequiredRating": 0
    },
    "drone": {
      "id": 1,
      "totalCapacity": 40,
      "remainingTrips": 3,
      "store": {
      "name": "kroger",
      "revenue": 1000,
      "droneMaintenanceAlertThreshold": 5,
      "minRequiredCredit": 0,
      "minRequiredRating": 0
    },
      "pilot": null
    },
    "customer": {
      "firstName": "Alana",
      "lastName": "Apple",
      "phoneNumber": "222-222-2222",
      "account": "aapple2",
      "rating": 4,
      "credit": 100,
      "fullName": "Alana Apple"
    },
    "lineItems": []
  }
]
```
- Create Line Item  
Method: `POST`  
Path: `/stores/{storeName}/orders/{orderName}/lineItems`  
Request:
```json
{
  "itemName": "coffee",
  "itemPrice": 40,
  "itemQuantity": 2
}
```
- Get Line Items  
Method: `GET`  
Path: `/stores/{storeName}/orders/{orderName}/lineItems`  
Request:
```json
[
  {
    "id": 8,
    "order": {
      "name": "purchaseA",
      "store": {
        "name": "kroger",
        "revenue": 1000,
        "droneMaintenanceAlertThreshold": 5,
        "minRequiredCredit": 0,
        "minRequiredRating": 0
      },
      "drone": {
        "id": 1,
        "totalCapacity": 40,
        "remainingTrips": 3,
        "store": {
          "name": "kroger",
          "revenue": 1000,
          "droneMaintenanceAlertThreshold": 5,
          "minRequiredCredit": 0,
          "minRequiredRating": 0
        },
        "pilot": null
      },
      "customer": {
        "firstName": "Alana",
        "lastName": "Apple",
        "phoneNumber": "222-222-2222",
        "account": "aapple2",
        "rating": 4,
        "credit": 100,
        "fullName": "Alana Apple"
      },
      "totalCost": 80,
      "totalWeight": 6
    },
    "item": {
      "id": 1,
      "store": {
        "name": "kroger",
        "revenue": 1000,
        "droneMaintenanceAlertThreshold": 5,
        "minRequiredCredit": 0,
        "minRequiredRating": 0
      },
      "name": "coffee",
      "weight": 3
    },
    "itemQuantity": 2,
    "itemPrice": 40,
    "totalCost": 80,
    "totalWeight": 6
  }
]
```
- Purchase Order  
Method: `POST`  
Path: `/stores/{storeName}/orders/{orderName}/purchase`  

- Get Config Options  
Method: `GET`  
Path: `/config`  

- Config Drone Maintenance Alert Threshold
Method: `Post`
Path: `/stores/{storeName}`
Request:
```json
{
  "droneMaintenanceAlertThreshold":5
}
```

- Config Customer Minimum Required Credit
Method: `Post`
Path: `/stores/{storeName}`
Request:
```json
{
  "minRequiredCredit": 0,
}
```

- Config Customer Minimum Required Rating
Method: `Post`
Path: `/stores/{storeName}`
Request:
```json
{
  "minRequiredRating": 0
}
```

- Config Pilot Probation Duration
Method: `Post`
Path: `/stores/{storeName}`
Request:
```json
{
  "pilotProbationDuration": 0
}
```

- Config Pilot Vacation Dates
Method: `Post`
Path: `/pilots/{account}`
Request:
```json
{
  "vacationDate": [2021-12-25]
}
```
