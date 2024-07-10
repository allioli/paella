
import requests
from base_api import BaseApi

# Based on https://developer.themoviedb.org/reference/account-rated-movies


class AccountV3Api(BaseApi):

    def __init__(self, gateway_url, access_token):
        super().__init__(gateway_url + '/3/account', access_token)

    # Example 3/account/7954295/rated/movies?sort_by=created_at.desc
    def get_latest_rated_movies(self, account_id):

        url = self.base_url + '/' + str(account_id) + '/rated/movies'

        query_params = {'sort_by' : 'created_at.desc'}

        response = requests.request(
            'GET', url, headers=self.headers, params=query_params)

        assert (response.status_code == 200), "Expected HTTP 200, received HTTP " + \
            str(response.status_code) + " : " + response.text

        return response.json()
    
