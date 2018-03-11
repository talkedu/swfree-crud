# swfree-crud

Swfree-crud is a web API built to store information of SW planets. It was developed in Java 8 using Spring Boot and a embedded MongoDB.

# Running
```sh
$ ./run.sh
```

A server will be created at the port 8080.

## API Documentation

### Planets Collection [/api/planets]

#### List All Planets [GET]

+ Response 200 (application/json)

        [
            {
                "name": "Alderaan",
                "terrain": "water",
                "climate" : cold,
                "occurences": 4 
            }
        ]

#### Create a New Planet [POST]

You may create your own planet and if is a real SW planet, will ask another api (swapi.co) how many movies that planet had occurrences and store it too.

+ Request (application/json)

        {
            {
                "name": "Alderaan",
                "terrain": "water",
                "climate" : "cold"
            }
        }

+ Response 201 (application/json)

    + Headers

            Location: /api/planets/{id}
            
    + Body

            {
                "id": "{id}",
                "name": "Alderaan",
                "climate": "cold",
                "terrain": "water",
                "occurrences": 4
            }

#### Delete All Planets [DELETE]

+ Response 204

        
### Planets Search [/api/planets{?name}]

+ Parameters
    + name (string) - Name of the planet in the form of an string

    
#### Search Planets By name [GET]

You may search for Planets by a specific name
    
+ Response 200 (application/json)

        [
            {
                "name": "Alderaan",
                "terrain": "water",
                "climate" : cold,
                "occurences": 4 
            }
        ]    


## Planet [/api/planets/{id}]

+ Parameters
    + id (string) - ID of the planet in the form of an string

### Delete a Planet [DELETE]

You may delete a planet giving it's id

+ Response 204
+ Response 400

### Find a Planet [GET]

You may find a planet giving it's id

+ Response 200 (application/json)

        {
            "id": "{id}",
            "name": "Alderaan",
            "climate": "cold",
            "terrain": "water",
            "occurrences": 4
        }

+ Response 400
