package omok.model.board

import omok.model.Position

@JvmInline
value class Boxes(val boxes: Map<Position, BoxState>) {
    constructor(size: Int) : this(initBoxes(size))

    companion object {
        private const val MIN_BOXES_SIZE = 1

        @JvmStatic
        private fun initBoxes(size: Int): Map<Position, BoxState> {
            return (MIN_BOXES_SIZE..size)
                .let(Position::createPositions)
                .associateWith { BoxState.EMPTY }
        }
    }
}
