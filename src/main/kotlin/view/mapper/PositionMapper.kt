package view.mapper

import domain.position.Position
import view.model.PositionModel
import view.model.PositionModel.Companion.COLUMN_RANGE
import view.model.PositionModel.Companion.ROW_RANGE

fun Position.toPresentation(): PositionModel {
    val row = ROW_RANGE[row + 1]
    val col = COLUMN_RANGE[col + 1]
    return PositionModel(row, col)
}

fun PositionModel.toDomain(): Position? {
    val row = ROW_RANGE.indexOf(row)
    val col = COLUMN_RANGE.indexOf(col)
    if (row == NOT_FOUND_INDEX || col == NOT_FOUND_INDEX) return null
    return Position(row + 1, col + 1)
}

private const val NOT_FOUND_INDEX = -1
