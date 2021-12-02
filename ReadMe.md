## Golabab-account-server

> `discord-client` 에서 받은 회원가입 요청과 회원조회 요청을 수행한다.
회원의 기본 정보 및 권한을 관리한다.
>

### 기능 명세

**계정 등록**

- `discord-client` 에서 받은 인증링크 송신 요청을 받아, 유저의 정보를 담은 인증링크를 생성하여 반환한다.
- `user`  가 인증링크로 접속시, 링크를 분석하여 회원가입 작업을 수행한다.

**계정 조회**

- `discord` 에서 계정 조회 요청을 받아 이를 수행한다.

**계정 권한 관리**

- `discord` 에서 계정 권한 부여/변경 요청을 받아 이를 수행한다.

### API 명세

> **account-api** | 계정 관리를 위한 여러 기능을 수행한다.
>

**PUT api/v1/account-api/account | 계정을 추가한다**

**GET api/v1/account-api/account/{id} | id 를 통해 계정을 조회한다**

**PATCH api/v1/account-api/account/{id} | id 를 통해 계정정보를 수정한다**

*주로 Permission 정보 변경을 위해 사용한다*

> **register-api** | 회원가입을 위한 여러 기능을 수행한다.
>

**POST api/v1/register-api/authorize-link | 인증링크를 생성하여 반환한다**

*만약 인증링크로 접속시, 회원가입을 진행한다*