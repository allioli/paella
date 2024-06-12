
import requests
from base_api import BaseApi

# Based on NASA Open APIs described in https://api.nasa.gov/?ref=public_apis


class MarsRoverManifestsV1Api(BaseApi):

    earth_date_format = "%Y-%m-%d"

    def __init__(self, gateway_url, api_key=None):
        super().__init__(gateway_url + '/mars-photos/api/v1/manifests', api_key)

    # Example /mars-photos/api/v1/manifests/curiosity
    def get(self, rover_name):

        url = self.base_url + '/' + rover_name

        query_params_with_api_token = self.add_api_key_to_query_params()

        response = requests.request(
            'GET', url, headers=self.headers, params=query_params_with_api_token)

        assert (response.status_code == 200), "Expected HTTP 200, received HTTP " + \
            str(response.status_code) + " : " + response.text

        return response.json()['photo_manifest']
    
