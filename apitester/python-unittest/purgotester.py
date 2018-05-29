import unittest
import requests
import string
from random import choices
import json

html_report_filename = 'test_matrix.html'

class TestPurgomalumAPI(unittest.TestCase):

    document_start = '<!DOCTYPE html><html><body><h1>Purgomalum API test matrix</h1>'
    table_start = '<table border="1"><tr><th>Test Case</th><th>Request</th><th>Expected Result</th><th>Actual Result</th></tr>'
    document_end = '</table></body></html>'

    @staticmethod
    def generate_random_string(string_length):
        return ''.join(choices(string.ascii_uppercase + string.digits + string.ascii_lowercase, k=string_length))

    @classmethod
    def write_to_report_file(cls, mode, html_content):

        """Write test report to file in html format

            Args:
                mode (int): File open mode
                html_content (str): String with HTML content to write to file
    
        """
        
        with open(html_report_filename, mode) as report_file:
            report_file.write(html_content)
 
    @classmethod
    def setUpClass(cls):
        cls.write_to_report_file("w", cls.document_start + cls.table_start)
         

    @classmethod
    def tearDownClass(cls):
        cls.write_to_report_file("a", cls.document_end)

    @classmethod
    def write_test_result(cls, test_name, request, expected_result, actual_result):

        """Write a test result report to file

            Args:
                test_name (str): Value for field 'Test Case' in report 
                request (str): Value for field 'Request' in report
                expected_result (str): Value for field 'Expected Result' in report
                actual_result (str): Value for field 'Actual Result' in report
    
        """

        html_string = '<tr><td>'+ test_name + '</td><td>' + request + '</td><td>' + expected_result + '</td><td>' + \
                      actual_result + '</td></tr>'

        
        cls.write_to_report_file("a", html_string)

    def perform_test_service_json(self, test_name, request, expected_response_json, assertion_error_message):
        
        """Perform a test on API service 'json'

            Args:
                test_name (str): Name of the test 
                request (str): Value of the HTTP request URL
                expected_response_json (dict): Expected response body in JSON format
                assertion_error_message (str): Message to be displayed if assertion fails
    
        """

        response = requests.get(request)

        response_json = response.json()

        self.write_test_result(test_name, request, json.dumps(expected_response_json), json.dumps(response_json)) 

        self.assertEqual(response.status_code, 200)

        self.assertEqual(response_json, expected_response_json, assertion_error_message)
        

    def perform_test_service_containsprofanity(self, test_name, request, expected_response, assertion_error_message):

        """Perform a test on API service 'containsprofanity'

            Args:
                test_name (str): Name of the test 
                request (str): Value of the HTTP request URL
                expected_response (dict): Expected response body in text format
                assertion_error_message (str): Message to be displayed if assertion fails
    
        """

        response = requests.get(request)

        self.write_test_result(test_name, request, expected_response, response.text) 

        self.assertEqual(response.status_code, 200)

        self.assertEqual(response.text, expected_response, assertion_error_message)
    

    def test_1_white_list_city_name(self):

        request = 'http://www.purgomalum.com/service/containsprofanity?text=Welcome to Scunthorpe!'
        expected_response_text = 'false'
        test_name = 'test_1_white_list_city_name'
        assertion_error_message = 'Scunthorpe is a legit city name and should be white listed'

        self.perform_test_service_containsprofanity(test_name, request, expected_response_text, assertion_error_message)
    
    def test_2_white_list(self):

        request = 'http://www.purgomalum.com/service/json?text=You are an embarrassment'
        expected_response_json = {'result': 'You are an embarrassment'}
        test_name = 'test_2_white_list'
        assertion_error_message = 'Embarrassment should be in white list'

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message) 

    def test_3_quotes_in_text(self):

        request = 'http://www.purgomalum.com/service/json?text=That’s good, he repeated and began to smile, I’m out!'
        expected_response_json = {'result': 'That’s good, he repeated and began to smile, I’m out!'}
        test_name = "test_3_quotes_in_text"
        assertion_error_message = "Quotes in original text replaced by special characters"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)

    def test_4_capitalisation_and_multiplicity_in_text(self):

        request = 'http://www.purgomalum.com/service/json?text=Is it Fuck or ffffffuuuuuuckkkkkkkkk... or fFUCK?'
        expected_response_json = {'result': 'Is it **** or ****... or ****?'}
        test_name = "test_4_capitalisation_and_multiplicity_in_text"
        assertion_error_message = "One or more offending words not replaced"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)


    def test_5_similar_chars_in_text(self):

        request = 'http://www.purgomalum.com/service/json?text=Hello $sssssshithe@d$$$$$, yes, you, $$hith3@dddd$'
        expected_response_json = {'result': 'Hello *********, yes, you, *********'}
        test_name = "test_5_similar_chars_in_text"
        assertion_error_message = "One or more offending words not replaced"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)

    def test_6_white_list_and_replacement(self):

        request = 'http://www.purgomalum.com/service/json?text=Good... he repeated and began to snigger, I am a bastard&fill_text=nigger'
        expected_response_json = {'result': 'Good... he repeated and began to snigger, I am a nigger'}
        test_name = "test_6_white_list_and_replacement"
        assertion_error_message = "Snigger should be white listed. Values for fill_text should also be filtered"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)

    def test_7_offending_word_between_spaces(self):

        request = 'http://www.purgomalum.com/service/json?text=Welcome class of 2018 to Dick Head'
        expected_response_json = {'result': 'Welcome class of 2018 to ********'}
        test_name = "test_7_offending_word_between_spaces"
        assertion_error_message = "Profanity word separated by spaces not identified"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)

    def test_8_add_profanitylist(self):

        request = 'http://www.purgomalum.com/service/json?text=Welcome class of 222.0.1.8888 to AssVille!&add=2018,ill'
        expected_response_json = {'result': 'Welcome class of **** to ***V***e!'}
        test_name = "test_8_add_profanitylist"
        assertion_error_message = "Added profanity word separated by dots not identified"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)

    def test_9_text_with_hash_profanity(self):

        request = 'http://www.purgomalum.com/service/containsprofanity?text=Right as f#ck'
        expected_response_text = 'false'
        test_name = "test_9_text_with_hash_profanity"
        assertion_error_message = "f#ck is not expected to be filtered from text"

        self.perform_test_service_containsprofanity(test_name, request, expected_response_text, assertion_error_message)

    def test_91_text_with_hash_filtering(self):

        request = 'http://www.purgomalum.com/service/json?text=Right as f#ck'
        expected_response_json = {'result': 'Right as f#ck'}
        test_name = "test_91_text_with_hash_filtering"
        assertion_error_message = "Missing characters from original text in result"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)

    def test_92_add_invalid_character(self):

        request = 'http://www.purgomalum.com/service/json?text=Who said clowns are creepy?&add=clowns,#'
        expected_response_json = {'error': 'Invalid Characters in User Black List'}
        test_name = "test_92_add_invalid_character"
        assertion_error_message = "Expected error because invalid character hash in add parameter"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)

    def test_93_profanity_list_hierarchy(self):

        request = 'http://www.purgomalum.com/service/json?text=the last time you look like a smart yass?&add=yass'
        expected_response_json = {'result': 'the last time you look like a smart y***?'}
        test_name = "test_93_profanity_list_hierarchy"
        assertion_error_message = "'ass' expected to be filtered from text before 'yass', since 'yass' was added by user"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)

    def test_94_add_max_characters(self):

        random_offending_word = self.generate_random_string(200);
        request = 'http://www.purgomalum.com/service/json?text=Super '+ random_offending_word +'&fill_text=!&add=' + random_offending_word
        expected_response_json = {'result': 'Super !'}
        test_name = "test_94_add_max_characters"
        assertion_error_message = "Offending word with size 200 not filtered correctly from text"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)
        

    def test_95_add_too_many_characters(self):

        random_offending_word = self.generate_random_string(201);
        request = 'http://www.purgomalum.com/service/json?text=Super '+ random_offending_word +'&fill_text=!&add=' + random_offending_word 
        expected_response_json = {'error': 'User Black List Exceeds Limit of 200 Characters.'}
        test_name = "test_95_add_too_many_characters"
        assertion_error_message = "Error message does not report limit of 200 characters for add parameter breached"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)

    def test_96_fill_text_invalid_characters(self):
        
        request = 'http://www.purgomalum.com/service/json?text=Shut your mug you bloody cunt&fill_text=%%%%%%%%%%%%%%%%%%%%'
        expected_response_json = {'error': 'Invalid User Replacement Text'}
        test_name = "test_96_fill_text_invalid_characters"
        assertion_error_message = "Error message does not report limit of 200 characters for add parameter exceeded"
        
        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)
         
    def test_97_fill_text_too_many_characters(self):
        
        request = 'http://www.purgomalum.com/service/json?text=Shut your mug you bloody cunt&fill_text={[(!|*|!)]=[(!|*|!)]}'
        expected_response_json = {'error': 'User Replacement Text Exceeds Limit of 20 Characters.'}
        test_name = "test_97_fill_text_too_many_characters"
        assertion_error_message = "Error message does not report limit of 20 characters"
        
        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)

    def test_98_fill_text_max_characters(self):
        
        request = 'http://www.purgomalum.com/service/json?text=Shut your mug you bloody cunt&fill_text={[(!|*|!)][(!|*|!)]}'
        expected_response_json = {'result': 'Shut your mug you bloody {[(!|*|!)][(!|*|!)]}'}
        test_name = "test_98_fill_text_max_characters"
        assertion_error_message =  "Unexpected error for fill_text parameter with correct value"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)

    def test_99_fill_char(self):
        
        request = 'http://www.purgomalum.com/service/json?text=Stay put&add=put&fill_char=-'
        expected_response_json = {'result': 'Stay ---'}
        test_name = "test_99_fill_char"
        assertion_error_message =  "Unexpected error for fill_text parameter with correct value"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)
        
    def test_991_fill_char_invalid_characters(self):
        
        request = 'http://www.purgomalum.com/service/json?text=Stay put&add=put&fill_char=@'
        expected_response_json = {'error': 'Invalid User Replacement Characters'}
        test_name = "test_991_fill_char_invalid_characters"
        assertion_error_message =  "Unexpected error for fill_text parameter with correct value"

        self.perform_test_service_json(test_name, request, expected_response_json, assertion_error_message)
    

    
if __name__ == '__main__':
    unittest.main()
