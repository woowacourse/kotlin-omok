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
import woowacourse.omok.view.OutputAppView

class OmokAppControl(
    private val boardSize: BoardSize,
    private val outputAppView: OutputAppView,
    private val omokDBHelper: OmokDBHelper,
    private val roomId: Int,
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
        outputAppView.turnInfoUiUpdate(board.nextStoneColor)
        if (board.stonesMap.isNotEmpty()) {
            outputAppView.stonesUiDraw(board.stonesMap, positionViews)
            outputAppView.recoveryStonesAlert()
        }
    }

    fun turn(
        positionView: ImageView,
        coordinate: Pair<Int, Int>,
    ) {
        val row = Row(coordinate.first)
        val col = Col(coordinate.second)
        val nextPosition = Position(row, col)
        if (!isPositionValid(nextPosition)) return

        val newBoard = stoneAddedBoard(nextPosition)
        boardUpdate(newBoard, positionView)
        omokCheck()
        outputAppView.turnInfoUiUpdate(board.nextStoneColor)
    }

    private fun isPositionValid(position: Position): Boolean {
        val positionState = board.positionStatus(position)

        if (positionState == EMPTY) {
            return true
        }
        outputAppView.positionStatusAlert(positionState)
        return false
    }

    private fun stoneAddedBoard(nextPosition: Position) = board.nextStonePlacedBoard(nextPosition)

    private fun boardUpdate(
        newBoard: Board,
        positionView: ImageView,
    ) {
        val foul = foulCheck(newBoard)

        if (foul == SAFE) {
            outputAppView.stoneUiDraw(board.nextStoneColor, positionView)
            board = newBoard
            board.lastStone?.let { stoneDBSave(it) }
            return
        }
    }

    private fun foulCheck(newBoard: Board): RenjuFoul {
        val foul = omokReferee.lastStoneFoul(newBoard)
        outputAppView.foulAlert(foul)
        return foul
    }

    private fun omokCheck() {
        if (omokReferee.isOmok(board)) {
            board.lastStone?.let {
                outputAppView.omokDialogAlert(
                    it.stoneColor,
                    ::gameRestart,
                ) { omokDBHelper.roomWithStonesDelete(roomId) }
            }
        }
    }

    private fun stoneDBSave(stone: Stone) {
        val values = ContentValues()
        values.put(OmokDBContract.StonesTable.COLUMN_ROOM_ID, roomId)
        values.put(OmokDBContract.StonesTable.COLUMN_NAME_ROW_INDEX, stone.position.row.value)
        values.put(OmokDBContract.StonesTable.COLUMN_NAME_COL_INDEX, stone.position.col.value)
        values.put(OmokDBContract.StonesTable.COLUMN_NAME_STONE_COLOR, stone.stoneColor.name)
        val omokDB = omokDBHelper.writableDatabase
        omokDB.insert(OmokDBContract.StonesTable.TABLE_NAME, null, values)
    }

    private fun gameRestart() {
        board = Board(boardSize)
        outputAppView.turnInfoUiUpdate(board.nextStoneColor)
        outputAppView.stoneUiClear()
    }
}
