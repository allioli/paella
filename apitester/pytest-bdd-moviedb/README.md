# Overview

This is a Pytest BDD project. Before you start, I recommend you check the pytest-nasa sample project, which is based on standard pytest.

It was put together installing `python 3.12`, then `pip3 install pytest requests pytest-bdd python-dotenv`.

It will run test scenarios related to the [The Movie Database API](https://developer.themoviedb.org/reference/intro/getting-started).

## Contents


| **Folder** | **Contents** |
| ---        | ---          |
|  [api_classes](api_classes) |    Reusable API objects for the different endpoints      |
|  [tests/features](tests/features) |    Feature files with scenarios in Gherkin         |
|  [tests/step_definitions](tests/step_definitions) |    Glue code with step definitions |
|  [conftest.py](tests/conftest.py) |   Fixtures and common steps defined here           |

## Dependencies

- Python 3.12
- pytest 8.2.2 with plugin bdd-7.2.0
- requests module
- pydotenv module

## How to run
### From terminal
`pytest`

Cucumber-style output is enabled by default via settings in [pytest.ini](pytest.ini)

### From your favourite IDE
- Import pytest project
- Refresh tests (Example Vistual Studio Code: Command Palette -> Test: Refresh tests
- Go to Tests section
- You should see all tests expanded
- Run / Debug at will

## Highlights

### API objects for easier maintainability
In this project, we want to encapsulate the request, authentication, error handling, gateway URLs and payload format of the calls to the API. This way we can abstract all these details and concerns from the actual testing logic. API object pattern is similar to Page objects, but for API tests. 

Decoupling these two major blocks of logic will allow them to evolve separately (new versions of the API will result in new API objects), at the same time as reducing code duplication.

The base class [base_api.py](api_classes/base_api.py) takes care of common behaviour and settings, while the other classes cover requests for a particular enpoint.


### Common Fixtures and Steps
In [conftest.py](tests/conftest.py) you will find scoped fixtures to perform the following set-up tasks:
* Load basic context settings, like API gateway, API tokens from .env file
* Initialise API objects
* Retrieve guest session id
* Provide a base mechanism to pass response objects to other steps, overriding a common fixture

Common Given, Then steps with glue code can be also found in [conftest.py](tests/conftest.py) that deal with: 
* Providing guest session id to other steps
* Asserting on response status and status message 

### When steps: Functional vs Intent
How do we keep the code in steps and API objects maintainable while supporting both happy path and error scenarios? Re-using API object methods and defining different steps for each type of scenario.

* **Functional steps** are assertive "When" steps that support happy path scenarios. We expect a positive result, so the same API object method that performs the API call (user action) is also checking a success response status code. This is an example of "close ended" step. A bit of the validation is added to the action itself for convenience and DRY, at the price of less flexibility and step re-usability.

Example from [test_movie_rating.py](tests/step_definitions/test_movie_rating.py)
```python
@when(parsers.parse('I rate The GodFather with value \"{rating:.1f}\"'))
def step_when_rate_the_godfather(get_movies_v3_api, guest_session_id, rating):
    """I rate The GodFather."""
    get_movies_v3_api.rate_movie(movie_id=238, session_id=guest_session_id, rating=rating, expected_status_code=201)
```

* **Intent steps** are tentative "When" steps that support error flows. We expect different error status codes, a list that could grow over time. In this case, we use an "open ended" step, that only performs an API call (user action) and delegates any validation to downstream "Then" steps by returning the response object. We decouple the API object methods from the list of relevant error codes, at the price of having to return the response object, instead of an optimistic response.json() that saves us some code on the step side.
```python
@when(parsers.parse('I try to rate a film with value \"{rating}\"'), target_fixture="response")
def step_when_rate_film_intent(get_movies_v3_api, guest_session_id, rating):
    """I rate a film intent."""
    return get_movies_v3_api.rate_movie(movie_id=238, session_id=guest_session_id, rating=rating)   
```

## Sources
* [Pytest BDD documentation](https://pypi.org/project/pytest-bdd/)
* [Pytest BDD Guide With Eric](https://pytest-with-eric.com/bdd/pytest-bdd/)







