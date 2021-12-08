package tools.doc;

import java.util.HashMap;
import java.util.List;

public class AsciiDocGenerator {
    public static void main(String[] args) {
        HashMap<String, List<SectionType>> data = new HashMap<>();
        initData(data);

        StringBuilder sb = new StringBuilder();
        sb.append(TEMPLATE);
        data.forEach((key, value) -> {
            sb.append("\n\n")
                    .append("=== ").append(key);
            value.forEach(sectionType ->
                    sb.append("\n\n")
                    .append("==== ").append(sectionType.getTitle()).append('\n')
                    .append(String.format("include::{snippets}/docs/%s/%s.adoc[]\n", key, sectionType.getFilename())));
        });
        System.out.println(sb);
    }

    private static void initData(HashMap<String, List<SectionType>> data) {
        List<SectionType> addAccount = List.of(
                SectionType.CURL_REQUEST,
                SectionType.HTTP_REQUEST,
                SectionType.HTTP_RESPONSE,
                SectionType.REQUEST_BODY,
                SectionType.REQUEST_FIELDS,
                SectionType.RESPONSE_BODY,
                SectionType.RESPONSE_FIELDS
        );
        List<SectionType> editAccount = List.of(
                SectionType.CURL_REQUEST,
                SectionType.HTTP_REQUEST,
                SectionType.HTTP_RESPONSE,
                SectionType.PATH_PARAMETERS,
                SectionType.REQUEST_BODY,
                SectionType.RESPONSE_BODY,
                SectionType.RESPONSE_FIELDS
        );
        List<SectionType> getAccount = List.of(
                SectionType.CURL_REQUEST,
                SectionType.HTTP_REQUEST,
                SectionType.HTTP_RESPONSE,
                SectionType.PATH_PARAMETERS,
                SectionType.RESPONSE_BODY,
                SectionType.RESPONSE_FIELDS
        );

        data.put("add-account", addAccount);
        data.put("edit-account", editAccount);
        data.put("get-account", getAccount);
    }

    private static final String TEMPLATE = """
            = 골라밥 V2 Account API Document
            :doctype: book
            :icons: font
            :source-highlighter: highlightjs
            :toc: left
            :toclevels: 3
            :sectlinks:

            [[introduction]]
            == 소개

            Golabab V2 의 계정관리를 위한 Restful API 입니다.

            [[common]]
            == 공통 사항

            API PORT는 8080 입니다.

            [[account-api]]
            계정관리 기능을 수행하는 api 입니다.

            root path : api/v1/account

            == API 명세
            """;
}
