package omok

import omok.model.Board
import omok.model.state.Stone
import omok.model.turn.Turn

fun Board.makeStones(
    player: Turn,
    vararg stones: Stone,
) {
    stones.forEach { stone ->
        this.setStoneState(player, stone)
    }
}
