package woowacourse.omok

import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.grid.Row

val POINT_H5 = Point(Row(4), Column(7))
val POINT_H6 = Point(Row(5), Column(7))
val POINT_H7 = Point(Row(6), Column(7))
val POINT_H8 = Point(Row(7), Column(7))
val POINT_H9 = Point(Row(8), Column(7))
val POINT_H10 = Point(Row(9), Column(7))
val POINT_E8 = Point(Row(7), Column(4))
val POINT_F8 = Point(Row(7), Column(5))
val POINT_G8 = Point(Row(7), Column(6))

fun beforeDoubleThree(): List<Point> = listOf(POINT_H7, POINT_G8, POINT_H6, POINT_F8)

fun beforeDoubleFour(): List<Point> = listOf(POINT_H7, POINT_G8, POINT_H6, POINT_F8, POINT_E8, POINT_H5)

fun beforeOverLine(): List<Point> = listOf(POINT_H5, POINT_H6, POINT_H7, POINT_H9, POINT_H10)

fun getFoulPoint(): Point = POINT_H8

fun omokPoints(): List<Point> = listOf(POINT_H5, POINT_H6, POINT_H7, POINT_H8, POINT_H9)
