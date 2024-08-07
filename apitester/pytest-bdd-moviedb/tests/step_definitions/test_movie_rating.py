"""MovieDB API Movie rating feature tests."""

from pytest_bdd import (
    scenarios,
    then,
    when
)

from pytest_bdd import parsers

scenarios('movie_rating.feature')

## WHEN steps ##

@when(parsers.parse('I rate The GodFather with value \"{rating:.1f}\"'))
def step_when_rate_the_godfather(get_movies_v3_api, guest_session_id, rating):
    """I rate The GodFather."""
    get_movies_v3_api.rate_movie(movie_id=238, session_id=guest_session_id, rating=rating, expected_status_code=201)

@when(parsers.parse('I try to rate a film with value \"{rating}\"'), target_fixture="response")
def step_when_rate_film_intent(get_movies_v3_api, guest_session_id, rating):
    """I rate a film intent."""
    return get_movies_v3_api.rate_movie(movie_id=238, session_id=guest_session_id, rating=rating)

@when('I try to list My Rated Movies as guest user', target_fixture="response")
def step_when_list_my_rated_movies_as_guest_user(test_context_settings, get_account_v3_api, guest_session_id):
    """I list My Rated Movies as guest user."""
    account_id = test_context_settings['TMDB_ACCOUNT_ID']
    return get_account_v3_api.get_latest_rated_movies(account_id=account_id, session_id=guest_session_id)

## THEN steps ##

@then(parsers.parse('A new record should appear in My Rated Movies with rating \"{expected_rating:.1f}\"')) 
def step_then_new_record_rated_movies(test_context_settings, get_account_v3_api, expected_rating):
    """A new record should appear in My Rated Movies."""
    account_id = test_context_settings['TMDB_ACCOUNT_ID']
    results_json = get_account_v3_api.get_latest_rated_movies(account_id=account_id, expected_status_code=200).json()

    assert(results_json['total_results'] > 0)
    assert(results_json['results'][0]['rating'] == expected_rating)
