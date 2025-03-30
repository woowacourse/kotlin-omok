package woowacourse.omok.model

import woowacourse.omok.model.rule.Rule

class Game(val board: Board, private val rule: Rule) {
    var lastStone: Stone? = null
        private set

    fun chooseTurn(): Color {
        return when (lastStone?.color) {
            Color.BLACK -> Color.WHITE
            Color.WHITE, null -> Color.BLACK
        }
    }

    fun play(newStone: Stone): MoveResult {
        return rule.checkMove(board, newStone).fold(
            onSuccess = { success ->
                lastStone = newStone
                board.add(newStone)
                success
            },
            onFailure = { failure -> failure },
        )
    }
}
