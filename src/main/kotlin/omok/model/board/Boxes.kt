package omok.model.board

import omok.model.OmokStone
import omok.model.Position

@JvmInline
value class Boxes(val boxes: Map<Position, OmokStone> = emptyMap()) : Map<Position, OmokStone> by boxes {
    constructor(vararg omokStones: OmokStone) : this(createBoxes(*omokStones))

    constructor(omokStones: List<OmokStone>) : this(createBoxes(*omokStones.toTypedArray()))

    operator fun plus(stone: OmokStone): Boxes {
        return Boxes(boxes + (stone.position to stone))
    }

    companion object {
        private fun createBoxes(vararg omokStones: OmokStone): Map<Position, OmokStone> {
            if (omokStones.isEmpty()) return emptyMap()
            return omokStones.associateBy { it.position }
        }
    }
}
