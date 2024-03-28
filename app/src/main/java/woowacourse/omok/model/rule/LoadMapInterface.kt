package woowacourse.omok.model.rule

import woowacourse.omok.model.search.Direction
import woowacourse.omok.model.search.DirectionFirstClearResult
import woowacourse.omok.model.search.DirectionResult
import woowacourse.omok.model.search.Node
import woowacourse.omok.model.stone.Stone

interface LoadMapInterface {
    fun loadMap(stone: Stone): Map<Direction, DirectionResult>

    fun firstClearLoadMap(stone: Stone): Map<Direction, DirectionFirstClearResult>

    fun getCurrentNode(stone: Stone): Node
}
