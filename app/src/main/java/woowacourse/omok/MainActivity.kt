package woowacourse.omok

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokGame
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone
import woowacourse.omok.db.StoneConstract
import woowacourse.omok.db.StonesHelper

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private lateinit var wDb: SQLiteDatabase
    private var roomNumber: Int = 0
    private var latestPutOrderNumber = 0
    private lateinit var gameEndButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wDb = StonesHelper(this).writableDatabase
        roomNumber = 0

        // 게임 진행 여부 상태를 먼저 파악해야 함. 그 다음 분기문으로 빠지도록.
        val (loadBoard, latestStone, latestPutOrderNum) = getAlreadyPutStones(wDb, roomNumber)
        omokGame = OmokGame(loadBoard, latestStone)
        latestPutOrderNumber = latestPutOrderNum + 1

        val board = findViewById<TableLayout>(R.id.board)

        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                val position = convertIndexToPosition(index)
                loadBoard[position]?.let {
                    setStone(it, view)
                }
                view.setOnClickListener {
                    if (omokGame.turn.boardState.isFinished().not()) positionClick(position, view)
                }
            }

        gameEndButton = findViewById(R.id.game_end_button)
        gameEndButton.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
    }

    private fun positionClick(position: Position, view: ImageView) {
        val playTurnColor = omokGame.playTurn(position)
        if (playTurnColor == null && omokGame.turn.boardState.isFinished().not()) {
            toast("해당 위치에는 놓을 수 없습니다.")
        } else if (playTurnColor == null) {
            toast("이미 게임이 종료됐습니다.")
        } else {
            setStone(playTurnColor, view)
            wDb.execSQL(
                "INSERT INTO ${StoneConstract.TABLE_NAME} (${StoneConstract.TABLE_COLUMN_ROOM_NUMBER}, ${StoneConstract.TABLE_COLUMN_COLOR}, ${StoneConstract.TABLE_COLUMN_X}, ${StoneConstract.TABLE_COLUMN_Y}, ${StoneConstract.TABLE_COLUMN_PUT_ORDER_NUMBER})" +
                    "VALUES ($roomNumber, ${playTurnColor.ordinal}, ${position.column.ordinal}, ${position.row.ordinal}, ${latestPutOrderNumber++});"
            )
        }
        if (omokGame.turn.boardState.isFinished()) {
            toast("게임이 종료됐습니다.")
            gameEndButton.visibility = View.VISIBLE
            // 일단은 삭제로직을 여기에 놓는다. 게임 종료되면 데이터베이스 테이블에서 삭제되도록
            wDb.execSQL(
                "DELETE FROM ${StoneConstract.TABLE_NAME} " +
                    "WHERE ${StoneConstract.TABLE_COLUMN_ROOM_NUMBER} = '$roomNumber';"
            )
        }
    }

    private fun getAlreadyPutStones(
        db: SQLiteDatabase,
        roomNumber: Int
    ): Triple<Map<Position, Color?>, Stone?, Int> {
        val cursor = db.query(
            StoneConstract.TABLE_NAME,
            arrayOf(
                StoneConstract.TABLE_COLUMN_ROOM_NUMBER,
                StoneConstract.TABLE_COLUMN_COLOR,
                StoneConstract.TABLE_COLUMN_X,
                StoneConstract.TABLE_COLUMN_Y,
                StoneConstract.TABLE_COLUMN_PUT_ORDER_NUMBER
            ),
            "${StoneConstract.TABLE_COLUMN_ROOM_NUMBER} = ?",
            arrayOf(roomNumber.toString()),
            null,
            null,
            "${StoneConstract.TABLE_COLUMN_PUT_ORDER_NUMBER} DESC"
        )

        var latestPutNum = 0
        val result = mutableListOf<Stone>()
        while (cursor.moveToNext()) {
            val colorOrdinal =
                cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_COLOR))
            val column = cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_X))
            val row = cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_Y))
            val position = Position(column + 1, row + 1)
            val color = Color.values()[colorOrdinal]
            if (latestPutNum == 0) {
                latestPutNum =
                    cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_PUT_ORDER_NUMBER))
            }
            result.add(Stone(position, color))
        }

        val latestStone: Stone? = result.firstOrNull()
        val loadBoard: MutableMap<Position, Color?> =
            Position.all().associateWith { null }.toMutableMap()
        result.forEach {
            loadBoard[it.position] = it.color
        }
        cursor.close()
        return Triple(loadBoard, latestStone, latestPutNum)
    }

    private fun convertIndexToPosition(index: Int): Position {
        val row = 14 - (index / 15)
        val column = index % 15
        Log.d("kmj", "index: $index , column: $column , row: $row")
        return Position(column + 1, row + 1)
    }

    private fun setStone(color: Color, view: ImageView) {
        when (color) {
            Color.BLACK -> view.setImageResource(R.drawable.black_navi_stone)
            Color.WHITE -> view.setImageResource(R.drawable.white_choonbae_stone)
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        wDb.close()
    }
}
