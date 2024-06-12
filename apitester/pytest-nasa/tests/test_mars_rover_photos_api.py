# -*- coding: utf-8 -*-

from mars_rover_photos_v1_api import MarsRoverPhotosV1Api
from datetime import datetime
import response_validators


class TestMarsRoverPhotosAPI():

    def test_curiosity_photos_by_martian_sol(self, get_mars_rover_photos_v1_api):

        # Act
        photos_json = get_mars_rover_photos_v1_api.get_by_martian_sol(
            rover_name='curiosity', sol_number=1000, page_number=1)

        # Assert
        assert (len(photos_json) ==
                25), "Unexpected number of curiosity photos received. Expected 25, found " + str(len(photos_json))

    def spirit_photos_default_api_key(self, test_context_settings):

        # Arrange
        # Create api object with default api key
        custom_mars_rover_photos_v1_api = MarsRoverPhotosV1Api(
            test_context_settings['gateway_url'])

        # Act
        photos_json = custom_mars_rover_photos_v1_api.get_by_martian_sol(
            rover_name='spirit', sol_number=1000, page_number=1)

        # Assert
        assert (len(photos_json) == 6), "Unexpected number of spirit photos received. Expected 6, found " + \
            str(len(photos_json)) + " >> " + json.dumps(photos_json, indent=4)

    def test_curiosity_photos_by_earth_date(self, get_mars_rover_photos_v1_api):

        # Act
        photos_json = get_mars_rover_photos_v1_api.get_by_earth_date(
            rover_name='curiosity', earth_date='2014-12-12', page_number=1)

        # Assert
        assert (len(photos_json) == 8), "Unexpected number of curiosity photos received. Expected 8, found " + \
            str(len(photos_json)) + " >> " + json.dumps(photos_json, indent=4)

    def test_curiosity_mission_manifest(self, get_mars_rover_manivests_v1_api):

        # Act
        curiosity_manifest = get_mars_rover_manivests_v1_api.get('curiosity')

        # Assert
        self.check_mission_manifest_basics(curiosity_manifest)
        self.check_manifest_dates(
            manifest=curiosity_manifest, expected_date_format=get_mars_rover_manivests_v1_api.earth_date_format)

    @staticmethod
    def check_manifest_dates(manifest, expected_date_format):

        landing_date = datetime.strptime(
            manifest['landing_date'], expected_date_format)

        launch_date = datetime.strptime(
            manifest['launch_date'], expected_date_format)

        max_date = datetime.strptime(
            manifest['max_date'], expected_date_format)

        assert (launch_date < landing_date)
        assert (landing_date < max_date)

    @staticmethod
    def check_mission_manifest_basics(curiosity_manifest):
        response_validators.assert_string_property_not_empty(
            element_id='curiosity_manifest', element=curiosity_manifest, property_name='name')

        response_validators.assert_string_property_not_empty(
            element_id='curiosity_manifest', element=curiosity_manifest, property_name='landing_date')

        response_validators.assert_string_property_not_empty(
            element_id='curiosity_manifest', element=curiosity_manifest, property_name='launch_date')

        response_validators.assert_string_property_not_empty(
            element_id='curiosity_manifest', element=curiosity_manifest, property_name='status')

        response_validators.assert_int_property_valid_range(
            element_id='curiosity_manifest', element=curiosity_manifest, property_name='max_sol')

        response_validators.assert_string_property_not_empty(
            element_id='curiosity_manifest', element=curiosity_manifest, property_name='max_date')

        response_validators.assert_int_property_valid_range(
            element_id='curiosity_manifest', element=curiosity_manifest, property_name='total_photos')

        response_validators.assert_number_of_elements(
            element_list_id='photos', elements=curiosity_manifest['photos'], min_elements=0)
