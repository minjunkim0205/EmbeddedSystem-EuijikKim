# 💻 EmbeddedSystem-EuijikKim

## Preview

![Preview](./Preview.png)

## Introduction

> 2024년 2학기 임베디드시스템[01]

## Evaluation method

> 중간 30%  
> 기말 30%  
> 프로젝트 30%  
> 출석 10% 
>   
> 40% A 유지  


- 실습때 라즈베리파이 사용  
- 라즈베리파이 4 or 3 버전 배포  
- 이론, 실습 이 두가지의 수업은 연결성이 높지 않다  

## Note

- ## 원격 연결/종료

    ```cmd
    arp -a
    ```
    ```cmd
    ssh pi@0.0.0.0
    ```
    ```cmd
    sudo shutdown -h now
    ```
    [Gradle 설치하기](https://park-jongseok.github.io/languages/java/2019/11/01/installing-gradle.html)  
    [VSCode JAR 파일 만들기](https://coding-restaurant.tistory.com/535)
    ```cmd
    javac -cp ".;lib/pi4j-core.jar" -d build src/file_name.java
    ```

- ## Tip

    > IPv4 가 아닌 IPv6() 로 응답하게 변경되어서 기존의  
    > IPv4 ip로 접근하면 접속할수가 없음.  
    > [모니터 없이 세팅](https://bbogle2.tistory.com/entry/Raspberry-PI-%EB%AA%A8%EB%8B%88%ED%84%B0-%EC%97%86%EC%9D%B4-%EC%84%B8%ED%8C%85%ED%95%98%EA%B8%B0)  
    > [raspberrypi.local 에 대한 정보](https://www.2cpu.co.kr/QnA/833645)  
    > [mDNS로 raspberrypi.local 찾기](https://wikidocs.net/3279)  
    > 
