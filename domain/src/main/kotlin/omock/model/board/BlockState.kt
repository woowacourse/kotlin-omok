package omock.model.board

import omock.model.Stone

enum class BlockState {
    EMPTY,
    BLACK_STONE,
    WHITE_STONE,
    ;

    companion object {
        fun from(stone: Stone): BlockState =
            when (stone) {
                Stone.BLACK -> BLACK_STONE
                Stone.WHITE -> WHITE_STONE
            }
    }
}
