## LoneLib of About
LoneLib은 마인크래프트 플러그인 개발에 사용되는 프레임워크입니다.<br>
LoneLib은 현재 **1.12 ~ 1.21.5**까지 지원합니다.<br>

## LoneLib of Comment
LoneLib의 일부 기능들은 테스트를 하지 않아서 작동이 되지 않을 수 있습니다.<br>

## LoneLib of Version
LoneLib은 현재 마인크래프트 버전으로 **1.12 ~ 1.21.5**까지 지원합니다.<br>
새로운 마인크래프트 버전들이 나오면 그에 맞춰 업데이트를 할 예정입니다.<br>

## LoneLib of Dependency
이 프레임워크를 기반으로 프로젝트를 구축하려면 `pom.xml`에 다음 참조 중 하나를 포함하기만 하면 됩니다:
```xml
<repository>
    <id>lone64 repository</id>
    <url>https://repo.repsy.io/mvn/lone64/everything/</url>
</repository>
```
이 프레임워크를 사용하려면 아래의 의존성을 등록해야 합니다:
```xml
<dependency>
    <groupId>dev.lone64</groupId>
    <artifactId>LoneLib</artifactId>
    <version>1.0.1</version>
</dependency>
```