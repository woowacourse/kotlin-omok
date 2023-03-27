package woowacourse.omok.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import domain.game.Omok
import domain.point.Point
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import domain.stone.StoneColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import woowacourse.omok.R
import woowacourse.omok.controller.AndroidOmokController
import woowacourse.omok.controller.ConsoleOmokController
import woowacourse.omok.data.database.OmokDatabase
import woowacourse.omok.data.database.repository.OmokRepository
import woowacourse.omok.mapper.toPresentation
import woowacourse.omok.model.StoneColorModel
import woowacourse.omok.utils.createAlertDialog
import woowacourse.omok.utils.message
import woowacourse.omok.utils.negativeButton
import woowacourse.omok.utils.positiveButton
import woowacourse.omok.utils.showMessage
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity(), InputView, OutputView {
    private lateinit var board: TableLayout
    private lateinit var manTurnHolder: View
    private lateinit var womanTurnHolder: View
    private lateinit var omokController: AndroidOmokController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        lifecycleScope.launch { omokController.startGame(BlackRenjuRule(), WhiteRenjuRule()) }
    }

    private fun init() {
        initView()
        initOmokController()
        initOmokIntersectionClickListener()
    }

    private fun initView() {
        board = findViewById(R.id.board)
        manTurnHolder = findViewById(R.id.man_turn_iv)
        womanTurnHolder = findViewById(R.id.woman_turn_iv)
    }

    private fun initOmokController() {
        omokController = AndroidOmokController(
            ConsoleOmokController(this, this),
            OmokRepository(OmokDatabase.getInstance(this)),
        )
    }

    private fun initOmokIntersectionClickListener() {
        boardImageViews().forEachIndexed { index, omokIntersection ->
            val row: Int = index / Omok.OMOK_BOARD_SIZE
            val col: Int = index % Omok.OMOK_BOARD_SIZE
            omokIntersection.setOnClickListener {
                lifecycleScope.launch { omokController.putStone(row, col) }
            }
        }
    }

    private fun continuePreviousGame() {
        lifecycleScope.launch(Dispatchers.IO) {
            omokController.loadPreviousPoints { points, pointsCount ->
                showLatestTurnHolder(pointsCount)
                drawPreviousPoints(points)
            }
        }
    }

    private fun showLatestTurnHolder(pointsCount: Int) {
        val latestColor = StoneColorModel.calcLatestTurn(pointsCount + 1)
        toggleTurnHolder(latestColor ?: StoneColorModel.WHITE)
    }

    private fun drawPreviousPoints(points: List<Point>) {
        points.forEachIndexed { index, point ->
            val viewIndex = point.row * Omok.OMOK_BOARD_SIZE + point.col
            val omokIv: ImageView = boardImageViews()[viewIndex]
            val curColor = StoneColorModel.calcLatestTurn(index) ?: StoneColorModel.BLACK

            lifecycleScope.launch(Dispatchers.Main) { drawStoneOnBoard(omokIv, curColor) }
        }
    }

    private fun drawStoneOnBoard(view: ImageView, stoneColor: StoneColorModel) {
        when (stoneColor) {
            StoneColorModel.BLACK -> view.setImageResource(R.drawable.white_bear)
            StoneColorModel.WHITE -> view.setImageResource(R.drawable.pink_bear)
        }
    }

    override suspend fun readPosition(): Point = suspendCoroutine { continuation ->
        lifecycleScope.launch {
            continuation.resume(omokController.pointChannel.receive())
        }
    }

    override fun startGame() {
        showMessage(getString(R.string.omok_start_message))
        if (omokController.hasPreviousGameHistory()) showRestartDialog()
    }

    override fun drawStone(lastStoneColor: StoneColor, newPoint: Point) {
        omokController.savePoint(newPoint)
        val index = newPoint.row * Omok.OMOK_BOARD_SIZE + newPoint.col
        drawStoneOnBoard(boardImageViews()[index], lastStoneColor.toPresentation())
    }

    override fun showPutFailed() {
        showMessage(getString(R.string.inplace_stone))
    }

    override fun showResult(
        lastStoneColor: StoneColor,
        winnerStoneColor: StoneColor,
        newPoint: Point,
    ) {
        omokController.clearPoints()
        showWinner(winnerStoneColor.toPresentation())
    }

    override fun showCurrentTurnColor(curStoneColor: StoneColor, point: Point?) {
        point?.let {
            toggleTurnHolder(curStoneColor.next().toPresentation())
            findViewById<TextView>(R.id.last_point_tv).text =
                getString(R.string.last_put_point, it.col, it.row)
        }
    }

    private fun toggleTurnHolder(prevStoneColor: StoneColorModel) {
        lifecycleScope.launch(Dispatchers.Main) {
            manTurnHolder.visibility =
                if (prevStoneColor == StoneColorModel.WHITE) View.VISIBLE else View.GONE
            womanTurnHolder.visibility =
                if (prevStoneColor == StoneColorModel.BLACK) View.VISIBLE else View.GONE
        }
    }

    private fun showWinner(winnerColor: StoneColorModel) {
        val resultIntent = Intent(this, GameResultActivity::class.java)
            .putExtra(GameResultActivity.WINNER_KEY, winnerColor)
        startActivity(resultIntent)
        finish()
    }

    private fun showRestartDialog(): Unit = createAlertDialog {
        message(getString(R.string.restart_game_ask_message))
        positiveButton(getString(R.string.yes)) { continuePreviousGame() }
        negativeButton(getString(R.string.no)) { omokController.clearPoints() }
    }.show()

    private fun boardImageViews(): List<ImageView> = board.children
        .filterIsInstance<TableRow>()
        .flatMap { it.children }
        .filterIsInstance<ImageView>()
        .toList()
}
