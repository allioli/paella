class BaseApi:

    headers = {
        'cache-control': 'no-cache',
        'accept'       : 'application/json',
        'Content-Type' : 'application/json;charset=utf-8'
    }

    def __init__(self, base_url, access_token):

        self.base_url                 = base_url
        self.headers['Authorization'] = "Bearer " + access_token

