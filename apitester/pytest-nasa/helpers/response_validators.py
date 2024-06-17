import json


def assert_property_exists(element_id, element, property_name):
    assert (property_name in element), "Missing " + property_name + \
        " in element " + str(element_id) + " >> " + \
        json.dumps(element, indent=4)

def assert_property_not_empty(element_id, element, property_name):

    assert_property_exists(element_id, element, property_name)

    assert (element[property_name] is not None and len(element[property_name]) > 0), "Empty " + \
        property_name + " in element " + \
        str(element_id) + " >> " + json.dumps(element, indent=4)

def assert_int_property_valid_range(element_id, element, property_name, min_value=0, max_value=None):

    assert_property_exists(element_id, element, property_name)

    assert (element[property_name] is not None and element[property_name] >= min_value), property_name + \
        " with value " + str(min_value) + " in element " + str(element_id)

    if max_value is not None:
        assert (element[property_name] <= max_value), property_name + \
            " with value " + str(min_value) + " in element " + str(element_id)

def assert_number_of_elements(element_list_id, elements, expected_size):

    number_of_elements = len(elements)
    assert (number_of_elements == expected_size), "Incorrect amount of elements in " + element_list_id + \
        " :: Found " + str(number_of_elements) + \
        " Expected " + str(expected_size)


def assert_number_of_elements_valid_range(element_list_id, elements, min_elements, max_elements=None):

    number_of_elements = len(elements)
    assert (number_of_elements >= min_elements), "Not enough elements in " + element_list_id + \
        " :: Found " + str(number_of_elements) + \
        " Expected at least " + str(min_elements)

    if max_elements is not None:
        assert (max_elements >= number_of_elements), "Too many elements in " + element_list_id + \
            " :: Found " + str(number_of_elements) + \
            " Expected max " + str(max_elements)

def assert_url(element_id, element, property_name, expected_url_start_list=None, expected_url_ending_list=None):

        assert_property_not_empty(element_id=element_id, element=element, property_name=property_name)

        url = element[property_name]

        if expected_url_start_list is not None:
            assert(check_url_starts_with(url, expected_url_start_list)), "Missing expected url start in element " + str(element_id) + ". Expected one of " + str(expected_url_start_list) + " but found [" + url + "]"

        if expected_url_ending_list is not None:
            assert(check_url_ends_with(url, expected_url_ending_list)), "Missing expected url ending in element " + str(element_id) + ". Expected one of " + str(expected_url_ending_list) + " but found [" + url + "]"


def check_url_ends_with(url, expected_url_ending_list):
        for expected_url_ending in expected_url_ending_list:
            if url.endswith(expected_url_ending):
                return True
        return False

def check_url_starts_with(url, expected_url_start_list):
        for expected_url_start in expected_url_start_list:
            if url.startswith(expected_url_start):
                return True
        return False