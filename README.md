# AppEngine Diff API
This is a **REST API** developed with Java, that runs on top of **Google AppEngine** (to avoid wasting time with servers configuration and maintenance :) ).

It exposes two endpoints for loading both the left and right inputs for comparison and a third endpoint that lists the differences between the two inputs.

## Libraries
Following are the main libraries used in this project and the motivation to use them:
* **Jersey**: For defining the REST API;
* **Jackson**: For JSON parsing;
* **Objectify**: for encapsulating Google Datastore operations;
* **Guice**: for Dependency Injection.

For tests:
* **junit**;
* **truth** (for assertions);
* **mockito**;
* **jersey-test-framework**: for API integration tests;


## Running the project
In order run the service in your local machine, follow these steps:

1. Install docker: https://docs.docker.com/engine/installation/
2. Clone the project: `git clone git@github.com:ivamluz/iluz-differ-api.git`
3. `cd` into the root project folder (the one containing the **Dockerfile**)
4. Build the image: `docker build -t appengine-differ-api .`
5. List the available images: `docker images`
6. Run the container (grab IMAGE ID from the previous command output): `docker run -v `pwd`/appengine-differ-api/:/appengine-differ-api -p 8080:8080 -i -t <IMAGE ID>`
7. Inside the container:
    * 7.1. `cd appengine-differ-api/`
    * 7.2. **Run the project** in background: `mvn appengine:devserver  &`
    * 7.3. Wait for the server to be up and run the following command for executing the **integration tests**: `mvn integration-test`

## API Enpoints
There are 3 endpoints available:

### `POST http://host/v1/diff/<id>/left`
Receives data to be diff-ed on the left side.

* Returns empty body with *200 OK* status if the data is accepted and properly saved.
* Returns *400 Bad Request* if the data can't be accepted.

```
curl -i \
-H "Content-type: application/json" \
-d "{\"data\": \"`echo -e \"hello\n\nthis is\nmy tests\nwith multiple\nline\nand line\" | base64`\"}" \
'http://localhost:8080/v1/diff/1/left'
```

### `POST http://host/v1/diff/<id>/right`
Receives data to be diff-ed on the right side.

* Returns empty body with *200 OK* status if the data is accepted and properly saved.
* Returns *400 Bad Request* if the data can't be accepted.

```
curl -i \
-H "Content-type: application/json" \
-d "{\"data\": \"`echo -e \"this is\nmy test\nwith multiple\nlines\" | base64`\"}" \
'http://localhost:8080/v1/diff/1/right'
```

### `GET http://host/v1/diff/<id>`
Diff-ies the previously input values.
* Returns a JSON containing the number of differences and a list with the differences found if everything is OK.
* Returns a *400 Bad Request* and error JSON in any of the following situations:
    * Left side is missing;
    * Right side is missing;

```
curl -i 'http://localhost:8080/v1/diff/1'
```

In case of **success**, the output of this API call is a JSON with a format similar to the following:
```javascript
{
	"differences": [{
		"type": "DELETED",
		"original": {
			"position": 0,
			"size": 2,
			"lines": ["hello", ""]
		},
		"revised": {
			"position": 0,
			"size": 0,
			"lines": []
		}
	}, {
		"type": "CHANGED",
		"original": {
			"position": 3,
			"size": 1,
			"lines": ["my tests"]
		},
		"revised": {
			"position": 1,
			"size": 1,
			"lines": ["my test"]
		}
	}, {
		"type": "CHANGED",
		"original": {
			"position": 5,
			"size": 2,
			"lines": ["line", "and line"]
		},
		"revised": {
			"position": 3,
			"size": 1,
			"lines": ["lines"]
		}
	}],
	"count": 3
}
```

In case of **error**, the output will be a JSON similar to the following:
```javascript
{
	"status": 400,
	"message": "Both left and right inputs should be set for diffJob-ing",
	"code": "1"
}
```


## Limitations
* Maximum size for both left and right inputs is **~1mb**. The reason for this, is that I chose to use Google Datastore as the database for saving the inputs and the service has a limit of around 1mb per entity saved;
* Project is tied to Google AppEngine, so it's not possible to host it on another provider.

## Potential improvements
* Refactor the project, so it can run, for example, on Docker, making it more portable;
* Use MySQL as the database, instead of Google Datastore, so we remove the 1mb limitation. The downside of it is that it would make it harder to scale the solution;
* Implement an JWT auth mechanism for the API. That would improve the security and avoid users to mess up with each others data;
* Create a Jenkins pipeline integrated with Artifactory. Thus, we could generate and save the war and deploy it to different Google AppEngine instances (QA, Staging and Production, for example);
* Expose a single endpoint through which users could post both sides and get the diff as result.
* Define proper codes for API errors;
