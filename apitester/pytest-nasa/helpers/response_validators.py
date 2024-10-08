
from hamcrest import assert_that, described_as, has_key, has_length, greater_than_or_equal_to, less_than_or_equal_to, all_of, greater_than


def assert_property_present(element_id, element, property_name):
    assert_that(element, described_as(element_id,
                                      has_key(property_name)))


def assert_string_property_not_empty(element_id, element, property_name):

    assert_property_present(element_id, element, property_name)

    assert_that(element[property_name],
                described_as(element_id + ' : ' + property_name,
                             has_length(greater_than(0))))


def assert_int_property_valid_range(element_id, element, property_name, min_value=0, max_value=None):

    assert_property_present(element_id, element, property_name)

    assert_that(element[property_name],
                described_as(element_id,
                             greater_than_or_equal_to(min_value)))

    if max_value is not None:
        assert_that(element[property_name],
                    described_as(element_id,
                                 less_than_or_equal_to(max_value)))


def assert_number_of_elements_valid_range(element_description, element_list, min_elements, max_elements):

    assert_that(element_list,
                described_as('Number of items for ' + element_description + ' in range [' + str(min_elements) + ' - ' + str(max_elements) + ']',
                             all_of(
                    has_length(greater_than_or_equal_to(min_elements)),
                    has_length(
                        less_than_or_equal_to(max_elements))
                )))
