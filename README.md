# kotlin-omok

### 오목 게임

- [x] 바둑돌을 놓을 위치를 `입력`받는다.
    - [x] 바둑판의 범위를 벗어나선 안된다.
    - [x] 이미 돌이 놓인 위치에는 돌을 놓을 수 없다.
    - [x] 입력 형태가 $알파벳$숫자 형태여야한다.
- 성공적으로 수를 놓았다면
    - [x] 승리 여부를 판단한다.
    - [x] 차례가 바뀐다.
- [x] 바둑판을 `출력`한다.
- [x] 6목 이상의 장목인 경우도 승리한다.
- [x] 마지막 놓인 돌의 위치를 가져온다.
- [x] 누구의 차례인지 `출력`한다.
- [x] 마지막 돌의 위치를 `출력`한다.
    - [x] 첫 돌인 경우 마지막 돌의 위치를 `출력`하지 않는다.
- [ ] sqlite database에 돌의 위치와 색을 저장한다.
- [ ] 앱을 시작할 때 database에서 데이터를 불러온다.
- [ ] 불러온 데이터로 초기 세팅을 한다.
  - [ ] 돌들을 다시 출력한다.
  - [ ] 마지막으로 둔 돌을 업데이트한다.
- [ ] 게임을 재시작한다.

### CRC

- 바둑판
    - 바둑판을 생성한다.
    - 바둑판의 크기를 알고있다.
    - 바둑판 위에 수를 둔다.
    - 마지막으로 둔 돌의 위치를 알 수 있다.

- 심판
    - 완성된 5줄이 있는가 확인한다.

- 바둑돌
    - 자신의 차례(흑, 백)와 자신의 위치를 알고있다.

- 위치
    - x축인 알파벳과 y축인 숫자를 좌표로 가진다.

- 차례
    - 차례 종류를 가지고있다.
    - 다음 차례를 구할 수 있다.

- 게임
    - 바둑판을 생성한다.
    - 입력받은 위치에 돌을 놓을 수 있는지 심판이 확인한다.
    - 심판 확인 후 가능하면 수를 둔다.
    - 승리하는 사람이 생길 때까지 차례로 위 과정을 반복한다.
