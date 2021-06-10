import pickle


class Request(object):
    def __init__(self, method: str = None, payload: list = None):
        self.__method = method
        self.__payload = payload

    def serializar(self) -> bytes:
        return pickle.dumps({'method': self.__method, 'payload': self.__payload})

    def deserializar(self, res):
        return pickle.loads(res, encoding='utf-8')


class Response(object):
    def __init__(self, payload: list = None):
        self.__payload = payload

    def serializar(self) -> bytes:
        return pickle.dumps({'response': self.__payload})

    def deserializar(self, res):
        return pickle.loads(res, encoding='utf-8')
