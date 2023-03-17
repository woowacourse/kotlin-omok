package domain.board

import domain.stone.Stone

// TODO: 인터페이스를 다시 abstract class 가 구현하고, 이를 다른 board 가 상속받는다면
// interface 의 이점이 사라지지 않을까요?
abstract class BasedBoard(protected val placedStones: List<Stone>) : Board {

    override fun getStones(): List<Stone> = placedStones.toList()

    override fun getLatestStone(): Stone? {
        return placedStones.lastOrNull()
    }
}
