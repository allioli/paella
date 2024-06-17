# -*- coding: utf-8 -*-

import pytest
import sys
import os
from pathlib import Path

sys.path.append(os.path.join(Path(os.path.dirname(__file__)).parent, 'api_classes'))
sys.path.append(os.path.join(Path(os.path.dirname(__file__)).parent, 'helpers'))

from mars_rover_photos_v1_api import MarsRoverPhotosV1Api
from mars_rover_manifests_v1_api import MarsRoverManifestsV1Api


# Arrange step with test context settings
@pytest.fixture(scope="session")
def test_context_settings():
    test_context_settings = {
        'gateway_url': 'https://api.nasa.gov',
        'dev_api_key': 'e3Wh6EN0m0BcPY2Qa2H3QerLKmZQFnTMJ3OrUvYS'
    }

    return test_context_settings

# Arrange step to initialise api object
@pytest.fixture(scope='class')
def get_mars_rover_photos_v1_api(test_context_settings):
    mars_rover_photos_v1_api_instance = MarsRoverPhotosV1Api(
        test_context_settings['gateway_url'], test_context_settings['dev_api_key'])

    return mars_rover_photos_v1_api_instance

# Arrange step to initialise api object
@pytest.fixture(scope='class')
def get_mars_rover_manifests_v1_api(test_context_settings):
    mars_rover_manifests_v1_api_instance = MarsRoverManifestsV1Api(
        test_context_settings['gateway_url'], test_context_settings['dev_api_key'])

    return mars_rover_manifests_v1_api_instance

# Arrange step with several rover names
@pytest.fixture(scope="session", params=['spirit', 'opportunity', 'curiosity', 'perseverance'])
def rover_name(request):
    return request.param

# Arrange step with expected data params on photos by earth date
@pytest.fixture(params=[({'rover_name': 'curiosity', 'earth_date': '2014-12-12'},  8),
                        ({'rover_name': 'perseverance', 'earth_date': '2024-06-12'},  16)])
def expected_photo_amount_earth_date(request):
    return request.param