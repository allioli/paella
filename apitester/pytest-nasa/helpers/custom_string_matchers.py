from hamcrest.core.base_matcher import BaseMatcher

class IsValidURL(BaseMatcher):

    def __init__(self, url_start_list, url_ending_list):
        self.url_start_list  = url_start_list
        self.url_ending_list = url_ending_list

    def _matches(self, item):
        # Guard check non-empty string item
        if (not isinstance(item, str)) or (len(item) == 0):
            return False
         
        matching_result = self.check_url_starts(item) and self.check_url_endings(item)

        return matching_result

    def describe_to(self, description):
        description.append_text('URL should start with one of ')    \
                   .append_text(str(self.url_start_list))
        
        description.append_text('. URL should end with one of ')    \
                   .append_text(str(self.url_ending_list))
        
    def check_url_starts(self, item):
        for url_start in self.url_start_list:
            if item.startswith(url_start):
                return True
        return False
    
    def check_url_endings(self, item):
        for url_ending in self.url_ending_list:
            if item.endswith(url_ending):
                return True
        return False


def is_valid_url(url_start_list, url_ending_list):
    return IsValidURL(url_start_list=url_start_list, url_ending_list=url_ending_list)