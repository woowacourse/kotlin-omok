package woowacourse.omok.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import controller.OmokController
import domain.game.Omok
import domain.game.PutSuccess
import domain.point.Point
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import domain.stone.StoneColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import view.InputView
import view.OutputView
import woowacourse.omok.R
import woowacourse.omok.database.OmokDatabase
import woowacourse.omok.database.repository.OmokRepository
import woowacourse.omok.database.repository.Repository
import woowacourse.omok.mapper.toPresentation
import woowacourse.omok.model.StoneColorModel
import woowacourse.omok.utils.createAlertDialog
import woowacourse.omok.utils.message
import woowacourse.omok.utils.negativeButton
import woowacourse.omok.utils.positiveButton

class MainActivity(override val coroutineContext: MainCoroutineDispatcher = Dispatchers.Main) :
    AppCompatActivity(), InputView, OutputView, CoroutineScope {
    private lateinit var board: TableLayout
    private lateinit var omok: Omok
    private lateinit var omokRepo: Repository<Point>
    private lateinit var manTurnHolder: View
    private lateinit var womanTurnHolder: View
    private lateinit var omokController: OmokController

    private val pointChannel = Channel<Point>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        omokController = OmokController(this, this)
        omokRepo = OmokRepository(OmokDatabase.getInstance(this))
        omok = Omok(BlackRenjuRule(), WhiteRenjuRule())
        initView()
        setOmokClickListener()

        launch {
            omokController.startGame(BlackRenjuRule(), WhiteRenjuRule())
        }
    }

    private fun initView() {
        board = findViewById(R.id.board)
        manTurnHolder = findViewById(R.id.man_turn_iv)
        womanTurnHolder = findViewById(R.id.woman_turn_iv)
    }

    private fun continuePreviousGame() {
        val points = omokRepo.getAll()
        if (points.size % 2 == 1) toggleTurnHolder(StoneColorModel.BLACK)
        points.forEach { point ->
            val index = point.row * Omok.OMOK_BOARD_SIZE + point.col
            val omokIv: ImageView = boardImageViews()[index]
            val putResult = omok.put { point }
            if (putResult is PutSuccess) {
                drawStoneOnBoard(omokIv, putResult.stoneColor)
            }
        }
    }

    private fun setOmokClickListener() {
        boardImageViews().forEachIndexed { index, view ->
            val row: Int = index / Omok.OMOK_BOARD_SIZE
            val col: Int = index % Omok.OMOK_BOARD_SIZE

            view.setOnClickListener {
                CoroutineScope(coroutineContext).launch {
                    pointChannel.send(Point(row, col))
                }
            }
        }
    }

    override suspend fun askPosition(onPutStone: (Point) -> Unit) {
        for (newPoint in pointChannel) {
            onPutStone(newPoint)
        }
    }

    override fun startGame() {
        showMessage(getString(R.string.omok_start_message))
        if (hasPreviousGameHistory()) {
            showRestartDialog()
        }
    }

    override fun drawPoint(stoneColor: StoneColor, newPoint: Point) {
        toggleTurnHolder(stoneColor.toPresentation())
        savePoint(newPoint)
        val index = newPoint.row * Omok.OMOK_BOARD_SIZE + newPoint.col
        drawStoneOnBoard(boardImageViews()[index], stoneColor)
    }

    override fun printPutFailed() {
        showMessage(getString(R.string.inplace_stone))
    }

    override fun printResult(
        lastStoneColor: StoneColor,
        winnerStoneColor: StoneColor,
        newPoint: Point,
    ) {
        omokRepo.clear()
        showWinner(winnerStoneColor.toPresentation())
    }

    override fun printTurnStartMessage(stoneColor: StoneColor, point: Point?) {
        toggleTurnHolder(stoneColor.toPresentation())
        if (point != null) {
            // TODO("마지막 돌을 놓은 위치에 border 처리")
//            val viewPosition = point.toPresentation()
//            print("(마지막 돌의 위치: ${viewPosition.row})\n")
        }
    }

    private fun toggleTurnHolder(prevStoneColor: StoneColorModel) {
        when (prevStoneColor) {
            StoneColorModel.BLACK -> {
                manTurnHolder.visibility = View.GONE
                womanTurnHolder.visibility = View.VISIBLE
            }
            StoneColorModel.WHITE -> {
                womanTurnHolder.visibility = View.GONE
                manTurnHolder.visibility = View.VISIBLE
            }
        }
    }

    private fun savePoint(point: Point) {
        omokRepo.insert(point)
    }

    private fun hasPreviousGameHistory(): Boolean = !omokRepo.isEmpty()

    private fun drawStoneOnBoard(view: ImageView, stoneColor: StoneColor) {
        when (stoneColor) {
            StoneColor.BLACK -> view.setImageResource(R.drawable.pink_bear)
            StoneColor.WHITE -> view.setImageResource(R.drawable.white_bear)
        }
    }

    private fun showWinner(winnerColor: StoneColorModel) {
        val resultIntent = Intent(this, GameResultActivity::class.java)
            .putExtra("winner_color", winnerColor)
        startActivity(resultIntent)
        finish()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showRestartDialog(): Unit = createAlertDialog {
        message(getString(R.string.restart_game_ask_message))
        positiveButton(getString(R.string.yes)) { continuePreviousGame() }
        negativeButton(getString(R.string.no)) { omokRepo.clear() }
    }.show()

    private fun boardImageViews(): List<ImageView> = board.children
        .filterIsInstance<TableRow>()
        .flatMap { it.children }
        .filterIsInstance<ImageView>()
        .toList()

    override fun onDestroy() {
        super.onDestroy()
        omokRepo.close()
    }
}
