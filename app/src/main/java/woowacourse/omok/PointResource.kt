package woowacourse.omok

import domain.OmokGame
import domain.Board
import domain.stone.Point
import domain.stone.Stone
import domain.stone.Team

fun Point.getResourceForRunningGame(omokGame: OmokGame): Int =
    when {
        this.isWhereBlackStoneIsPlaced(omokGame) -> R.drawable.black_stone
        this.isWhereWhiteStoneIsPlaced(omokGame) -> R.drawable.white_stone
        this.isWhereNoStoneCanBePlaced(omokGame) -> R.drawable.not_place_mark
        this.isLeftTopCornerOfBoard() -> R.drawable.board_top_left
        this.isRightTopCornerOfBoard() -> R.drawable.board_top_right
        this.isLeftBottomCornerOfBoard() -> R.drawable.board_bottom_left
        this.isRightBottomCornerOfBoard() -> R.drawable.board_bottom_right
        this.isTopCornerOfBoard() -> R.drawable.board_top
        this.isBottomCornerOfBoard() -> R.drawable.board_bottom
        this.isLeftCornerOfBoard() -> R.drawable.board_left
        this.isRightCornerOfBoard() -> R.drawable.board_right
        else -> R.drawable.board_center
    }

fun Point.getResourceForFinishedGame(omokGame: OmokGame): Int =
    when {
        this.isWhereBlackStoneIsPlaced(omokGame) -> R.drawable.black_stone
        this.isWhereWhiteStoneIsPlaced(omokGame) -> R.drawable.white_stone
        this.isLeftTopCornerOfBoard() -> R.drawable.board_top_left
        this.isRightTopCornerOfBoard() -> R.drawable.board_top_right
        this.isLeftBottomCornerOfBoard() -> R.drawable.board_bottom_left
        this.isRightBottomCornerOfBoard() -> R.drawable.board_bottom_right
        this.isTopCornerOfBoard() -> R.drawable.board_top
        this.isBottomCornerOfBoard() -> R.drawable.board_bottom
        this.isLeftCornerOfBoard() -> R.drawable.board_left
        this.isRightCornerOfBoard() -> R.drawable.board_right
        else -> R.drawable.board_center
    }

private fun Point.isWhereBlackStoneIsPlaced(omokGame: OmokGame): Boolean =
    omokGame.board.isPlaced(Team.BLACK, Stone(this))

private fun Point.isWhereWhiteStoneIsPlaced(omokGame: OmokGame): Boolean =
    omokGame.board.isPlaced(Team.WHITE, Stone(this))

private fun Point.isWhereNoStoneCanBePlaced(omokGame: OmokGame): Boolean =
    omokGame.canPlace(Stone(this)).not()

private fun Point.isLeftTopCornerOfBoard(): Boolean = x == 'A' && y == Board.BOARD_SIZE
private fun Point.isRightTopCornerOfBoard(): Boolean =
    x == 'A' + Board.BOARD_SIZE - 1 && y == Board.BOARD_SIZE

private fun Point.isLeftBottomCornerOfBoard(): Boolean = x == 'A' && y == 1
private fun Point.isRightBottomCornerOfBoard(): Boolean =
    x == 'A' + Board.BOARD_SIZE - 1 && y == 1

private fun Point.isTopCornerOfBoard(): Boolean = y == Board.BOARD_SIZE
private fun Point.isBottomCornerOfBoard(): Boolean = y == 1
private fun Point.isLeftCornerOfBoard(): Boolean = x == 'A'
private fun Point.isRightCornerOfBoard(): Boolean = x == 'A' + Board.BOARD_SIZE - 1

