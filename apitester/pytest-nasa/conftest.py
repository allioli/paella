# -*- coding: utf-8 -*-

import pytest
import sys
sys.path.append('api_classes')
from mars_rover_photos_v1_api import MarsRoverPhotosV1Api
from mars_rover_manifests_v1_api import MarsRoverManifestsV1Api


@pytest.fixture(scope="session")
def test_context_settings():
    test_context_settings = {
        'gateway_url': 'https://api.nasa.gov',
        'dev_api_key': 'e3Wh6EN0m0BcPY2Qa2H3QerLKmZQFnTMJ3OrUvYS'
    }

    return test_context_settings


@pytest.fixture(scope="class")
def get_mars_rover_photos_v1_api(test_context_settings):
    mars_rover_photos_v1_api_instance = MarsRoverPhotosV1Api(
        test_context_settings['gateway_url'], test_context_settings['dev_api_key'])

    return mars_rover_photos_v1_api_instance


@pytest.fixture(scope="class")
def get_mars_rover_manivests_v1_api(test_context_settings):
    mars_rover_manifests_v1_api_instance = MarsRoverManifestsV1Api(
        test_context_settings['gateway_url'], test_context_settings['dev_api_key'])

    return mars_rover_manifests_v1_api_instance
