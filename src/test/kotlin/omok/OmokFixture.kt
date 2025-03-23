package omok

import omok.domain.Position

val POSITION_H5 = Position(4, 7)
val POSITION_H6 = Position(5, 7)
val POSITION_H7 = Position(6, 7)
val POSITION_H8 = Position(7, 7)
val POSITION_H9 = Position(8, 7)
val POSITION_H10 = Position(9, 7)
val POSITION_E8 = Position(7, 4)
val POSITION_F8 = Position(7, 5)
val POSITION_G8 = Position(7, 6)

fun beforeDoubleThree(): List<Position> = listOf(POSITION_H7, POSITION_G8, POSITION_H6, POSITION_F8)

fun beforeDoubleFour(): List<Position> = listOf(POSITION_H7, POSITION_G8, POSITION_H6, POSITION_F8, POSITION_E8, POSITION_H5)

fun beforeOverLine(): List<Position> = listOf(POSITION_H5, POSITION_H6, POSITION_H7, POSITION_H9, POSITION_H10)

fun toViolation(): Position = POSITION_H8
