import json


def assert_property_exists(element_id, element, property_name):
    assert (property_name in element), "Missing " + property_name + \
        " in element " + str(element_id) + " >> " + \
        json.dumps(element, indent=4)


def assert_string_property_not_empty(element_id, element, property_name):

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


def assert_number_of_elements(element_list_id, elements, min_elements, max_elements=None):

    number_of_elements = len(elements)
    assert (number_of_elements >= min_elements), "Not enough elements in " + element_list_id + \
        " :: Found " + str(number_of_elements) + \
        " Expected at least " + str(min_elements)

    if max_elements is not None:
        assert (max_elements >= number_of_elements), "Too many elements in " + element_list_id + \
            " :: Found " + str(number_of_elements) + \
            " Expected max " + str(max_elements)
