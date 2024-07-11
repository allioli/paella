
def check_movies_sorted_by_attribute(movies_list, attribute_name):

    num_movies = len(movies_list)

    if (num_movies > 1):
        for index in range(1, num_movies):
            # Check that movie value for attribute_name is lower or equal than on the previous one
            rating_value = movies_list[index][attribute_name]
            previous_rating_value =  movies_list[index-1][attribute_name]
            assert(rating_value <= previous_rating_value, 'Movies not correctly sorted')
