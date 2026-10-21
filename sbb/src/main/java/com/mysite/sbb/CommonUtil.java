package com.mysite.sbb;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

// @Component: 스프링 컨테이너에 의해 관리되는 스프링 빈으로 등록
@Component
public class CommonUtil {
	// 입력한 마크다운 문자열을 Html로 변환하는 메소드
    public String markdown(String markdown) {
    	// 마크다운 글자를 읽는 도구 객체
        Parser parser = Parser.builder().build();
        
        // 마크다운 결과를 담는 변수
        Node document = parser.parse(markdown);
        
        // Html로 변환하는 도구
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        
        // Html로 변환해서 출력
        return renderer.render(document);
    }
}
