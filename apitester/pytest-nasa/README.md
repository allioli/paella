# Overview

This is a Pytest project.

It was put together installing `python 3.12`, then `pip3 install pytest requests pyhamcrest`

It will run test cases related to the most excellent [Nasa Mars Photos API](https://api.nasa.gov/?ref=public_apis)


## Contents


| **Folder** | **Contents** |
| ---        | ---          |
|   [api_classes](api_classes) |    Reusable API objects for the different endpoints      |
|   [helpers](helpers) |     Supporting code for response validation (Camera definitions, assertions, custom hamcrest matcher)       |
|   [tests](tests) |    Pytest fixtures and tests      |
|  [conftest.py](tests/conftest.py) |   Fixtures defined here        |

## Dependencies

- Python 3.12
- pytest 8.2.2
- requests module
- pyhamcrest module

## How to run
### From terminal
`pytest`
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

Example from [mars_rover_manifests_v1_api.py](api_classes/mars_rover_manifests_v1_api.py)

```
class MarsRoverManifestsV1Api(BaseApi):

    earth_date_format = "%Y-%m-%d"

    def __init__(self, gateway_url, api_key=None):
        super().__init__(gateway_url + '/mars-photos/api/v1/manifests', api_key)

    # Example /mars-photos/api/v1/manifests/curiosity
    def get(self, rover_name):

        url = self.base_url + '/' + rover_name

        query_params_with_api_token = self.add_api_key_to_query_params()

        response = requests.request(
            'GET', url, headers=self.headers, params=query_params_with_api_token)

        assert (response.status_code == 200), "Expected HTTP 200, received HTTP " + \
            str(response.status_code) + " : " + response.text

        return response.json()['photo_manifest']
    
```

### CI-friendly assert output
Print friendly descriptions that identify a specfic data resource under test are passed to pytest assertions and pyhamcrest matchers with `described_as`, so that a person checking the test logs can quickly get that all-important context information without having to scroll up the log output for ages.

Example from [rover_camera_helper.py](helpers/rover_camera_helper.py)

```
def check_photo_camera(element_id, camera_name, rover_name):

    allowed_camera_types_for_rover = expected_rover_camera_types[MarsRover(
        rover_name)]

    assert_that(CameraType(camera_name),
                described_as('Photos in ' + element_id + ' with unexpected camera. Allowed cameras for rover ' + rover_name + ' are: ' + str(allowed_camera_types_for_rover),
                             is_in(allowed_camera_types_for_rover)))

    
```

## Custom Pyhamcrest matcher
This is an example of a matcher that checks a URL string for allowed starting and ending substrings. It comes handy for early detection of typos in URLs / unexpected file extensions. From [custom_string_matchers.py](helpers/custom_string_matchers.py)

```
class IsValidURL(BaseMatcher):

    def __init__(self, url_start_list, url_ending_list):
        self.url_start_list = url_start_list
        self.url_ending_list = url_ending_list

    def _matches(self, item):
        # Guard check non-empty string item
        if (not isinstance(item, str)) or (len(item) == 0):
            return False

        matching_result = self.check_url_starts(
            item) and self.check_url_endings(item)

        return matching_result

    def describe_to(self, description):
        description.append_text('URL should start with one of ')    \
                   .append_text(str(self.url_start_list))

        description.append_text('. URL should end with one of ')    \
                   .append_text(str(self.url_ending_list))

    def check_url_starts(self, item):
        for url_start in self.url_start_list:
            if item.startswith(url_start):
                return True
        return False

    def check_url_endings(self, item):
        for url_ending in self.url_ending_list:
            if item.endswith(url_ending):
                return True
        return False


def is_valid_url(url_start_list, url_ending_list):
    return IsValidURL(url_start_list=url_start_list, url_ending_list=url_ending_list)

    
```

### Fixtures for the people
In this project we use fixtures defined in [conftest.py](tests/conftest.py) to:
* Define basic context settings, like API gateway, API key
* Define expectations for the content
* Initialise API objects
* Support data-driven tests (parameterised fixtures for rover names, request-response expectations)


### Test scope
There are different types of tests:
* Tests that check expected data or number of resources given a specific request
* Tests that check that returned data is valid (presence, range of expected properties, date consistency, URL content)

Cross-endpoint testing is out of scope of this project. It would be a natural next step to check consistency of manifest, camera data across endpoints or supporting more functional, high level flows from a real client app.

 

## Sources
* [Mars photo api github](https://github.com/corincerami/mars-photo-api?tab=readme-ov-file)
* [Writing pyhamcrest custom matchers](https://pyhamcrest.readthedocs.io/en/release-1.8/custom_matchers/)
* [Pytest basics](https://docs.pytest.org/en/8.2.x/)







