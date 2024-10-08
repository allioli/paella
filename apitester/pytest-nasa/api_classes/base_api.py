class BaseApi:

    default_api_key = 'DEMO_KEY'

    headers = {
        'cache-control': 'no-cache',
        'Content-Type': 'application/json'
    }

    def __init__(self, base_url, dev_api_key=None):

        self.base_url = base_url

        if dev_api_key is None:
            self.api_key = BaseApi.default_api_key
        else:
            self.api_key = dev_api_key

    # Best practice is to include api_key in request header, but NASA open apis enforce passing it in the query params
    def add_api_key_to_query_params(self, query_params=None):
        if query_params is None:
            query_params = {}

        query_params['api_key'] = self.api_key
        
        return query_params
