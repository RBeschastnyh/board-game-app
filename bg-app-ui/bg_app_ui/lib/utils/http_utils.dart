bool isHttpStatusServerError(int code) {
  return code >= 500 && code <= 599;
}

bool isHttpStatusError(int code) {
  return code >= 400 && code <= 499;
}

bool isOk(int code) {
  return code == 200;
}

bool isNotFound(int code) {
  return code == 404;
}

bool isSuccess(int code) {
  return code >= 200 && code <= 299;
}

