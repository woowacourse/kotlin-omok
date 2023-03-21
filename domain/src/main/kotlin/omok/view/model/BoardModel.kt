package omok.view.model

import omok.domain.board.Column
import omok.domain.board.Position
import omok.domain.board.Row
import omok.domain.player.Black
import omok.domain.player.Stone

enum class BoardModel(val text: String) {
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
    return BoardModel.INTERVAL_INTERVAL.text
}

private fun Column.rowFifteenToModel(): BoardModel {
    if (this == Column.A) return BoardModel.A_FIFTEEN
    if (this == Column.O) return BoardModel.O_FIFTEEN
    return BoardModel.INTERVAL_FIFTEEN
}

private fun Column.rowOneToModel(): BoardModel {
    if (this == Column.A) return BoardModel.A_ONE
    if (this == Column.O) return BoardModel.O_ONE
    return BoardModel.INTERVAL_ONE
}

private fun Row.columnAToModel(): BoardModel {
    if (this == Row.ONE) return BoardModel.A_ONE
    if (this == Row.FIFTEEN) return BoardModel.A_FIFTEEN
    return BoardModel.A_INTERVAL
}

private fun Row.columnOToModel(): BoardModel {
    if (this == Row.ONE) return BoardModel.O_ONE
    if (this == Row.FIFTEEN) return BoardModel.O_FIFTEEN
    return BoardModel.O_INTERVAL
}
