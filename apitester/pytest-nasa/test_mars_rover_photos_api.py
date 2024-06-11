# -*- coding: utf-8 -*-

import json
import sys
sys.path.append('api_classes')
from mars_rover_photos_v1_api import MarsRoverPhotosV1Api
from mars_rover_manifests_v1_api import MarsRoverManifestsV1Api

class TestMarsRoverPhotosAPI():

    def test_curiosity_photos_by_martian_sol(self, get_mars_rover_photos_v1_api):

        photos_json = get_mars_rover_photos_v1_api.get_by_martian_sol(
            rover_name='curiosity', sol_number=1000, page_number=1)

        assert (len(photos_json) == 25), "Unexpected number of curiosity photos received. Expected 25, found " + \
            str(len(photos_json)) + " >> " + json.dumps(photos_json, indent=4)

    def test_curiosity_photos_by_earth_date(self, get_mars_rover_photos_v1_api):

        photos_json = get_mars_rover_photos_v1_api.get_by_earth_date(
            rover_name='curiosity', earth_date='2014-12-12', page_number=1)

        assert (len(photos_json) == 8), "Unexpected number of curiosity photos received. Expected 8, found " + \
            str(len(photos_json)) + " >> " + json.dumps(photos_json, indent=4)

    def test_curiosity_mission_manifest(self, get_mars_rover_manivests_v1_api):

        photos_json = get_mars_rover_manivests_v1_api.get('curiosity')
