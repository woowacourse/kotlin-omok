package woowacourse.omok.domain

class PositionOccupiedException(position: Position) : IllegalArgumentException(
    "위치 (${position.x}, ${position.y})에 이미 돌이 존재 합니다.",
)
