package omock

import woowacourse.omok.model.GameState
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.stone.Stone

fun Board.makeStones(
    player: Player,
    vararg coordinates: String,
) {
    coordinates.forEach { coordinate ->
        val stoneResult = Stone.from(
            Row(coordinate.substring(0, coordinate.length - 1)),
            Column(coordinate.substring(coordinate.length - 1)),
        ) as GameState.LoadStone.Success
        this.setStoneState(
            player = player,
            stone = stoneResult.stone,
        )
    }
}
