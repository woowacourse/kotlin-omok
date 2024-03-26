package omock.model.rule

import omock.model.search.Direction
import omock.model.search.DirectionFirstClearResult
import omock.model.search.DirectionResult
import omock.model.search.Node
import omock.model.stone.Stone

interface LoadMapInterface {

    fun loadMap(stone: Stone): Map<Direction,DirectionResult>

    fun firstClearLoadMap(stone: Stone): Map<Direction,DirectionFirstClearResult>

    fun getCurrentNode(stone: Stone): Node
}
