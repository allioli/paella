# -*- coding: utf-8 -*-

import sys
import os
from pathlib import Path
sys.path.append(os.path.join(Path(os.path.dirname(__file__)).parent, 'api_classes'))
sys.path.append(os.path.join(Path(os.path.dirname(__file__)).parent, 'helpers'))

from authentication_v3_api import AuthenticationV3Api
from account_v3_api import AccountV3Api
from movies_v3_api import MoviesV3Api
from movie_top_rated_list_v3_api import MovieTopRatedListV3Api
import pytest
from pytest_bdd import given, then
from pytest_bdd import parsers
from dotenv import dotenv_values

## ARRANGE Fixtures ##

# Load test context settings
@pytest.fixture(scope="session")
def test_context_settings():
    test_context_settings = dotenv_values('.env')
    return test_context_settings

# Initialise api object
# Session scope, because we only need to use it once per test session and cache the result
@pytest.fixture(scope='session')
def get_authentication_v3_api(test_context_settings):
    authentication_v3_api = AuthenticationV3Api(
        test_context_settings['TMDB_GATEWAY_URL'], test_context_settings['TMDB_API_ACCESS_TOKEN'])

    return authentication_v3_api

# Initialise api object
@pytest.fixture(scope='class')
def get_account_v3_api(test_context_settings):
    account_v3_api = AccountV3Api(
        test_context_settings['TMDB_GATEWAY_URL'], test_context_settings['TMDB_API_ACCESS_TOKEN'])

    return account_v3_api

# Initialise api object
@pytest.fixture(scope='class')
def get_movies_v3_api(test_context_settings):
    movies_v3_api = MoviesV3Api(
        test_context_settings['TMDB_GATEWAY_URL'], test_context_settings['TMDB_API_ACCESS_TOKEN'])

    return movies_v3_api

# Initialise api object
@pytest.fixture(scope='class')
def get_movie_top_rated_v3_api(test_context_settings):
    movie_top_rated_list_v3_api = MovieTopRatedListV3Api(
        test_context_settings['TMDB_GATEWAY_URL'], test_context_settings['TMDB_API_ACCESS_TOKEN'])

    return movie_top_rated_list_v3_api

# Create guest user session
# Session scope, because we only need to use it once per test session and cache the result
@pytest.fixture(scope='session')
def guest_session_id(get_authentication_v3_api):
    return get_authentication_v3_api.create_guest_session().json()['guest_session_id']

## ACT fixtures ##

# Act step to return a response to a Then step
# To be overriden by different When steps performing API calls
@pytest.fixture(scope='session')
def response():
    return {}

## Common GIVEN steps ##
@given('I have logged in as a guest moviedb user')
def step_given_logged_in_session_id(guest_session_id):
    """I have logged in as a moviedb user."""

## Common THEN steps ##
@then(parsers.parse('Response status code is \"{status_code:d}\"'))
def step_then_response_contains(response, status_code):
    """Assert on Response."""
    assert(response.status_code == status_code)

@then(parsers.parse('Response status message is \"{response_message}\"'))
def step_then_response_contains(response, response_message):
    """Assert on Response."""
    response_json = response.json()
    assert(response_json['status_message'] == response_message)