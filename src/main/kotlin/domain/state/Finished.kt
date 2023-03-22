package domain.domain.state

import domain.Position
import domain.domain.Stones

abstract class Finished(stones: Stones) : State(stones) {
    override fun toNextState(position: Position): State {
        throw IllegalStateException("게임이 끝났습니다. 다음 상태가 없습니다.")
    }
}
