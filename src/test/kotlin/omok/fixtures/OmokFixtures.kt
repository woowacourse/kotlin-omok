package omok.fixtures

import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor

fun createOmokStone(
    x: Int,
    y: Int,
    color: StoneColor,
): OmokStone = OmokStone(createPosition(x, y), color)

fun createBlackStone(
    x: Int,
    y: Int,
): OmokStone = OmokStone(createPosition(x, y), StoneColor.BLACK)

fun createBlackStone(position: Position): OmokStone = OmokStone(position, StoneColor.BLACK)

fun createWhiteStone(
    x: Int,
    y: Int,
): OmokStone = OmokStone(createPosition(x, y), StoneColor.WHITE)

fun createWhiteStone(position: Position): OmokStone = OmokStone(position, StoneColor.WHITE)
