# -*- coding: utf-8 -*-

from mars_rover_photos_v1_api import MarsRoverPhotosV1Api
from datetime import datetime
import response_validators
import rover_camera_helper as rc_helper


class TestMarsRoverPhotosAPI():
    
    def test_curiosity_photos_by_martian_sol(self, get_mars_rover_photos_v1_api):

        # Act
        photos = get_mars_rover_photos_v1_api.get_by_martian_sol(
            rover_name='curiosity', sol_number=1000, page_number=1)

        # Assert
        assert (len(photos) ==
                25), "Unexpected number of curiosity photos received. Expected 25, found " + str(len(photos))

    def test_spirit_photos_default_api_key(self, test_context_settings):

        # Arrange
        # Create api object with default api key
        custom_mars_rover_photos_v1_api = MarsRoverPhotosV1Api(
            test_context_settings['gateway_url'])

        # Act
        photos = custom_mars_rover_photos_v1_api.get_by_martian_sol(
            rover_name='spirit', sol_number=2, page_number=1)

        # Assert
        assert (len(photos) == 25), "Unexpected number of spirit photos received. Expected 25, found " + \
            str(len(photos))

    def test_curiosity_photos_by_earth_date(self, get_mars_rover_photos_v1_api):

        # Act
        photos = get_mars_rover_photos_v1_api.get_by_earth_date(
            rover_name='curiosity', earth_date='2014-12-12', page_number=1)

        # Assert
        assert (len(photos) == 8), "Unexpected number of curiosity photos received. Expected 8, found " + \
            str(len(photos))
