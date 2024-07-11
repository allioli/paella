
import requests
from base_api import BaseApi

# Based on https://developer.themoviedb.org/reference/movie-top-rated-list


class MovieTopRatedListV3Api(BaseApi):

    def __init__(self, gateway_url, access_token):
        super().__init__(gateway_url + '/3/movie/top_rated', access_token)

    # Example 3/movie/top_rated?language=en-US&region=US&page=2"
    def get(self, language=None, region=None, page=None):

        url = self.base_url

        query_params = {}

        if language is not None:
            query_params['language'] = language

        if region is not None:
            query_params['region'] = region

        if page is not None:
            query_params['page'] = page

        response = requests.request(
            'GET', url, headers=self.headers, params=query_params)
 
        self.check_response_status_code(response=response, expected_status_code=200)

        return response
    
