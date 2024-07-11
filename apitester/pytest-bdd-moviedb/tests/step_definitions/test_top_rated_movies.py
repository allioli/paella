"""MovieDB API Top Rated Movies feature tests."""

from pytest_bdd import (
    scenarios,
    then,
    when,
    given
)

from pytest_bdd import parsers

import movie_list_validation as movie_validation

scenarios('top_rated_movies.feature')

## GIVEN steps ##

@given(parsers.parse('I am a user in \"{region}\" region with language \"{language}\"'), target_fixture="user_params")
def step_when_rate_the_godfather(region, language):
    """I am a user in region and language."""
    return {'region': region, 'language': language}

## WHEN steps ##

@when('I check Top Rated Movie list', target_fixture="response")
def step_when_check_top_rated_movie_list(get_movie_top_rated_v3_api, user_params):
    """I check Top Rated Movie list."""
    return get_movie_top_rated_v3_api.get(language=user_params['language'], region=user_params['region'])

 ## THEN steps ##

@then('Movies should be sorted by vote_average in descending order')
def step_then_movies_sorted_by_vote_average(response):
    """Movies should be sorted by vote_average in descending order."""
    movie_validation.check_movies_sorted_by_attribute(
        movies_list=response.json()['results'], attribute_name='vote_average')
    

@then(parsers.parse('Top rated movie has property \"{attribute_name}\" like \"{expected_value}\"'))
def step_then_movie_properties(response, attribute_name, expected_value):
    """Top rated movie has expected attribute values."""
    top_rated_movie = response.json()['results'][0]
    assert(top_rated_movie[attribute_name] == expected_value)

