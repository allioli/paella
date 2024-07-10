# -*- coding: utf-8 -*-

import sys
import os
from pathlib import Path
sys.path.append(os.path.join(Path(os.path.dirname(__file__)).parent, 'api_classes'))

from authentication_v3_api import AuthenticationV3Api
from account_v3_api import AccountV3Api
from movies_v3_api import MoviesV3Api
import pytest
from pytest_bdd import given




# Arrange step with test context settings
@pytest.fixture(scope="session")
def test_context_settings():
    test_context_settings = {
        'gateway_url': 'https://api.themoviedb.org',
        'tmbdb_account_id': 7954295,
        'access_token': 'eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOGQ5YjZhMGFhMzBjOWE1ZGJkZDA0YjU5Mzk0NDQxNyIsIm5iZiI6MTcyMDQzOTY2NC43MzE5MzUsInN1YiI6IjViM2Y0MDA5MGUwYTI2MjZkZTAwNWI1MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PXizLLv6rFT1soWp_mkjh6p1t3P5ZWTLf2kSzbrBhEA',
        'image_url_start': 'https://image.tmdb.org/t/p/w300/',
        'allowed_image_url_endings': ['.jpg', '.JPG']
    }

    return test_context_settings

# Arrange step to initialise api object
@pytest.fixture(scope='class')
def get_authentication_v3_api(test_context_settings):
    authentication_v3_api = AuthenticationV3Api(
        test_context_settings['gateway_url'], test_context_settings['access_token'])

    return authentication_v3_api

# Arrange step to initialise api object
@pytest.fixture(scope='class')
def get_account_v3_api(test_context_settings):
    account_v3_api = AccountV3Api(
        test_context_settings['gateway_url'], test_context_settings['access_token'])

    return account_v3_api

# Arrange step to initialise api object
@pytest.fixture(scope='class')
def get_movies_v3_api(test_context_settings):
    movies_v3_api = MoviesV3Api(
        test_context_settings['gateway_url'], test_context_settings['access_token'])

    return movies_v3_api

@given('I have logged in as a moviedb user', target_fixture="session_id")
def step_given_logged_in_session_id(get_authentication_v3_api):
    """I have logged in as a moviedb user."""
    return get_authentication_v3_api.create_guest_session()