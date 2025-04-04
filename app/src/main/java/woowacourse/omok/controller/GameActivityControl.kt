package woowacourse.omok.controller

import android.content.ContentValues
import android.widget.ImageView
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.PositionStatus.EMPTY
import woowacourse.omok.model.database.OmokDBContract
import woowacourse.omok.model.database.OmokDBHelper
import woowacourse.omok.model.rule.BudoolRenjuRuleAdapter
import woowacourse.omok.model.rule.OmokReferee
import woowacourse.omok.model.rule.RenjuFoul
import woowacourse.omok.model.rule.RenjuFoul.SAFE
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.position.Col
import woowacourse.omok.model.stone.position.Position
import woowacourse.omok.model.stone.position.Row
import woowacourse.omok.view.GameActivityOutputView

class GameActivityControl(
    private val boardSize: BoardSize,
    private val gameActivityOutputView: GameActivityOutputView,
    private val omokDBHelper: OmokDBHelper,
    private val roomId: Int,
    private val blackPlayerName: String,
    private val whitePlayerName: String,
) {
    private val omokReferee = OmokReferee(BudoolRenjuRuleAdapter(boardSize))
    private var board = Board(boardSize, dbOrderedStoneMap())

    private fun dbOrderedStoneMap(): LinkedHashMap<Position, StoneColor> {
        val db = omokDBHelper.readableDatabase
        val projection =
            arrayOf(
                OmokDBContract.StonesTable.COLUMN_NAME_ORDER,
                OmokDBContract.StonesTable.COLUMN_NAME_ROW_INDEX,
                OmokDBContract.StonesTable.COLUMN_NAME_COL_INDEX,
                OmokDBContract.StonesTable.COLUMN_NAME_STONE_COLOR,
            )

        val selection = "${OmokDBContract.StonesTable.COLUMN_ROOM_ID} = ?"
        val selectionArgs = arrayOf(roomId.toString())

        db
            .query(
                OmokDBContract.StonesTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                "${OmokDBContract.StonesTable.COLUMN_NAME_ORDER} ASC",
            ).use { cursor ->
                val rowIndex = cursor.getColumnIndexOrThrow(OmokDBContract.StonesTable.COLUMN_NAME_ROW_INDEX)
                val colIndex = cursor.getColumnIndexOrThrow(OmokDBContract.StonesTable.COLUMN_NAME_COL_INDEX)
                val colorIndex = cursor.getColumnIndexOrThrow(OmokDBContract.StonesTable.COLUMN_NAME_STONE_COLOR)

                val stones: LinkedHashMap<Position, StoneColor> = linkedMapOf()
                while (cursor.moveToNext()) {
                    val row = cursor.getInt(rowIndex)
                    val col = cursor.getInt(colIndex)
                    val color = cursor.getString(colorIndex)

                    stones[Position(Row(row), Col(col))] = StoneColor.valueOf(color)
                }
                return stones
            }
    }

    fun boardUiRestore(positionViews: Map<Position, ImageView>) {
        gameActivityOutputView.turnInfoUiUpdate(getPlayerNameByColor(board.nextStoneColor))
        if (board.stonesMap.isNotEmpty()) {
            gameActivityOutputView.stonesUiDraw(board.stonesMap, positionViews)
            gameActivityOutputView.recoveryStonesAlert()
        }
    }

    fun turn(
        positionView: ImageView,
        nextPosition: Position,
    ) {
        if (!isPositionValid(nextPosition)) return

        val newBoard = stoneAddedBoard(nextPosition)
        boardUpdate(newBoard, positionView)
        omokCheck()
        gameActivityOutputView.turnInfoUiUpdate(getPlayerNameByColor(board.nextStoneColor))
    }

    private fun getPlayerNameByColor(stoneColor: StoneColor): String =
        when (stoneColor) {
            StoneColor.BLACK -> "$blackPlayerName(흑돌)"
            StoneColor.WHITE -> "$whitePlayerName(백돌)"
        }

    private fun isPositionValid(position: Position): Boolean {
        val positionState = board.positionStatus(position)

        if (positionState == EMPTY) {
            return true
        }
        gameActivityOutputView.positionStatusAlert(positionState)
        return false
    }

    private fun stoneAddedBoard(nextPosition: Position) = board.nextStonePlacedBoard(nextPosition)

    private fun boardUpdate(
        newBoard: Board,
        positionView: ImageView,
    ) {
        val foul = foulCheck(newBoard)

        if (foul == SAFE) {
            gameActivityOutputView.stoneUiDraw(board.nextStoneColor, positionView)
            board = newBoard
            board.lastStone?.let { stoneDBSave(it) }
            return
        }
    }

    private fun foulCheck(newBoard: Board): RenjuFoul {
        val foul = omokReferee.lastStoneFoul(newBoard)
        gameActivityOutputView.foulAlert(foul)
        return foul
    }

    private fun omokCheck() {
        if (omokReferee.isOmok(board)) {
            board.lastStone?.let {
                gameActivityOutputView.omokDialogAlert(
                    getPlayerNameByColor(it.stoneColor),
                    { gameRestart(it.stoneColor) },
                    { omokWinnerDBWrite(it.stoneColor) },
                    { omokDBHelper.roomWithStonesDelete(roomId) },
                    { omokDBHelper.stonesDelete(roomId) },
                )
            }
        }
    }

    private fun stoneDBSave(stone: Stone) {
        val values =
            ContentValues().apply {
                put(OmokDBContract.StonesTable.COLUMN_ROOM_ID, roomId)
                put(OmokDBContract.StonesTable.COLUMN_NAME_ROW_INDEX, stone.position.row.value)
                put(OmokDBContract.StonesTable.COLUMN_NAME_COL_INDEX, stone.position.col.value)
                put(OmokDBContract.StonesTable.COLUMN_NAME_STONE_COLOR, stone.stoneColor.name)
            }

        omokDBHelper.writableDatabase.use { db ->
            db.insert(OmokDBContract.StonesTable.TABLE_NAME, null, values)
        }
    }

    private fun gameRestart(stoneColor: StoneColor) {
        board = Board(boardSize)
        omokDBHelper.addPlayerHistory(blackPlayerName, playCount = GAME_LOG_COUNT_UNIT)
        omokDBHelper.addPlayerHistory(whitePlayerName, playCount = GAME_LOG_COUNT_UNIT)
        omokWinnerDBWrite(stoneColor)
        gameActivityOutputView.turnInfoUiUpdate(getPlayerNameByColor(board.nextStoneColor))
        gameActivityOutputView.stoneUiClear()
    }

    private fun omokWinnerDBWrite(stoneColor: StoneColor) {
        when (stoneColor) {
            StoneColor.BLACK ->
                omokDBHelper.addPlayerHistory(
                    blackPlayerName,
                    blackWinCount = GAME_LOG_COUNT_UNIT,
                )
            StoneColor.WHITE ->
                omokDBHelper.addPlayerHistory(
                    whitePlayerName,
                    whiteWinCount = GAME_LOG_COUNT_UNIT,
                )
        }
    }

    companion object {
        private const val GAME_LOG_COUNT_UNIT = 1
    }
}
