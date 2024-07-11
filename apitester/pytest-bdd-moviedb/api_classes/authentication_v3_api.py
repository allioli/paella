
import requests
from base_api import BaseApi

# Based on https://developer.themoviedb.org/reference/authentication-create-guest-session


class AuthenticationV3Api(BaseApi):

    def __init__(self, gateway_url, access_token):
        super().__init__(gateway_url + '/3/authentication', access_token)

    # Example /3/authentication/guest_session/new
    def create_guest_session(self):

        url = self.base_url + '/guest_session/new'

        response = requests.request(
            'GET', url, headers=self.headers)

        self.check_response_status_code(response=response, expected_status_code=200)

        return response
    
