
package com.myproject.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.myproject.dto.Yes24BookResponse;
import com.myproject.dto.Yes24BookResponse.BookItem;

@Service
public class Yes24BookService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;

    // 생성자
    // @Value: 설정 파일의 값을 주입 받는 어노테이션
    public Yes24BookService(
            RestTemplate restTemplate,
            @Value("${yes24.api.key}") String apiKey,
            @Value("${yes24.api.base-url}") String baseUrl) {

        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    // 도서 검색
    public List<BookItem> searchBooks(String query) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "YES24 API Key가 설정되지 않았습니다.");
        }

        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/goods/itemList")
                .queryParam("query", query)
                .queryParam("page", 1)
                .queryParam("pageSize", 10)
                .queryParam("detail", "Y")
                .build()
                .encode()
                .toUri();

        Yes24BookResponse response = request(uri);

        return response.data() == null
                || response.data().items() == null
                ? List.of()
                : response.data().items();
    }

    // YES24 상품 번호로 도서 상세 조회
    public BookItem getBookDetail(Long itemId) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "YES24 API Key가 설정되지 않았습니다.");
        }

        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/goods/itemDetail")
                .queryParam("searchType", "ItemId")
                .queryParam("query", itemId)
                .queryParam("detail", "Y")
                .build()
                .encode()
                .toUri();

        Yes24BookResponse response = request(uri);

        if (response.data() == null
                || response.data().items() == null
                || response.data().items().isEmpty()) {
            throw new IllegalStateException(
                    "해당 도서 정보를 찾을 수 없습니다.");
        }

        return response.data().items().get(0);
    }

    // 공통 HTTP 요청 처리
    private Yes24BookResponse request(URI uri) {
        HttpHeaders headers = new HttpHeaders();
        
        // api 키를 요청 헤더에 추가
        headers.set("X-Api-Key", apiKey);

        HttpEntity<Void> requestEntity =
                new HttpEntity<>(headers);

        try {
            ResponseEntity<Yes24BookResponse> result =
                    restTemplate.exchange(
                            uri,
                            HttpMethod.GET,
                            requestEntity,
                            Yes24BookResponse.class);

            Yes24BookResponse body = result.getBody();

            if (body == null || !body.success()) {
                String message = body == null
                        ? "YES24 API 응답이 비어 있습니다."
                        : body.message();

                throw new IllegalStateException(message);
            }

            return body;

        } catch (RestClientException e) {
            throw new IllegalStateException(
                    "YES24 API 호출에 실패했습니다.", e);
        }
    }
}
