package com.myproject;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.myproject.entity.Book;
import com.myproject.repository.BookRepository;

// 스프링 빈으로 등록
@Component
// CommandLineRunner: 스프링부트 어플리케이션이 시작된 후 실행 할 작업 정의
public class DataInitializer implements CommandLineRunner {

	private final BookRepository bookRepository;

	public DataInitializer(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	// run(): 스프링부트 어플리케이션이 시작된 후 자동 호출되는 메소드
	@Override
	public void run(String... args) {

		
		 // 이미 책 데이터가 있다면 추가하지 않는다. 
		if (bookRepository.count() > 0) {
			 System.out.println("기존 책 데이터가 있어 더미 데이터 생성을 건너뜁니다."); 
			 return; 
		 }
		 

		// Book 더미 데이터 생성
		List<Book> books = List.of(
			    new Book("클린 코드", "로버트 C. 마틴", "COMPLETED",
			        LocalDate.parse("2026-01-05"), LocalDate.parse("2026-02-15")),

			    new Book("객체지향의 사실과 오해", "조영호", "READING",
			        LocalDate.parse("2026-10-01"), null),

			    new Book("스프링 부트와 AWS로 혼자 구현하는 웹 서비스", "이동욱", "PLANNED",
			        null, null),

			    new Book("자바의 정석", "남궁성", "READING",
			        LocalDate.parse("2026-09-15"), null),

			    new Book("토비의 스프링", "이일민", "PLANNED",
			        null, null),

			    new Book("이펙티브 자바", "조슈아 블로크", "COMPLETED",
			        LocalDate.parse("2026-03-10"), LocalDate.parse("2026-04-20")),

			    new Book("모던 자바 인 액션", "라울-가브리엘 우르마", "READING",
			        LocalDate.parse("2026-10-10"), null),

			    new Book("리팩터링", "마틴 파울러", "PLANNED",
			        null, null),

			    new Book("HTTP 완벽 가이드", "데이비드 고울리", "COMPLETED",
			        LocalDate.parse("2025-11-01"), LocalDate.parse("2025-12-15")),

			    new Book("도메인 주도 설계", "에릭 에반스", "PLANNED",
			        null, null),

			    new Book("데이터 중심 애플리케이션 설계", "마틴 클레프만", "READING",
			        LocalDate.parse("2026-09-20"), null),

			    new Book("함께 자라기", "김창준", "COMPLETED",
			        LocalDate.parse("2026-04-01"), LocalDate.parse("2026-04-15")),

			    new Book("실용주의 프로그래머", "데이비드 토머스", "PLANNED",
			        null, null),

			    new Book("이것이 자바다", "신용권", "READING",
			        LocalDate.parse("2026-10-05"), null),

			    new Book("오브젝트", "조영호", "COMPLETED",
			        LocalDate.parse("2026-05-10"), LocalDate.parse("2026-06-05"))
			);
		
		// 데이터베이스에 더미 데이터 저장
		bookRepository.saveAll(books);

		System.out.println("테스트용 책 데이터 " + books.size() + "개를 생성했습니다.");
		
	}
}