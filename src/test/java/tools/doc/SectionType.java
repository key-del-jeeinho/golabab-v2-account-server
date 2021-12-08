package tools.doc;

public enum SectionType {
    CURL_REQUEST("CURL", "curl-request"),
    PATH_PARAMETERS("Path Parameters", "path-parameters"),

    HTTP_REQUEST("Request HTTP", "http-request"),
    REQUEST_BODY("Request Body", "request-body"),
    REQUEST_FIELDS("Request Fields", "request-fields"),

    HTTP_RESPONSE("Response HTTP", "http-response"),
    RESPONSE_BODY("Response Body", "response-body"),
    RESPONSE_FIELDS("Response Fields", "response-fields");

    private final String title;
    private final String filename;

    SectionType(String title, String filename) {
        this.title = title;
        this.filename = filename;
    }

    public String getTitle() {
        return title;
    }

    public String getFilename() {
        return filename;
    }
}
