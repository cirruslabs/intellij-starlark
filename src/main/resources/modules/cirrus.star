# stub for Cirrus builtins https://github.com/cirruslabs/cirrus-cli/blob/master/STARLARK.md#builtins


class FileSystem:
    def __init__(self):
        pass

    def exists(self, path):
        """
        :type path: str
        :rtype bool
        """
        pass

    def read(self, path):
        """
        :type path: str
        :rtype str, NoneType
        """
        pass

    def readdir(self, path):
        """
        :type path: str
        :rtype list[str]
        """
        pass


fs = FileSystem()

env: dict = {}


def changes_include(*args):
    """
    :rtype bool
    """
    pass


class HttpResponse:
    url: str
    status_code: int
    headers: object
    encoding: str

    def __init__(self):
        pass

    def body(self):
        """
        :rtype: str
        """
        pass

    def json(self):
        """
        :rtype: object
        """
        pass


class HttpClient:
    def __init__(self):
        pass

    def delete(self, url, params={}, headers={}, body="", form_body={}, json_body={}, auth=()):
        """
        :type url: str
        :type params: object
        :type headers: object
        :type body: str
        :type form_body: object
        :type json_body: object
        :type auth: tuple
        :rtype HttpResponse
        """
        pass

    def get(self, url, params={}, headers={}, auth=()):
        """
        :type url: str
        :type params: object
        :type headers: object
        :type auth: tuple
        :rtype HttpResponse
        """
        pass

    def options(self, url, params={}, headers={}, body="", form_body={}, json_body={}, auth=()):
        """
        :type url: str
        :type params: object
        :type headers: object
        :type body: str
        :type form_body: object
        :type json_body: object
        :type auth: tuple
        :rtype HttpResponse
        """
        pass

    def patch(self, url, params={}, headers={}, body="", form_body={}, json_body={}, auth=()):
        """
        :type url: str
        :type params: object
        :type headers: object
        :type body: str
        :type form_body: object
        :type json_body: object
        :type auth: tuple
        :rtype HttpResponse
        """
        pass

    def post(self, url, params={}, headers={}, body="", form_body={}, json_body={}, auth=()):
        """
        :type url: str
        :type params: object
        :type headers: object
        :type body: str
        :type form_body: object
        :type json_body: object
        :type auth: tuple
        :rtype HttpResponse
        """
        pass

    def put(self, url, params={}, headers={}, body="", form_body={}, json_body={}, auth=()):
        """
        :type url: str
        :type params: object
        :type headers: object
        :type body: str
        :type form_body: object
        :type json_body: object
        :type auth: tuple
        :rtype HttpResponse
        """
        pass


http = HttpClient()


class Hash:
    def __init__(self):
        pass

    def md5(self, text):
        """
        :type text: str
        :rtype str
        """
        pass

    def sha1(self, text):
        """
        :type text: str
        :rtype str
        """
        pass

    def sha256(self, text):
        """
        :type text: str
        :rtype str
        """
        pass


hash = Hash()


class Base64:
    def __init__(self):
        pass

    def decode(self, src, encoding="standard"):
        """
        :type src: str
        :type encoding: str
        :rtype str
        """
        pass

    def encode(self, src, encoding="standard"):
        """
        :type src: str
        :type encoding: str
        :rtype str
        """
        pass


base64 = Base64()


class Json:
    def __init__(self):
        pass

    def dumps(self, obj):
        """
        :type obj: object
        :rtype str
        """
        pass

    def loads(self, text):
        """
        :type text: str
        :rtype object
        """
        pass


json = Json()


class Yaml:
    def __init__(self):
        pass

    def dumps(self, obj):
        """
        :type obj: object
        :rtype str
        """
        pass

    def loads(self, text):
        """
        :type text: str
        :rtype object
        """
        pass


yaml = Yaml()


class RegEx:
    def __init__(self):
        pass

    def findall(self, pattern, text, flags=0):
        """
        :type pattern: str
        :type text: str
        :type flags: int
        :rtype str
        """
        pass

    def split(self, pattern, text, maxsplit=0, flags=0):
        """
        :type pattern: str
        :type text: str
        :type maxsplit: int
        :type flags: int
        :rtype list[str]
        """
        pass

    def sub(self, pattern, repl, text, count=0, flags=0):
        """
        :type pattern: str
        :type text: str
        :type repl: str
        :type count: int
        :type flags: int
        :rtype str
        """
        pass


re = RegEx()


class ZipInfo:
    def __init__(self):
        pass

    def read(self):
        """
        :rtype: str
        """
        pass


class ZipFile:
    def __init__(self):
        pass

    def namelist(self):
        """
        :rtype: list[str]
        """
        pass

    def open(self, filename):
        """
        :param filename: str
        :rtype: ZipInfo
        """
        pass


class ZipFileFactory:
    def __init__(self):
        pass

    def ZipFile(self, data):
        """
        :rtype: ZipInfo
        """
        pass


zipfile = ZipFileFactory()


# Types for Cirrus CI hooks

class CirrusHookRepositoryPayload:
    id: int
    owner: str
    name: str
    isPrivate: bool

    def __init__(self):
        pass


class CirrusHookBuildPayload:
    id: int
    branch: str
    pullRequest: int
    changeIdInRepo: str
    changeTimestamp: int
    status: str

    def __init__(self):
        pass


class CirrusHookTaskPayload:
    id: int
    name: str
    status: str
    statusTimestamp: int
    creationTimestamp: int
    uniqueLabels: list[str]
    automaticReRun: bool
    automaticallyReRunnable: bool

    def __init__(self):
        pass


class CirrusHookPayloadData:
    repository: CirrusHookRepositoryPayload
    build: CirrusHookBuildPayload
    task: CirrusHookTaskPayload

    def __init__(self):
        pass


class CirrusHookPayload:
    action: str
    data: CirrusHookPayloadData

    def __init__(self):
        pass


class CirrusHookContext:
    payload: CirrusHookPayload

    def __init__(self):
        pass
