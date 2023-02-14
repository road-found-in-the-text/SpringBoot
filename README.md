## ec2 사용한 무중단 배포

<aside>
📌 Amazon RDS로 MySQL 배포

</aside>

Amazon RDS를 사용해 클라우드에서 MySQL을 배포해 프로젝트 기간 동안에 작업을 진행했습니다.

Amazon RDS를 사용하면 비용 효율적이고 크기 조정 가능한 하드웨어 용량을 갖춘 확장 가능한 MySQL 서버를 몇 분 만에 배포할 수 있습니다.

- Java Mail Library: SMTP를 사용하여 메일을 전송하는 방법으로 신고 기능과 비밀번호 재발급 기능을 구현했습니다.
- Amazon S3: 저장 용량이 무한대이고 파일 저장에 최적화되어 있는 S3를 이미지 업로드를 위해 사용했습니다.

![image](https://user-images.githubusercontent.com/81344634/218734889-8d702a3b-7841-4e77-8358-48710769879c.png)


<aside>
📌 스프링 부트 애플리케이션을 AWS EC2에 도커를 통해 배포

</aside>

AWS EC2 인스턴스를 생성하고, 도커를 설치한 후 애플리케이션을 배포 합니다.

EC2의 private IP를 RDS의 인바운드규칙 소스에 추가해 RDS의 MySQL과 연동 상태를 유지합니다

![image](https://user-images.githubusercontent.com/81344634/218735003-5e35d2e9-4eb7-4625-8ff8-fd3389ed47a7.png)

## swagger 사용

swagger 사용하여 애플리케이션의 **RESTful API** 문서를 자동으로 생성하고 test case를 만들어서 사용하였습다. 이를 사용하여 application의 모든 엔드포인트를 살펴보고 test를 해보았습니다. 

```java
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.umc3_teamproject"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Web Application Api")
                .description("설명 부분")
                .version("1.0.0")
                .build();
    }
}
```

## Social Login 사용

<aside>
📌 Apple Login

</aside>

- token 포함 필요한 정보를 모두 받아 jwt로 만든 후 서버로 넘겨 DB에 저장한다.
- 필요한 정보만 payload 에 넣어서 jwt 를 다시 만들고 클라이언트에게 전달한다.
- reject 사유를 피하기 위해 꼭 필요한 기능이다.

<aside>
📌 KaKao Login

</aside>

- Client에서 access token을 받고 보안상 jwt로 만든 후 서버로 넘긴다
- token을 검증 후 token을 이용하여 사용자 정보를 얻는다.

## TDD ****(Test-Driven-Development)**** 방법론 사용

> 테스트 코드를 작성하며 아래와 같은 과정으로 개발을 진행했습니다.
> 
1. 테스트 코드 작성
2. 테스트 코드를 통과하는 구현 코드 작성
3. 테스트가 통과할 경우 리팩토링 (더욱 자세한 테스트, 통과할 수 있는 코드, 가시성 등)

> 이러한 개발 과정을 통해 다음과 같은 이점을 얻을 수 있었습니다
> 
1. 단기적인 뚜렷한 목표를 세울 수 있다.
2. 추가 구현의 용이함 - 새로운 기능들이 기존의 코드에 영향을 미치는지 알 수 있다.
3. 운영 단계가 아닌, 배포 단계에서 버그를 검출해 내어 불필요한 비용을 감축할 수 있을 것이다. (커뮤니케이션, 시간 등)
