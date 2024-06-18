# -*- coding: utf-8 -*-

from mars_rover_photos_v1_api import MarsRoverPhotosV1Api
from datetime import datetime
import response_validators
import rover_camera_helper as rc_helper
import pytest


class TestMarsRoverPhotosAPI():

    @pytest.mark.data_health
    def test_rover_photos_by_martian_sol(self, get_mars_rover_photos_v1_api, rover_name):

        # Act
        response_json = get_mars_rover_photos_v1_api.get_by_martian_sol(
            rover_name=rover_name, sol_number=1000, page_number=1)

        # Assert
        response_validators.assert_number_of_elements_valid_range(
            element_list_id='photos_' + rover_name + '_sol_1000',
            elements=response_json['photos'],
            min_elements=1,
            max_elements=25)

        for photo_element in response_json['photos']:
            photo_printable_id = self.get_photo_printable_id(
                photo=photo_element, rover_name=rover_name)

            self.check_photo_properties(
                photo_id=photo_printable_id, photo=photo_element, rover_name=rover_name)

    def test_spirit_photos_default_api_key(self, test_context_settings):

        # Arrange
        # Create api object with default api key
        custom_mars_rover_photos_v1_api = MarsRoverPhotosV1Api(
            test_context_settings['gateway_url'])

        # Act
        response_json = custom_mars_rover_photos_v1_api.get_by_martian_sol(
            rover_name='spirit', sol_number=2, page_number=1)

        # Assert
        response_validators.assert_number_of_elements_valid_range(
            element_list_id='photos_spirit_sol_2',
            elements=response_json['photos'],
            min_elements=1,
            max_elements=25)

    @pytest.mark.smoke
    def test_rover_photos_by_earth_date(self, get_mars_rover_photos_v1_api, expected_photo_amount_earth_date):

        # Arrange
        rover_name = expected_photo_amount_earth_date[0]['rover_name']
        earth_date = expected_photo_amount_earth_date[0]['earth_date']

        # Act
        response_json = get_mars_rover_photos_v1_api.get_by_earth_date(
            rover_name=rover_name, earth_date=earth_date, page_number=1)

        # Assert
        expected_amount_photos = expected_photo_amount_earth_date[1]

        response_validators.assert_number_of_elements(
            element_list_id='photos_' + rover_name + '_earth_date_' + earth_date,
            elements=response_json['photos'],
            expected_size=expected_amount_photos)
        
    
    @pytest.mark.smoke
    def test_perseverance_photos_by_camera(self, get_mars_rover_photos_v1_api, expected_photos_by_camera):

        # Arrange
        rover_name = expected_photos_by_camera[0]['rover_name']
        sol        = expected_photos_by_camera[0]['sol']
        camera     = expected_photos_by_camera[0]['camera']

        # Act
        response_json = get_mars_rover_photos_v1_api.get_by_martian_sol(
            rover_name=rover_name, sol_number=sol, camera=camera, page_number=1)

        # Assert
        expected_photo_id_list = expected_photos_by_camera[1]

        response_validators.assert_number_of_elements(
            element_list_id='photos_' + rover_name + '_sol_' + str(sol) + '_camera_' + camera,
            elements=response_json['photos'],
            expected_size=len(expected_photo_id_list))
        

    @staticmethod
    def check_photo_properties(photo_id, photo, rover_name):
        response_validators.assert_int_property_valid_range(
            element_id=photo_id, element=photo, property_name='id', min_value=0)

        response_validators.assert_int_property_valid_range(
            element_id=photo_id, element=photo, property_name='sol', min_value=0)

        response_validators.assert_url(element_id=photo_id, element=photo, property_name='img_src',
                                       expected_url_start_list=['http://mars.jpl.nasa.gov/', 'https://mars.nasa.gov/', 'http://mars.nasa.gov'], expected_url_ending_list=['.jpg', '.JPG'])

        response_validators.assert_property_not_empty(
            element_id=photo_id, element=photo, property_name='earth_date')

        response_validators.assert_property_not_empty(
            element_id=photo_id, element=photo, property_name='camera')

        rc_helper.check_photo_camera(
            element_id=photo_id + '_camera', camera_name=photo['camera']['name'], rover_name=rover_name)

        response_validators.assert_property_not_empty(
            element_id=photo_id, element=photo, property_name='rover')

    @staticmethod
    def get_photo_printable_id(photo, rover_name):
        return 'photo_id_' + str(photo['id']) + '_rover_' + rover_name
