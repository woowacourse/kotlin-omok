package woowacourse.omok

import woowacourse.omok.domain.model.position.Column
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.position.Row
import woowacourse.omok.domain.model.position.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones

val horizontalFourStones =
    Stones(
        listOf(
            Stone(Position(Column.from(1, 15), Row.from(1, 15)), StoneType.BLACK),
            Stone(Position(Column.from(2, 15), Row.from(1, 15)), StoneType.BLACK),
            Stone(Position(Column.from(3, 15), Row.from(1, 15)), StoneType.BLACK),
            Stone(Position(Column.from(4, 15), Row.from(1, 15)), StoneType.BLACK),
        ),
    )

val verticalFourStones =
    Stones(
        listOf(
            Stone(Position(Column.from(5, 15), Row.from(1, 15)), StoneType.BLACK),
            Stone(Position(Column.from(5, 15), Row.from(2, 15)), StoneType.BLACK),
            Stone(Position(Column.from(5, 15), Row.from(3, 15)), StoneType.BLACK),
            Stone(Position(Column.from(5, 15), Row.from(4, 15)), StoneType.BLACK),
        ),
    )

val diagonalDownFourStones =
    Stones(
        listOf(
            Stone(Position(Column.from(1, 15), Row.from(1, 15)), StoneType.BLACK),
            Stone(Position(Column.from(2, 15), Row.from(2, 15)), StoneType.BLACK),
            Stone(Position(Column.from(3, 15), Row.from(3, 15)), StoneType.BLACK),
            Stone(Position(Column.from(4, 15), Row.from(4, 15)), StoneType.BLACK),
            Stone(Position(Column.from(5, 15), Row.from(5, 15)), StoneType.BLACK),
        ),
    )

val doubleThreeFixture =
    Stones(
        listOf(
            Stone(Position(Column.from(2, 15), Row.from(6, 15)), StoneType.BLACK),
            Stone(Position(Column.from(5, 15), Row.from(6, 15)), StoneType.BLACK),
            Stone(Position(Column.from(3, 15), Row.from(5, 15)), StoneType.BLACK),
            Stone(Position(Column.from(5, 15), Row.from(5, 15)), StoneType.BLACK),
        ),
    )

val doubleFourFixture =
    Stones(
        listOf(
            Stone(Position(Column.from(3, 15), Row.from(10, 15)), StoneType.BLACK),
            Stone(Position(Column.from(3, 15), Row.from(11, 15)), StoneType.BLACK),
            Stone(Position(Column.from(3, 15), Row.from(12, 15)), StoneType.BLACK),
            Stone(Position(Column.from(3, 15), Row.from(14, 15)), StoneType.BLACK),
            Stone(Position(Column.from(3, 15), Row.from(15, 15)), StoneType.BLACK),
            Stone(Position(Column.from(4, 15), Row.from(12, 15)), StoneType.BLACK),
        ),
    )

val stoneLongMoveFixture =
    Stones(
        listOf(
            Stone(Position(Column.from(2, 15), Row.from(2, 15)), StoneType.BLACK),
            Stone(Position(Column.from(3, 15), Row.from(3, 15)), StoneType.BLACK),
            Stone(Position(Column.from(4, 15), Row.from(4, 15)), StoneType.BLACK),
            Stone(Position(Column.from(6, 15), Row.from(6, 15)), StoneType.BLACK),
            Stone(Position(Column.from(7, 15), Row.from(7, 15)), StoneType.BLACK),
        ),
    )

val fourFixture =
    Stones(
        listOf(
            Stone(Position(Column.from(1, 15), Row.from(1, 15)), StoneType.BLACK),
            Stone(Position(Column.from(2, 15), Row.from(1, 15)), StoneType.BLACK),
            Stone(Position(Column.from(3, 15), Row.from(1, 15)), StoneType.BLACK),
            Stone(Position(Column.from(4, 15), Row.from(1, 15)), StoneType.BLACK),
        ),
    )

val positionOneAndOne = Position(1, 1, 15)

val stoneOneAndOne = Stone(positionOneAndOne, StoneType.BLACK)

val whiteStoneOneAndOne = Stone(positionOneAndOne, StoneType.WHITE)

val stoneSixAndSix = Stone(6, 6, 15, StoneType.BLACK)

val longMoveStone = Stone(5, 5, 15, StoneType.BLACK)

val doubleThreeStone = Stone(5, 3, 15, StoneType.BLACK)

val doubleFourStone = Stone(3, 13, 15, StoneType.BLACK)

val diagonalDownWinStone = Stone(Position(Column.from(5, 15), Row.from(5, 15)), StoneType.BLACK)

val verticalWinStone = Stone(Position(Column.from(5, 15), Row.from(5, 15)), StoneType.BLACK)

val horizontalWinStone = Stone(Position(Column.from(5, 15), Row.from(1, 15)), StoneType.BLACK)
