
import requests
from base_api import BaseApi

# Based on https://developer.themoviedb.org/reference/movie-add-rating


class MoviesV3Api(BaseApi):

    def __init__(self, gateway_url, access_token):
        super().__init__(gateway_url + '/3/movie', access_token)

    # Example 3/movie/11/rating?guest_session_id=123223
    def rate_movie(self, movie_id, session_id, rating):

        url = self.base_url + '/' + str(movie_id) + '/rating'

        query_params = {'guest_session_id': session_id}

        payload = {'value': rating}

        response = requests.request(
            'POST', url, headers=self.headers, params=query_params, json=payload)

        assert (response.status_code == 201), "Expected HTTP 201, received HTTP " + \
            str(response.status_code) + " : " + response.text
        
    
    # Example 3/movie/11/rating
    def rate_movie_intent(self, movie_id, rating, session_id):

        url = self.base_url + '/' + str(movie_id) + '/rating'

        query_params = {'guest_session_id': session_id}

        payload = {'value': rating}

        response = requests.request(
            'POST', url, headers=self.headers, params=query_params, json=payload)

        return response
