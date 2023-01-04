# S3 연동
일단 저는   
https://velog.io/@do-hoon/게시글-생성-파일-업로드SpringBoot-JPA-AWS-S3    
위의 블로그를 참고하였습니다.

## 1. build.gradle 설정
	implementation 'org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE'
	implementation "com.amazonaws:aws-java-sdk-s3"
    
위의 두가지를 설정해 주면 됩니다.

## 2. application.yml 설정
    cloud:
        aws:
            credentials:
                accessKey: SLKFJ       # AWS IAM AccessKey 적기
                secretKey: SDLKFJSLKDF   # AWS IAM SecretKey 적기
            s3:
                bucket: com.gleonroad.umcproject
            region:
                static: ap-northeast-2
                auto: false
            stack:
                auto: false


accessKey와 secretKey는 aws에서 설정한 값을 넣으면 됩니다.     
해당 값은 공개하면 안되니 github에는 올리지 않는게 좋다고 합니다.    
위에 적어놓은 값은 임의 값입니다. 
## 2-1. application.yml 추가 설정
~~~
spring:
  datasource:
    url: jdbc:h2:tcp://localhost/~/umc3project;DB_CLOSE_ON_EXIT=FALSE
    username: sa
    password:
    driver-class-name: org.h2.Driver
  servlet:
    multipart:
      max-file-size: 50MB
      max-request-size: 50MB

  #swagger? ?? ???
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher

  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        show_sql: true
        format_sql: true
        default_batch_fetch_size: 100

logging:
  level:
    org.hibernate.SQL: debug
    org.hibernate.type: trace
    com:
      amazonaws:
        util:
          EC2MetadataUtils: ERROR

cloud:
  aws:
    credentials:
      accessKey: SLDKFJ     # AWS IAM AccessKey 적기
      secretKey: SLDKFJSK   # AWS IAM SecretKey 적기
    s3:
      bucket: com.gleonroad.umcproject
    region:
      static: ap-northeast-2
      auto: false
    stack:
      auto: false
~~~
위에 servlet 설정과 logging 설정을 해줘야 합니다.

## 3. S3Uploader 생성

    S3Uploader.java 파일을 그대로 쓰시면 됩니다.
------------
저 같은 경우 하나의 forum 글에 다수의 이미지나 비디오가 들어갈 수 있어서  
forumImage라는 entity를 따로 만들었습니다.     
forum 과 forumImage 는 1:다 관계입니다.     

s3에 이미지를 업로드 하는 로직은     
forum글 생성시 이미지가 있으면 s3에 업로드 하면서 반환되는 이미지 url를 저장합니다.    
코드는 ForumController에서 createForum api에 있습니다. 

# s3 설정은 하나로 통일하면 될 것 같습니다.


