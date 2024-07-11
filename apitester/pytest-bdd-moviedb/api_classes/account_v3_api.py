
import requests
from base_api import BaseApi

# Based on https://developer.themoviedb.org/reference/account-rated-movies


class AccountV3Api(BaseApi):

    def __init__(self, gateway_url, access_token):
        super().__init__(gateway_url + '/3/account', access_token)

    # Example 3/account/7954295/rated/movies?sort_by=created_at.desc
    def get_latest_rated_movies(self, account_id, session_id=None, expected_status_code=None):

        url = self.base_url + '/' + str(account_id) + '/rated/movies'

        query_params = {'sort_by' : 'created_at.desc'}
        
        if session_id is not None:
            query_params['session_id'] = session_id

        response = requests.request(
            'GET', url, headers=self.headers, params=query_params)
 
        self.check_response_status_code(response=response, expected_status_code=expected_status_code)

        return response
    
