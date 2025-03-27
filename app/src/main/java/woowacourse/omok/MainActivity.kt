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
import view.InputView
import view.Message
import view.ResultView

class MainActivity : AppCompatActivity(), InputView, ResultView {

    private lateinit var omokController: OmokController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupBoard()
        omokController = OmokController(this, this)
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

    override fun printTurn(stoneColor: StoneColor){
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

        if(addStoneStatus == AddStoneStatus.IsWin) printWinner(stoneColor)
    }

    override fun printWinner(stoneColor: StoneColor) {
        val printStoneColor = when(stoneColor){
            StoneColor.WHITE -> "흼색"
            StoneColor.BLACK -> "검은색"
        }
        AlertDialog.Builder(this).run {
            setTitle("결과")
            setIcon(android.R.drawable.ic_dialog_info)
            setMessage("${printStoneColor}이 승리하엿습니다")
            setPositiveButton("YES", null)
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

    private fun setImage(stoneColor: StoneColor):Int{
        return when(stoneColor){
            StoneColor.BLACK -> R.drawable.black_stone
            StoneColor.WHITE -> R.drawable.white_stone
        }
    }

    override fun printError(status: AddStoneStatus.Failed){
        val message = when(status){
            AddStoneStatus.Failed.IsExist -> Message.EXIST_STONE
            AddStoneStatus.Failed.IsUnAblePosition -> Message.ERROR_POSITION
            AddStoneStatus.Failed.IsFourFour -> Message.FOUR_FOUR
            AddStoneStatus.Failed.IsThreeThree -> Message.THREE_THREE
            AddStoneStatus.Failed.IsOverFive -> Message.OVER_FIVE
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override var inputListener: ((String) -> Unit)? = { position:String ->
        omokController.addValidStone(position)
    }
}
