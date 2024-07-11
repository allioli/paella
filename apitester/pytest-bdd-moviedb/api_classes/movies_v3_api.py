
import requests
from base_api import BaseApi

# Based on https://developer.themoviedb.org/reference/movie-add-rating


class MoviesV3Api(BaseApi):

    def __init__(self, gateway_url, access_token):
        super().__init__(gateway_url + '/3/movie', access_token)

    # Example 3/movie/11/rating?guest_session_id=123223
    def rate_movie(self, movie_id, session_id, rating, expected_status_code=None):

        url = self.base_url + '/' + str(movie_id) + '/rating'

        query_params = {'guest_session_id': session_id}

        payload = {'value': rating}

        response = requests.request(
            'POST', url, headers=self.headers, params=query_params, json=payload)

        self.check_response_status_code(response=response, expected_status_code=expected_status_code)

        return response
