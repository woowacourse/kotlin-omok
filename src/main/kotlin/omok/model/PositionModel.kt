package omok.model

import omok.domain.board.Column
import omok.domain.board.Position
import omok.domain.board.Row
import omok.domain.player.Black
import omok.domain.player.Stone

enum class PositionModel(val text: String) {
    A_FIFTEEN("┌"),
    INTERVAL_FIFTEEN("┬"),
    O_FIFTEEN("┐"),
    A_INTERVAL("├"),
    A_ONE("└"),
    INTERVAL_ONE("┴"),
    O_ONE("┘"),
    O_INTERVAL("┤"),
    INTERVAL_INTERVAL("┼")
}

fun Position.toPresentation(stone: Stone?): String {
    if (stone != null) {
        if (stone == Black) return "●"
        return "○"
    }
    if (this.row == Row.FIFTEEN) return this.column.rowFifteenToModel().text
    if (this.row == Row.ONE) return this.column.rowOneToModel().text
    if (this.column == Column.A) return this.row.columnAToModel().text
    if (this.column == Column.O) return this.row.columnOToModel().text
    return PositionModel.INTERVAL_INTERVAL.text
}

private fun Column.rowFifteenToModel(): PositionModel {
    if (this == Column.A) return PositionModel.A_FIFTEEN
    if (this == Column.O) return PositionModel.O_FIFTEEN
    return PositionModel.INTERVAL_FIFTEEN
}

private fun Column.rowOneToModel(): PositionModel {
    if (this == Column.A) return PositionModel.A_ONE
    if (this == Column.O) return PositionModel.O_ONE
    return PositionModel.INTERVAL_ONE
}

private fun Row.columnAToModel(): PositionModel {
    if (this == Row.ONE) return PositionModel.A_ONE
    if (this == Row.FIFTEEN) return PositionModel.A_FIFTEEN
    return PositionModel.A_INTERVAL
}

private fun Row.columnOToModel(): PositionModel {
    if (this == Row.ONE) return PositionModel.O_ONE
    if (this == Row.FIFTEEN) return PositionModel.O_FIFTEEN
    return PositionModel.O_INTERVAL
}
