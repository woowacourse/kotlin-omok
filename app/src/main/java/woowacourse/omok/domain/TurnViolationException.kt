package woowacourse.omok.domain

class TurnViolationException(currentPlayer: StoneType) : IllegalArgumentException(
    "${currentPlayer.name} 플레이어의 차례입니다. 다른 플레이어는 돌을 놓을 수 없습니다.",
)
