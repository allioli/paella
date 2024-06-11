
import requests
from base_api import BaseApi

# Based on NASA Open APIs described in https://api.nasa.gov/?ref=public_apis


class MarsRoverPhotosV1Api(BaseApi):

    def __init__(self, gateway_url, api_key=None):
        super().__init__(gateway_url + '/mars-photos/api/v1/rovers', api_key)

    # Example /mars-photos/api/v1/rovers/curiosity/photos?sol=1000
    def get_by_martian_sol(self, rover_name, sol_number, camera=None, page_number=None):

        url = self.base_url + '/' + rover_name + '/photos'

        query_params = {'sol': sol_number}

        if camera is not None:
            query_params['camera'] = camera

        if page_number is not None:
            query_params['page'] = page_number

        query_params_with_api_token = self.add_api_key_to_query_params(query_params)

        response = requests.request(
            'GET', url, headers=self.headers, params=query_params_with_api_token)

        assert (response.status_code == 200), "Expected HTTP 200, received HTTP " + \
            str(response.status_code) + " : " + response.text

        return response.json()['photos']

    # Example /mars-photos/api/v1/rovers/opportunity/photos?earth_date=2015-6-13
    def get_by_earth_date(self, rover_name, earth_date, camera=None, page_number=None):

        url = self.base_url + '/' + rover_name + '/photos'

        query_params = {'earth_date': earth_date}

        if camera is not None:
            query_params['camera'] = camera

        if page_number is not None:
            query_params['page'] = page_number

        query_params_with_api_token = self.add_api_key_to_query_params(query_params)

        response = requests.request(
            'GET', url, headers=self.headers, params=query_params_with_api_token)

        assert (response.status_code == 200), "Expected HTTP 200, received HTTP " + \
            str(response.status_code) + " : " + response.text

        return response.json()['photos']
