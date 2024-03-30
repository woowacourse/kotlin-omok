package woowacourse.omok.util

import woowacourse.omok.R
import woowacourse.omok.db.OmokEntry
import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone
import woowacourse.omok.model.Position
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.rule.GeneralStonePlaceRule
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.model.state.BlackTurn
import woowacourse.omok.model.state.GameState
import woowacourse.omok.model.state.WhiteTurn

fun convertIndexToPosition(index: Int): Position {
    val x = index % 15 + 1
    val y = (224 - index) / 15 + 1
    return Position.of(x, y)
}

fun convertPositionToIndex(position: Position): Int {
    val x = position.x - 1
    val y = 14 - (position.y - 1)
    return y * 15 + x
}

fun mapStoneColorToDrawable(color: StoneColor): Int {
    return when (color) {
        StoneColor.BLACK -> R.drawable.black_stone
        StoneColor.WHITE -> R.drawable.white_stone
    }
}

fun mapStoneColorToString(color: StoneColor): String {
    return when (color) {
        StoneColor.BLACK -> "흑"
        StoneColor.WHITE -> "백"
    }
}

fun mapStringToStoneColor(colorString: String): StoneColor {
    return when (colorString) {
        "흑" -> StoneColor.BLACK
        "백" -> StoneColor.WHITE
        else -> throw IllegalArgumentException()
    }
}

fun mapEntriesToBoard(omokEntries: List<OmokEntry>): Board {
    return Board(
        omokEntries.map { dbItem ->
            OmokStone(
                position = Position.of(dbItem.x, dbItem.y),
                color = mapStringToStoneColor(dbItem.color),
            )
        }.associateBy(OmokStone::position),
    )
}

fun mapStoneColorToGameState(
    color: StoneColor,
    board: Board,
): GameState {
    return when (color) {
        StoneColor.BLACK -> WhiteTurn(GeneralStonePlaceRule, board)
        StoneColor.WHITE -> BlackTurn(RenjuRule, board)
    }
}
