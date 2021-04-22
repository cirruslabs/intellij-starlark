# stub for Cirrus builtin https://github.com/cirruslabs/cirrus-cli/blob/master/STARLARK.md#builtins

class FileSystem:
    def __init__(self):
        pass

    def exists(self, path):
        # type: (str) -> bool
        pass

    def read(self, path):
        # type: (str) -> Optional[str]
        pass

    def readdir(self, path):
        # type: (str) -> List[str]
        pass


fs = FileSystem()

env = {}  # type: Mapping[str, str]


def changes_include(*patterns):
    # type (list[str]) -> bool
    pass


class HttpResponse:
    def __init__(self):
        self.url = ""  # type: str
        self.status_code = 0  # type: int
        self.headers = {}  # type: object
        self.encoding = ""  # type: str

    def body(self):
        # type: () -> str
        pass

    def json(self):
        # type: () -> object
        pass


class HttpClient:
    def __init__(self):
        pass

    def delete(self, url, params={}, headers={}, body="", form_body={}, json_body={}, auth=()):
        # type: (str, object, object, str, object, object, tuple) -> HttpResponse
        pass

    def get(self, url, params={}, headers={}, auth=()):
        # type: (str, object, object, tuple) -> HttpResponse
        pass

    def options(self, url, params={}, headers={}, body="", form_body={}, json_body={}, auth=()):
        # type: (str, object, object, str, object, object, tuple) -> HttpResponse
        pass

    def patch(self, url, params={}, headers={}, body="", form_body={}, json_body={}, auth=()):
        # type: (str, object, object, str, object, object, tuple) -> HttpResponse
        pass

    def post(self, url, params={}, headers={}, body="", form_body={}, json_body={}, auth=()):
        # type: (str, object, object, str, object, object, tuple) -> HttpResponse
        pass

    def put(self, url, params={}, headers={}, body="", form_body={}, json_body={}, auth=()):
        # type: (str, object, object, str, object, object, tuple) -> HttpResponse
        pass


http = HttpClient()


class Hash:
    def __init__(self):
        pass

    def md5(self, text):
        # type: (str) -> str
        pass

    def sha1(self, text):
        # type: (str) -> str
        pass

    def sha256(self, text):
        # type: (str) -> str
        pass


hash = Hash()


class Base64:
    def __init__(self):
        pass

    def decode(self, src, encoding="standard"):
        # type: (str, str) -> str
        pass

    def encode(self, src, encoding="standard"):
        # type: (str, str) -> str
        pass


base64 = Base64()


class Json:
    def __init__(self):
        pass

    def dumps(self, obj):
        # type: (object) -> str
        pass

    def loads(self, text):
        # type: (str) -> object
        pass


json = Json()


class Yaml:
    def __init__(self):
        pass

    def dumps(self, obj):
        # type: (object) -> str
        pass

    def loads(self, text):
        # type: (str) -> object
        pass


yaml = Yaml()


class RegEx:
    def __init__(self):
        pass

    def findall(self, pattern, text, flags=0):
        # type: (str, str, int) -> str
        pass

    def split(self, pattern, text, maxsplit=0, flags=0):
        # type: (str, str, int, int) -> List[str]
        pass

    def sub(self, pattern, repl, text, count=0, flags=0):
        # type: (str, str, str, int, int) -> str
        pass


re = RegEx()


class ZipInfo:
    def __init__(self):
        pass

    def read(self):
        # type: () -> str
        pass


class ZipFile:
    def __init__(self):
        pass

    def namelist(self):
        # type: () -> List[string]
        pass

    def open(self, filename):
        # type: (str) -> ZipInfo
        pass


class ZipFileFactory:
    def __init__(self):
        pass

    def ZipFile(self, data):
        # type: (any) -> ZipFile
        pass


zipfile = ZipFileFactory()
