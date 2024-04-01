package omok.fixtures

import woowacourse.omok.model.OmokStone
import woowacourse.omok.model.StoneColor

fun createOmokStone(
    x: Int,
    y: Int,
    color: StoneColor,
): OmokStone = OmokStone(createPosition(x, y), color)

fun createBlackStone(
    x: Int,
    y: Int,
): OmokStone = OmokStone(createPosition(x, y), StoneColor.BLACK)

fun createWhiteStone(
    x: Int,
    y: Int,
): OmokStone = OmokStone(createPosition(x, y), StoneColor.WHITE)
