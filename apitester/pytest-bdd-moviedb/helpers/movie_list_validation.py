
def check_movies_sorted_by_attribute(movies_list, attribute_name):

    # List comprehension to extract only the requested attribute values
    values_list = [x[attribute_name] for x in movies_list]
 
    for index in range(1, len(values_list)):
        current_value = values_list[index]
        previous_value = values_list[index - 1]
        # Check that movie value for attribute_name is lower or equal than on the previous one
        assert(values_list[index] <= values_list[index - 1]), 'Movies not correctly sorted by ' + attribute_name + \
            ': ' + str(values_list)  
