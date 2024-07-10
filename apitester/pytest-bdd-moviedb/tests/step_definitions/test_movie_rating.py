"""MovieDB API Movie rating feature tests."""

from pytest_bdd import (
    scenarios,
    given,
    then,
    when
)

from pytest_bdd import parsers

scenarios('movie_rating.feature')

@when(parsers.parse('I rate The GodFather with value {rating:.1f}'))
def step_when_rate_the_godfather(get_movies_v3_api, session_id, rating):
    """I rate The GodFather."""
    get_movies_v3_api.rate_movie(movie_id=238, session_id=session_id, rating=rating)


@then(parsers.parse('A new record should appear in My Rated Movies with rating {expected_rating:.1f}')) 
def step_then_new_record_rated_movies(test_context_settings, get_account_v3_api, expected_rating):
    """A new record should appear in My Rated Movies."""
    account_id = test_context_settings['tmbdb_account_id']
    results_json = get_account_v3_api.get_latest_rated_movies(account_id=account_id)

    assert(results_json['total_results'] > 0)
    assert(results_json['results'][0]['rating'] == expected_rating)

