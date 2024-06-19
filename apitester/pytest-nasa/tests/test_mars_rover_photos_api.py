# -*- coding: utf-8 -*-

from mars_rover_photos_v1_api import MarsRoverPhotosV1Api
from datetime import datetime
import response_validators
import rover_camera_helper as rc_helper
import pytest
from hamcrest import *
from custom_string_matchers import is_valid_url


class TestMarsRoverPhotosAPI():

    @pytest.mark.data_health
    def test_rover_photos_by_martian_sol(self, get_mars_rover_photos_v1_api, test_context_settings, rover_name):

        # Arrange
        sol = 1000
        max_photos = test_context_settings['max_photos_per_page']
        min_photos = 1

        # Act
        response_json = get_mars_rover_photos_v1_api.get_by_martian_sol(
            rover_name=rover_name, sol_number=sol, page_number=1)

        # Assert
        response_validators.assert_number_of_elements_valid_range(element_description='photos_' + rover_name + '_sol_' + str(sol),
                                                                  element_list=response_json['photos'],
                                                                  min_elements=min_photos,
                                                                  max_elements=max_photos)

        for photo_element in response_json['photos']:
            photo_printable_id = self.get_photo_printable_id(
                photo=photo_element, rover_name=rover_name)

            self.check_photo_properties(
                photo_id=photo_printable_id, photo=photo_element, rover_name=rover_name, test_context_settings=test_context_settings)

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
            element_description='photos_spirit_sol_2',
            element_list=response_json['photos'],
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

        assert_that(response_json['photos'],
                    described_as('Number of photos for ' + rover_name + '_earth_date_' + earth_date + ' should be ' + str(expected_amount_photos),
                                 has_length(equal_to(expected_amount_photos))))

    @pytest.mark.smoke
    def test_rover_photos_by_camera(self, get_mars_rover_photos_v1_api, expected_photos_by_camera):

        # Arrange
        rover_name = expected_photos_by_camera[0]['rover_name']
        sol = expected_photos_by_camera[0]['sol']
        camera = expected_photos_by_camera[0]['camera']

        # Act
        response_json = get_mars_rover_photos_v1_api.get_by_martian_sol(
            rover_name=rover_name, sol_number=sol, camera=camera, page_number=1)

        # Assert
        expected_photo_id_list = expected_photos_by_camera[1]
        expected_amount_photos = len(expected_photo_id_list)
        print_friendly_id      = rover_name + '_sol_' + str(sol) + '_camera_' + camera

        assert_that(response_json['photos'],
                    described_as('Number of photos for ' + print_friendly_id + ' should be ' + str(expected_amount_photos),
                                 has_length(equal_to(expected_amount_photos))))
        
        for photo in response_json['photos']:
            assert_that(photo['id'],
                    described_as('Unexpected photo for ' + print_friendly_id + ' Expected photo ids are ' + str(expected_photo_id_list),
                                is_in(expected_photo_id_list)))
            
    @pytest.mark.smoke
    def test_rover_latest_photos(self, get_mars_rover_photos_v1_api, rover_name):

        # Act
        response_json = get_mars_rover_photos_v1_api.get_latest(rover_name=rover_name, page_number=1)

        # Assert
        response_validators.assert_number_of_elements_valid_range(element_description='latest_photos_' + rover_name,
                                                                  element_list=response_json['latest_photos'],
                                                                  min_elements=1,
                                                                  max_elements=25)

    @staticmethod
    def check_photo_properties(photo_id, photo, test_context_settings, rover_name):

        response_validators.assert_int_property_valid_range(
            element_id=photo_id, element=photo, property_name='id')

        response_validators.assert_int_property_valid_range(
            element_id=photo_id, element=photo, property_name='sol')
        
        response_validators.assert_string_property_not_empty(
            element_id=photo_id, element=photo, property_name='img_src')
        
        assert_that(photo['img_src'], 
                    is_valid_url(url_start_list=test_context_settings['allowed_image_url_starts'], 
                                 url_ending_list=test_context_settings['allowed_image_url_endings']))

        response_validators.assert_string_property_not_empty(
            element_id=photo_id, element=photo, property_name='earth_date')

        response_validators.assert_property_present(
            element_id=photo_id, element=photo, property_name='camera')

        rc_helper.check_photo_camera(
            element_id=photo_id + '_camera', camera_name=photo['camera']['name'], rover_name=rover_name)

        response_validators.assert_property_present(
            element_id=photo_id, element=photo, property_name='rover')

    @staticmethod
    def get_photo_printable_id(photo, rover_name):
        return 'photo_id_' + str(photo['id']) + '_rover_' + rover_name
