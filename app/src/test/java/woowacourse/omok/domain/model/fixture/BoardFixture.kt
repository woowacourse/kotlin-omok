package woowacourse.omok.domain.model.fixture

import woowacourse.omok.domain.omok.model.Color.BLACK
import woowacourse.omok.domain.omok.model.Color.NONE
import woowacourse.omok.domain.omok.model.Color.WHITE

fun createPlayingBoard() =
    arrayOf(
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, BLACK, BLACK, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
    )

fun createBlackWinningBoard() =
    arrayOf(
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, BLACK, BLACK, BLACK, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
    )

fun createThreeThreeBoard() =
    arrayOf(
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, BLACK, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
    )

fun createWhiteWinningBoard() =
    arrayOf(
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, WHITE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, BLACK, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
    )

fun createFourFourBoard() =
    arrayOf(
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, WHITE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, BLACK, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, BLACK, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, NONE, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
    )

fun createOverLineBoard() =
    arrayOf(
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, WHITE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, BLACK, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, BLACK, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, BLACK, BLACK, NONE, BLACK, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
    )

fun createGameOverBoard() =
    arrayOf(
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, WHITE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, WHITE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, WHITE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, BLACK, BLACK, BLACK, BLACK, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
    )

fun createDrawBoard() =
    arrayOf(
        arrayOf(WHITE, BLACK, WHITE, WHITE, WHITE, BLACK, BLACK, BLACK, WHITE, WHITE, BLACK, BLACK, WHITE, BLACK, BLACK, BLACK),
        arrayOf(BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE, BLACK, BLACK, WHITE, BLACK, BLACK, BLACK, WHITE, BLACK, BLACK),
        arrayOf(WHITE, BLACK, WHITE, WHITE, WHITE, BLACK, BLACK, BLACK, WHITE, WHITE, BLACK, BLACK, WHITE, BLACK, WHITE, BLACK),
        arrayOf(BLACK, WHITE, BLACK, BLACK, WHITE, BLACK, BLACK, WHITE, BLACK, BLACK, BLACK, WHITE, WHITE, BLACK, BLACK, WHITE),
        arrayOf(WHITE, BLACK, WHITE, BLACK, BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, BLACK),
        arrayOf(BLACK, WHITE, BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE, WHITE, BLACK),
        arrayOf(WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE, BLACK, BLACK, BLACK, WHITE),
        arrayOf(BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE, BLACK, BLACK, WHITE, BLACK, WHITE, BLACK, BLACK, BLACK, BLACK),
        arrayOf(WHITE, BLACK, WHITE, WHITE, BLACK, BLACK, BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE, BLACK, WHITE, BLACK),
        arrayOf(BLACK, WHITE, BLACK, BLACK, WHITE, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE),
        arrayOf(WHITE, BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE, BLACK, BLACK),
        arrayOf(BLACK, WHITE, BLACK, WHITE, BLACK, BLACK, WHITE, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, BLACK, BLACK, WHITE),
        arrayOf(WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, BLACK, BLACK, WHITE, BLACK, WHITE, BLACK, BLACK, BLACK, WHITE, BLACK),
        arrayOf(BLACK, WHITE, BLACK, WHITE, WHITE, WHITE, BLACK, BLACK, WHITE, WHITE, BLACK, BLACK, WHITE, WHITE, BLACK, BLACK),
        arrayOf(WHITE, BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, WHITE, BLACK),
        arrayOf(BLACK, WHITE, BLACK, WHITE, BLACK, BLACK, WHITE, BLACK, BLACK, BLACK, WHITE, BLACK, WHITE, BLACK, BLACK, WHITE),
    )
