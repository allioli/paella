class BaseApi:

    headers = {
        'cache-control': 'no-cache',
        'accept': 'application/json',
        'Content-Type': 'application/json;charset=utf-8'
    }

    def __init__(self, base_url, access_token):

        self.base_url = base_url
        self.headers['Authorization'] = "Bearer " + access_token

    def check_response_status_code(self, response, expected_status_code=None):

        if expected_status_code is not None:
            assert (response.status_code == expected_status_code), "Expected HTTP " + str(expected_status_code) + ", received HTTP " + \
                str(response.status_code) + " : " + response.text
