# -*- coding: utf-8 -*-

import sys
import os
from pathlib import Path
sys.path.append(os.path.join(Path(os.path.dirname(__file__)).parent, 'api_classes'))

from authentication_v3_api import AuthenticationV3Api
from account_v3_api import AccountV3Api
from movies_v3_api import MoviesV3Api
import pytest
from pytest_bdd import given, then
from pytest_bdd import parsers
from dotenv import dotenv_values


# Arrange step with test context settings
@pytest.fixture(scope="session")
def test_context_settings():
    test_context_settings = dotenv_values('.env')
    return test_context_settings

# Arrange step to initialise api object
@pytest.fixture(scope='class')
def get_authentication_v3_api(test_context_settings):
    authentication_v3_api = AuthenticationV3Api(
        test_context_settings['TMDB_GATEWAY_URL'], test_context_settings['TMDB_API_ACCESS_TOKEN'])

    return authentication_v3_api

# Arrange step to initialise api object
@pytest.fixture(scope='class')
def get_account_v3_api(test_context_settings):
    account_v3_api = AccountV3Api(
        test_context_settings['TMDB_GATEWAY_URL'], test_context_settings['TMDB_API_ACCESS_TOKEN'])

    return account_v3_api

# Arrange step to initialise api object
@pytest.fixture(scope='class')
def get_movies_v3_api(test_context_settings):
    movies_v3_api = MoviesV3Api(
        test_context_settings['TMDB_GATEWAY_URL'], test_context_settings['TMDB_API_ACCESS_TOKEN'])

    return movies_v3_api

# Act step to return a response to a Then step
@pytest.fixture(scope='function')
def response():
    return {}

@given('I have logged in as a guest moviedb user', target_fixture="guest_session_id")
def step_given_logged_in_session_id(get_authentication_v3_api):
    """I have logged in as a moviedb user."""
    return get_authentication_v3_api.create_guest_session()


@then(parsers.parse('Response status code is \"{status_code:d}\"'))
def step_then_response_contains(response, status_code):
    """Assert on Response."""
    assert(response.status_code == status_code)

@then(parsers.parse('Response status message is \"{response_message}\"'))
def step_then_response_contains(response, response_message):
    """Assert on Response."""
    response_json = response.json()
    assert(response_json['status_message'] == response_message)