package woowacourse.omok

import woowacourse.omok.domain.Position

val POSITION_H5 = Position(4, 7)
val POSITION_H6 = Position(5, 7)
val POSITION_H7 = Position(6, 7)
val POSITION_H8 = Position(7, 7)
val POSITION_H9 = Position(8, 7)
val POSITION_H10 = Position(9, 7)
val POSITION_E8 = Position(7, 4)
val POSITION_F8 = Position(7, 5)
val POSITION_G8 = Position(7, 6)
val POSITION_A1 = Position(0, 0)
val POSITION_A2 = Position(1, 0)
val POSITION_A3 = Position(2, 0)
val POSITION_A4 = Position(3, 0)
val POSITION_O1 = Position(0, 14)
val POSITION_O2 = Position(1, 14)

fun beforeDoubleThree(): List<Position> =
    listOf(POSITION_H7, POSITION_A1, POSITION_G8, POSITION_A2, POSITION_H6, POSITION_A3, POSITION_F8, POSITION_A4)

fun beforeDoubleFour(): List<Position> =
    listOf(
        POSITION_H7,
        POSITION_A1,
        POSITION_G8,
        POSITION_A2,
        POSITION_H6,
        POSITION_A3,
        POSITION_F8,
        POSITION_A4,
        POSITION_E8,
        POSITION_O1,
        POSITION_H5,
        POSITION_O2,
    )

fun beforeOverLine(): List<Position> =
    listOf(
        POSITION_H5,
        POSITION_A1,
        POSITION_H6,
        POSITION_A2,
        POSITION_H7,
        POSITION_A3,
        POSITION_H9,
        POSITION_A4,
        POSITION_H10,
        POSITION_O1,
    )

fun beforeFinished(): List<Position> =
    listOf(
        POSITION_H5,
        POSITION_A1,
        POSITION_H6,
        POSITION_A2,
        POSITION_H7,
        POSITION_A3,
        POSITION_H8,
        POSITION_A4,
    )

fun toViolationPosition(): Position = POSITION_H8

fun toFinishedPosition(): Position = POSITION_H9
