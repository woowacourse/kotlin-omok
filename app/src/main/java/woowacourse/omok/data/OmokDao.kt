package woowacourse.omok.data

import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.stone.OmokStone
import woowacourse.omok.domain.model.stone.StoneType

interface OmokDao {
    fun saveStone(
        position: Position,
        stoneType: StoneType,
    )

    fun loadStones(): List<OmokStone>

    fun saveGameFinished(isFinished: Boolean)

    fun isGameFinished(): Boolean

    fun clearGameData()
}
