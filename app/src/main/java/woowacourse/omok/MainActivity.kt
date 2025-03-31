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
import controller.OmokController
import model.AddStoneStatus
import model.StoneColor
import model.Stone
import model.Position
import model.Row
import model.Col
import view.InputView
import view.Message
import view.ResultView

class MainActivity : AppCompatActivity(), InputView, ResultView {

    private lateinit var omokController: OmokController
    private lateinit var dbHelper: DbHelper
    private var roomId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        dbHelper = DbHelper(this)
        roomId = intent.getIntExtra(RoomContract.COLUMN_STONE_ROOM_ID, -1)
        if (roomId == -1) {
            Toast.makeText(this, "방 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBoard()
        omokController = OmokController(this, this, dbHelper, roomId)
        restorePreviousStones()
        printTurn(omokController.turnColor)
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
            "SELECT x, y, color FROM stones WHERE room_id = ? ORDER BY turn ASC",
            arrayOf(roomId.toString())
        )

        while (cursor.moveToNext()) {
            val x = cursor.getInt(cursor.getColumnIndexOrThrow("x"))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow("y"))
            val colorStr = cursor.getString(cursor.getColumnIndexOrThrow("color"))

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
        cursor.close()
    }

    override fun printTurn(stoneColor: StoneColor) {
        val turnPrinter = findViewById<TextView>(R.id.TurnPrinter)
        turnPrinter.text = "${stoneColor.toDisplay()}의 차례입니다."
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
        val printStoneColor = when (stoneColor) {
            StoneColor.WHITE -> "흰색"
            StoneColor.BLACK -> "검은색"
        }
        AlertDialog.Builder(this).run {
            setTitle("결과")
            setIcon(android.R.drawable.ic_dialog_info)
            setMessage("${printStoneColor}이 승리하였습니다")
            setPositiveButton("확인", null)
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
