# Spring Rest Docs 사용 정리
* build.gradle 참고해서 설정
* 테스트 클래스에 @ExtendWith(RestDocumentationExtension.class) 추가
* json 형태로의 변환을 위해 ObjectMapper가 필요

## 흐름 정리
1. 테스트 코드 작성 후 실행 (mockMvc는 따로 공부 필요)  

```
//save 이름으로 저장되고 코드처럼 요청 필드 값만 별도로 저장할 수 있음(응답 값도 가능)  
document("save",
 requestFields(
         fieldWithPath("title").description("제목"),
         fieldWithPath("content").description("내용")
 ))
```


2. build/generated-snippets에 .adoc 파일 생성됨  

3. src/docs/asciidoc에 .adoc 파일을 만들어 build/generated-snippets에 생성된 파일을 이용해 작성(include:: 입력 시 자동으로 경로가 뜸)  

4. bootJar 실행 시 resources/static/docs에 html 파일이 생성됨  

5. 애플리케이션 실행 후 http://localhost:8080/docs/save.html 접속하면 문서 확인 가능
