package omok

import omok.domain.model.position.Position
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType

val horizontalWinStones =
    mutableListOf(
        OmokStone(Position.of(1, 1, 15), StoneType.BLACK),
        OmokStone(Position.of(2, 1, 15), StoneType.BLACK),
        OmokStone(Position.of(3, 1, 15), StoneType.BLACK),
        OmokStone(Position.of(4, 1, 15), StoneType.BLACK),
    )

val verticalWinStones =
    mutableListOf(
        OmokStone(Position.of(1, 1, 15), StoneType.BLACK),
        OmokStone(Position.of(1, 2, 15), StoneType.BLACK),
        OmokStone(Position.of(1, 3, 15), StoneType.BLACK),
        OmokStone(Position.of(1, 4, 15), StoneType.BLACK),
    )

val diagonalDownWinStones =
    mutableListOf(
        OmokStone(Position.of(1, 1, 15), StoneType.BLACK),
        OmokStone(Position.of(2, 2, 15), StoneType.BLACK),
        OmokStone(Position.of(3, 3, 15), StoneType.BLACK),
        OmokStone(Position.of(4, 4, 15), StoneType.BLACK),
    )

val doubleThreeFixture = // B6, E6, C5, E5
    mutableListOf(
        OmokStone(Position.of(2, 6, 15), StoneType.BLACK),
        OmokStone(Position.of(5, 6, 15), StoneType.BLACK),
        OmokStone(Position.of(3, 5, 15), StoneType.BLACK),
        OmokStone(Position.of(5, 5, 15), StoneType.BLACK),
    )

val doubleFourFixture = // C10, C11, C12, C14, C15, D12
    mutableListOf(
        OmokStone(Position.of(3, 10, 15), StoneType.BLACK),
        OmokStone(Position.of(3, 11, 15), StoneType.BLACK),
        OmokStone(Position.of(3, 12, 15), StoneType.BLACK),
        OmokStone(Position.of(3, 14, 15), StoneType.BLACK),
        OmokStone(Position.of(3, 15, 15), StoneType.BLACK),
        OmokStone(Position.of(4, 12, 15), StoneType.BLACK),
    )

val stoneLongMoveFixture = // B2, C3, D4, F6, G7
    mutableListOf(
        OmokStone(Position.of(2, 2, 15), StoneType.BLACK),
        OmokStone(Position.of(3, 3, 15), StoneType.BLACK),
        OmokStone(Position.of(4, 4, 15), StoneType.BLACK),
        OmokStone(Position.of(6, 6, 15), StoneType.BLACK),
        OmokStone(Position.of(7, 7, 15), StoneType.BLACK),
    )
