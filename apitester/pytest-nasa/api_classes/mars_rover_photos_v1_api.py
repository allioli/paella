
import requests
from base_api import BaseApi

# Based on NASA Open APIs described in https://api.nasa.gov/?ref=public_apis


class MarsRoverPhotosV1Api(BaseApi):

    def __init__(self, gateway_url, api_key=None):
        super().__init__(gateway_url + '/mars-photos/api/v1/rovers', api_key)

    def build_query_params(self, sol_number=None, earth_date=None, camera=None, page_number=None):
        query_params = {}

        if sol_number is not None:
            query_params['sol'] = sol_number

        if earth_date is not None:
            query_params['earth_date'] = earth_date

        if camera is not None:
            query_params['camera'] = camera

        if page_number is not None:
            query_params['page'] = page_number

        self.add_api_key_to_query_params(query_params)

        return query_params


    # Example /mars-photos/api/v1/rovers/curiosity/photos?sol=1000
    def get_by_martian_sol(self, rover_name, sol_number, camera=None, page_number=None):

        url = self.base_url + '/' + rover_name + '/photos'

        query_params = self.build_query_params(sol_number=sol_number, camera=camera, page_number=page_number)

        response = requests.request(
            'GET', url, headers=self.headers, params=query_params)

        assert (response.status_code == 200), "Expected HTTP 200, received HTTP " + \
            str(response.status_code) + " : " + response.text

        return response.json()

    # Example /mars-photos/api/v1/rovers/opportunity/photos?earth_date=2015-6-13
    def get_by_earth_date(self, rover_name, earth_date, camera=None, page_number=None):

        url = self.base_url + '/' + rover_name + '/photos'

        query_params = self.build_query_params(earth_date=earth_date, camera=camera, page_number=page_number)

        response = requests.request(
            'GET', url, headers=self.headers, params=query_params)

        assert (response.status_code == 200), "Expected HTTP 200, received HTTP " + \
            str(response.status_code) + " : " + response.text

        return response.json()
    
    # Example /mars-photos/api/v1/rovers/curiosity/latest_photos
    def get_latest(self, rover_name, page_number=None):

        url = self.base_url + '/' + rover_name + '/latest_photos'

        query_params = self.build_query_params(page_number=page_number)

        response = requests.request(
            'GET', url, headers=self.headers, params=query_params)

        assert (response.status_code == 200), "Expected HTTP 200, received HTTP " + \
            str(response.status_code) + " : " + response.text

        return response.json()
