package woowacourse.omok.data

import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.stone.StoneType

interface OmokDao {
    fun saveStone(
        position: Position,
        stoneType: StoneType,
    )

    fun loadStones(): List<Pair<Position, StoneType>>

    fun saveGameFinished(isFinished: Boolean)

    fun isGameFinished(): Boolean

    fun clearGameData()
}
