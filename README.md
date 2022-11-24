
## 프로젝트 목표

- 대규모의 트래픽의 분산 처리와 캐싱을 통해 부하를 줄이는 기능을 구현하는 것이 목표입니다.
- 단순한 기능 구현뿐만 아니라 직접 구현해보며 동작 원리를 이해하는 것이 목표입니다.
- 객체 지향 원리를 적용하여 유지 보수가 용이한 코드를 작성하는 것이 목표입니다.
- 효율적인 성능 테스트를 위한 CI/CD 파이프라인을 구축하여 자동화하는 것이 목표입니다.

## 사용 기술 및 개발 환경

- Java11
- Maven
- Netty
- Google Cloud Platform(GCP)
- Cloud Build(GCP)
- k6로 대체될 예정
- IntelliJ

<br/>

## Git Branch 전략

Workflow는 `feature`, `main`, `release`, `hotfix` 4가지의 브랜치로 나누어 작업하고 있고, 모든 브랜치에 대해 Pull Request를 통한 코드 리뷰 완료 후 Merge를 하고
있습니다.

> **Keyword**
> - `feature` : 기능의 구현을 담당하는 브랜치
> - `main` : 개발된 내용을 배포하기 위한 브랜치
> - `release` : 최종적으로 배포되는 브랜치
> - `hotfix` : 배포된 소스에서 버그가 발생하면 생성되는 브랜치

<br/>

브랜치 전략은 다음과 같습니다.

1. 구현할 기능을 Origin 레포지토리의 `feautre/{기능명}` 브랜치에서 개발을 하고 Commit log를 작성합니다.
2. 작업이 완료되면 Upstream 레포지토리의 `main` 브랜치에 Pull Request를 합니다.
3. 계획했던 기능이 모두 완성되고 이상이 없으면, `main` 브랜치에서 `release` 브랜치를 생성합니다.
4. `release` 브랜치에서 버그가 발생하면 `hotfix` 브랜치를 생성하여 수정합니다. 오류 수정이 완료되면 `release` 브랜치와 `main` 브랜치에 각각 merge 합니다.
