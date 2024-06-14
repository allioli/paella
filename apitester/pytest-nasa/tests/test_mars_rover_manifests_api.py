# -*- coding: utf-8 -*-

from datetime import datetime
import response_validators
import rover_camera_helper as rc_helper


class TestMarsRoverManifestsAPI():

    def test_mission_manifest_basics(self, get_mars_rover_manivests_v1_api, rover_name):

        # Act
        curiosity_manifest = get_mars_rover_manivests_v1_api.get(rover_name)

        # Assert
        self.check_manifest_properties(curiosity_manifest)


    def test_mission_manifest_dates(self, get_mars_rover_manivests_v1_api, rover_name):

        # Act
        curiosity_manifest = get_mars_rover_manivests_v1_api.get(rover_name)

        # Assert
        self.check_manifest_dates(
            manifest=curiosity_manifest, expected_date_format=get_mars_rover_manivests_v1_api.earth_date_format)


    def test_mission_manifest_photos(self, get_mars_rover_manivests_v1_api, rover_name):

        # Act
        curiosity_manifest = get_mars_rover_manivests_v1_api.get(rover_name)

        # Assert
        actual_photo_count = 0

        for daily_photo_group in curiosity_manifest['photos']:

            daily_photo_group_id = 'manifest_daily_photo_group_sol_' + \
                str(daily_photo_group['sol'])

            self.check_daily_photo_group_properties(
                photo_group_id=daily_photo_group_id, photo_group=daily_photo_group)

            rc_helper.check_photo_cameras(
                element_id=daily_photo_group_id, element=daily_photo_group, rover_name=rover_name)
            
            actual_photo_count += daily_photo_group['total_photos']

        assert (actual_photo_count == curiosity_manifest['total_photos'])
            

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
    def check_manifest_properties(curiosity_manifest):
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
            element_list_id='photos', elements=curiosity_manifest['photos'], min_elements=1)

    @staticmethod
    def check_daily_photo_group_properties(photo_group_id, photo_group):
        response_validators.assert_string_property_not_empty(
            element_id=photo_group_id, element=photo_group, property_name='earth_date')

        response_validators.assert_int_property_valid_range(
            element_id=photo_group_id, element=photo_group, property_name='sol')

        response_validators.assert_int_property_valid_range(
            element_id=photo_group_id, element=photo_group, property_name='total_photos')

        response_validators.assert_number_of_elements(
            element_list_id='cameras', elements=photo_group['cameras'], min_elements=1)
