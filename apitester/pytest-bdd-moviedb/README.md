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


### Fixtures for the people
In this project we use fixtures defined in [conftest.py](tests/conftest.py) to:
* Load basic context settings, like API gateway, API tokens from .env file
* Initialise API objects
* Support data-driven tests (parameterised fixtures for rover names, request-response expectations)
 

## Sources
* [Pytest BDD documentation](https://pypi.org/project/pytest-bdd/)
* [Pytest BDD Guide With Eric](https://pytest-with-eric.com/bdd/pytest-bdd/)







