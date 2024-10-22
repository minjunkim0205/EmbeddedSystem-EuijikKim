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

- ## 클린 설치 방법

    > os sd카드에 굽기  
    - SSH 세션


- ## ssh 세션 종료 방지
    > [Putty설정](https://blog.naver.com/duehd88/220984889791)
    
    ```txt
    1. LightDM 설정 파일 수정
    먼저 lightdm의 설정 파일을 편집합니다. 터미널에서 다음 명령어를 입력하세요:

    bash
    코드 복사
    sudo nano /etc/lightdm/lightdm.conf
    해당 파일에서 Seat:* 섹션을 찾거나 추가해야 합니다. 일반적으로 # Seat configuration과 관련된 주석 부분이 있을 텐데, 만약 없다면 해당 섹션을 새로 추가할 수 있습니다.

    아래의 내용을 찾아서, 만약 주석(;) 처리되어 있다면 주석을 제거하고 설정을 추가합니다:

    plaintext
    코드 복사
    [Seat:*]
    xserver-command=X -s 0 dpms
    이 설정은 X 서버가 화면 보호 모드와 절전 모드를 비활성화하는 데 사용됩니다. -s 0은 화면 보호를 비활성화하고, dpms는 디스플레이 파워 관리 신호(DPMS)를 해제하는 설정입니다.

    파일을 저장하고 종료합니다:

    nano에서 저장: Ctrl + X → Y → Enter
    2. Raspberry Pi 재부팅
    설정을 적용하려면 Raspberry Pi를 재부팅해야 합니다:

    bash
    코드 복사
    sudo reboot
    ```

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
