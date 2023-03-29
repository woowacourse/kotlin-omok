package view.mapper

import domain.point.Point
import view.model.PointModel
import view.model.PointModel.Companion.COLUMN_RANGE
import view.model.PointModel.Companion.ROW_RANGE

fun Point.toPresentation(): PointModel {
    val row = ROW_RANGE[row - 1]
    val col = COLUMN_RANGE[col - 1]
    return PointModel(row, col)
}

fun PointModel.toDomain(): Point? {
    val row = ROW_RANGE.indexOf(row)
    val col = COLUMN_RANGE.indexOf(col)
    if (row == NOT_FOUND_INDEX || col == NOT_FOUND_INDEX) return null
    return Point(row + 1, col + 1)
}

private const val NOT_FOUND_INDEX = -1
