package woowacourse.omok.data

import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.state.OmokState
import woowacourse.omok.domain.model.stone.StoneType

interface OmokDao {
    fun saveStone(
        position: Position,
        stoneType: StoneType,
    )

    fun loadStones(): List<Pair<Position, StoneType>>

    fun saveGameTurn(state: OmokState)

    fun loadGameTurn(): OmokState

    fun saveGameFinished(isFinished: Boolean)

    fun isGameFinished(): Boolean

    fun clearGameData()
}
