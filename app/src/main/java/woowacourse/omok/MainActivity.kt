package woowacourse.omok

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.controller.OmokController
import woowacourse.omok.model.AddStoneStatus
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.Stone
import woowacourse.omok.model.Position
import woowacourse.omok.model.Row
import woowacourse.omok.model.Col
import woowacourse.omok.view.InputView
import woowacourse.omok.view.Message
import woowacourse.omok.view.ResultView

class MainActivity : AppCompatActivity(), InputView, ResultView {

    private lateinit var omokController: OmokController
    private var dbHelper: DbHelper =  DbHelper(this)
    private var roomId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        if(!findRoomId()) return
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        makeGameScene()
    }

    private fun makeGameScene() {
        setupBoard()
        omokController = OmokController(this, this, dbHelper, roomId)
        restorePreviousStones()
        printTurn(omokController.turnColor)
    }

    private fun findRoomId() : Boolean {
        roomId = intent.getIntExtra(RoomContract.COLUMN_STONE_ROOM_ID, -1)
        if (roomId == -1) {
            Toast.makeText(this, resources.getString(R.string.fail_find_room), Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        return true
    }

    private fun setupBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        val totalRows = board.childCount
        board.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                val colLabel = (totalRows - rowIndex).toString()
                tableRow.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, imageView ->
                        val rowLabel = ('A' + colIndex).toString()
                        imageView.tag = "$rowLabel$colLabel"
                        imageView.setOnClickListener {
                            val position = imageView.tag as? String ?: return@setOnClickListener
                            onCellClicked(position)
                        }
                    }
            }
    }

    private fun restorePreviousStones() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            RoomContract.SQL_FIND_ROOM_STONES,
            arrayOf(roomId.toString())
        )

        while (cursor.moveToNext()) {
            val x = cursor.getInt(cursor.getColumnIndexOrThrow(RoomContract.COLUMN_STONE_X))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow(RoomContract.COLUMN_STONE_Y))
            val colorStr = cursor.getString(cursor.getColumnIndexOrThrow(RoomContract.COLUMN_STONE_COLOR))
            putPreviousStone(x,y,colorStr)
        }
        cursor.close()
    }

    private fun putPreviousStone(x:Int,y:Int,colorStr:String){
        val position = Position(Row.from(x), Col.from(y))
        val color = StoneColor.from(colorStr)
        val tag = position.toString()

        val board = findViewById<TableLayout>(R.id.board)
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .firstOrNull { it.tag == tag }
            ?.setImageResource(setImage(color))

        omokController.gameBoard.addStone(Stone(position, color))
        omokController.turnColor = color.switch()
    }

    override fun printTurn(stoneColor: StoneColor) {
        val turnPrinter = findViewById<TextView>(R.id.TurnPrinter)
        turnPrinter.text = String.format(resources.getString(R.string.turn_message),stoneColor.toString())
    }

    override fun printStone(
        addStoneStatus: AddStoneStatus,
        stoneColor: StoneColor,
        position: String
    ) {
        val board = findViewById<TableLayout>(R.id.board)
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .firstOrNull { it.tag == position }
            ?.setImageResource(setImage(stoneColor))

        if (addStoneStatus == AddStoneStatus.IsWin) printWinner(stoneColor)
    }

    override fun printWinner(stoneColor: StoneColor) {
        AlertDialog.Builder(this).run {
            setTitle(resources.getString(R.string.result))
            setIcon(android.R.drawable.ic_dialog_info)
            setMessage(String.format(resources.getString(R.string.winner_message),stoneColor.toString()))
            setPositiveButton(resources.getString(R.string.check), null)
            show()
        }
        val board = findViewById<TableLayout>(R.id.board)
        board.children.forEach { row ->
            if (row is TableRow) {
                row.children.forEach { cell ->
                    cell.isClickable = false
                }
            }
        }
    }

    private fun setImage(stoneColor: StoneColor): Int {
        return when (stoneColor) {
            StoneColor.BLACK -> R.drawable.black_stone
            StoneColor.WHITE -> R.drawable.white_stone
        }
    }

    override fun printError(status: AddStoneStatus.Failed) {
        val message = when (status) {
            AddStoneStatus.Failed.IsExist -> Message.EXIST_STONE
            AddStoneStatus.Failed.IsUnAblePosition -> Message.ERROR_POSITION
            AddStoneStatus.Failed.IsFourFour -> Message.FOUR_FOUR
            AddStoneStatus.Failed.IsThreeThree -> Message.THREE_THREE
            AddStoneStatus.Failed.IsOverFive -> Message.OVER_FIVE
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override var inputListener: ((String) -> Unit)? = { position: String ->
        omokController.addValidStone(position)
    }
}
