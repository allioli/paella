
import requests
from behave import given, when, then
from hamcrest import equal_to, assert_that


containsprofanity_method_url = 'http://www.purgomalum.com/service/containsprofanity'
plain_method_url = 'http://www.purgomalum.com/service/plain'


def compose_purgomalum_request_url(context):

   url = plain_method_url + '?text=' + context.request_params['text']

   if('add' in context.request_params):
      url += ( '&add=' + context.request_params['add'] )

   if('fill_text' in context.request_params):
      url += ( '&fill_text=' + context.request_params['fill_text'] )

   if('fill_char' in context.request_params):
      url += ( '&fill_char=' + context.request_params['fill_char'] )

   return url


@given('I create purgomalum query with "{text}"')
def step_given_I_create_purgomalum_query_with_text(context, text):
   
   context.request_params = {'text': text}

@given('I add parameter "{parameter_name}" with value "{parameter_value}"')
def step_given_I_add_parameter_with_value(context, parameter_name, parameter_value):
   
   context.request_params[parameter_name] = parameter_value

@when('I perform request to purgomalum')
def step_I_perform_purgomalum_request(context):

   request_url = compose_purgomalum_request_url(context)
   context.response = requests.get(request_url)

@when('I query containsprofanity with "{text}"')
def step_when_I_query_containsprofanity_with_text(context, text):

   request_url = containsprofanity_method_url + '?text=' + text
   context.response = requests.get(request_url)


@then('the request succeeds')
def step_then_request_succeeds(context):

   assert_that(context.response.status_code, equal_to(200))


@then('web service returns "{expected_result}" because "{error_explanation}"')
def step_then_web_service_returns(context, expected_result, error_explanation):

   assert_that(context.response.text, equal_to(expected_result), error_explanation)

