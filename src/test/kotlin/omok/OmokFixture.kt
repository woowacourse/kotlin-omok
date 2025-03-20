package omok

import rule.wrapper.point.Point

val POINT_H5 = Point(4, 7)
val POINT_H6 = Point(5, 7)
val POINT_H7 = Point(6, 7)
val POINT_H8 = Point(7, 7)
val POINT_H9 = Point(8, 7)
val POINT_H10 = Point(9, 7)
val POINT_E8 = Point(7, 4)
val POINT_F8 = Point(7, 5)
val POINT_G8 = Point(7, 6)

fun beforeDoubleThree(): List<Point> = listOf(POINT_H7, POINT_G8, POINT_H6, POINT_F8)

fun beforeDoubleFour(): List<Point> = listOf(POINT_H7, POINT_G8, POINT_H6, POINT_F8, POINT_E8, POINT_H5)

fun beforeOverLine(): List<Point> = listOf(POINT_H5, POINT_H6, POINT_H7, POINT_H9, POINT_H10)

fun toViolation(): Point = POINT_H8
