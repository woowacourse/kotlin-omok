package omok

import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.position.Stone
import omok.domain.model.stone.StoneType
import omok.domain.model.stone.Stones

val horizontalFourStones =
    Stones(
        listOf(
            Stone(Position(Column.from(1, true), Row.from(1, true)), StoneType.BLACK),
            Stone(Position(Column.from(2, true), Row.from(1, true)), StoneType.BLACK),
            Stone(Position(Column.from(3, true), Row.from(1, true)), StoneType.BLACK),
            Stone(Position(Column.from(4, true), Row.from(1, true)), StoneType.BLACK),
        ),
    )

val verticalFourStones =
    Stones(
        listOf(
            Stone(Position(Column.from(5, true), Row.from(1, true)), StoneType.BLACK),
            Stone(Position(Column.from(5, true), Row.from(2, true)), StoneType.BLACK),
            Stone(Position(Column.from(5, true), Row.from(3, true)), StoneType.BLACK),
            Stone(Position(Column.from(5, true), Row.from(4, true)), StoneType.BLACK),
        ),
    )

val diagonalDownFourStones =
    Stones(
        listOf(
            Stone(Position(Column.from(1, true), Row.from(1, true)), StoneType.BLACK),
            Stone(Position(Column.from(2, true), Row.from(2, true)), StoneType.BLACK),
            Stone(Position(Column.from(3, true), Row.from(3, true)), StoneType.BLACK),
            Stone(Position(Column.from(4, true), Row.from(4, true)), StoneType.BLACK),
            Stone(Position(Column.from(5, true), Row.from(5, true)), StoneType.BLACK),
        ),
    )

val doubleThreeFixture =
    Stones(
        listOf(
            Stone(Position(Column.from(2, true), Row.from(6, true)), StoneType.BLACK),
            Stone(Position(Column.from(5, true), Row.from(6, true)), StoneType.BLACK),
            Stone(Position(Column.from(3, true), Row.from(5, true)), StoneType.BLACK),
            Stone(Position(Column.from(5, true), Row.from(5, true)), StoneType.BLACK),
        ),
    )

val doubleFourFixture =
    Stones(
        listOf(
            Stone(Position(Column.from(3, true), Row.from(10, true)), StoneType.BLACK),
            Stone(Position(Column.from(3, true), Row.from(11, true)), StoneType.BLACK),
            Stone(Position(Column.from(3, true), Row.from(12, true)), StoneType.BLACK),
            Stone(Position(Column.from(3, true), Row.from(14, true)), StoneType.BLACK),
            Stone(Position(Column.from(3, true), Row.from(15, true)), StoneType.BLACK),
            Stone(Position(Column.from(4, true), Row.from(12, true)), StoneType.BLACK),
        ),
    )

val stoneLongMoveFixture =
    Stones(
        listOf(
            Stone(Position(Column.from(2, true), Row.from(2, true)), StoneType.BLACK),
            Stone(Position(Column.from(3, true), Row.from(3, true)), StoneType.BLACK),
            Stone(Position(Column.from(4, true), Row.from(4, true)), StoneType.BLACK),
            Stone(Position(Column.from(6, true), Row.from(6, true)), StoneType.BLACK),
            Stone(Position(Column.from(7, true), Row.from(7, true)), StoneType.BLACK),
        ),
    )

val fourFixture =
    Stones(
        listOf(
            Stone(Position(Column.from(1, true), Row.from(1, true)), StoneType.BLACK),
            Stone(Position(Column.from(2, true), Row.from(1, true)), StoneType.BLACK),
            Stone(Position(Column.from(3, true), Row.from(1, true)), StoneType.BLACK),
            Stone(Position(Column.from(4, true), Row.from(1, true)), StoneType.BLACK),
        ),
    )

val positionOneAndOne = Position(1, true, 1, true)

val stoneOneAndOne = Stone(positionOneAndOne, StoneType.BLACK)

val stoneSixAndSix = Stone(6, true, 6, true, StoneType.BLACK)

val longMoveStone = Stone(1, true, 5, true, StoneType.BLACK)

val doubleThreeStone = Stone(5, true, 3, true, StoneType.BLACK)

val doubleFourStone = Stone(3, true, 13, true, StoneType.BLACK)

val diagonalDownWinStone = Stone(Position(Column.from(5, true), Row.from(5, true)), StoneType.BLACK)

val verticalWinStone = Stone(Position(Column.from(5, true), Row.from(5, true)), StoneType.BLACK)

val horizontalWinStone = Stone(Position(Column.from(5, true), Row.from(1, true)), StoneType.BLACK)
