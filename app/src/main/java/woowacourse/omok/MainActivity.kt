package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import omok.model.rule.OmokRuleManager
import omok.model.rule.count.OverlineRule
import omok.model.rule.lib.DoubleFourMoveRule
import omok.model.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.database.OmokGameDao
import woowacourse.omok.database.SavedStone
import woowacourse.omok.model.OmokGame
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.PlaceStoneResult
import woowacourse.omok.model.board.Point

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private val omokGameDao: OmokGameDao by lazy {
        OmokGameDao(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBoardUI()

        val savedStones = omokGameDao.loadGameState()
        val size = BoardSize.OMOK_BOARD_SIZE
        val rules = getRules()

        omokGame = OmokGame(rules, BoardSize(size), savedStones)

        updateBoardUI(savedStones)
    }

    override fun onDestroy() {
        omokGameDao.close()
        super.onDestroy()
    }

    private fun getRules(): OmokRuleManager =
        OmokRuleManager.apply {
            forbiddenMoveRule.add(OverlineRule())
            forbiddenMoveRule.add(DoubleThreeMoveRule())
            forbiddenMoveRule.add(DoubleFourMoveRule())
        }

    private fun setupBoardUI() {
        val boardView = findViewById<TableLayout>(R.id.board)
        boardView.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, imageView ->
                        imageView.setImageDrawable(null)
                        imageView.tag = Point(rowIndex + 1, colIndex + 1)
                        imageView.setOnClickListener {
                            playWithTurn(imageView, rowIndex + 1, colIndex + 1)
                        }
                    }
            }
    }

    private fun updateBoardUI(savedStones: List<SavedStone>) {
        val boardView = findViewById<TableLayout>(R.id.board)

        savedStones.forEach { stone ->
            val imageView = boardView.findViewWithTag<ImageView>(Point(stone.x, stone.y))
            setStoneImage(imageView, stone.color)
        }
    }

    private fun playWithTurn(
        view: ImageView,
        x: Int,
        y: Int,
    ) {
        val currentTurn = omokGame.currentStoneColor
        val result = omokGame.placeStone(x, y)

        when (result) {
            is PlaceStoneResult.Success -> {
                setStoneImage(view, currentTurn)
                omokGameDao.saveStone(x, y, currentTurn)
            }

            is PlaceStoneResult.Omok -> {
                handleGameWin(currentTurn)
                omokGameDao.saveStone(x, y, currentTurn)
            }

            is PlaceStoneResult.AlreadyPlaced -> showToast(getString(R.string.error_already_placed))

            is PlaceStoneResult.ForbiddenMove -> showToast(getString(R.string.error_forbidden_move))
        }
    }

    private fun setStoneImage(
        view: ImageView,
        turn: StoneColor,
    ) {
        when (turn) {
            StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
            StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun handleGameWin(turn: StoneColor) {
        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.game_end_title))
            .setMessage(getString(R.string.game_win_message, turn))
            .setPositiveButton(getString(R.string.restart_button)) { _, _ ->
                resetGame()
            }.setNegativeButton(getString(R.string.exit_button)) { _, _ ->
                disableBoardTouch()
                omokGameDao.clearGameData()
            }.setCancelable(false)
            .show()
    }

    private fun resetGame() {
        omokGame.resetGame()
        omokGameDao.clearGameData()
        setupBoardUI()
    }

    private fun disableBoardTouch() {
        val boardView = findViewById<TableLayout>(R.id.board)
        boardView.children
            .filterIsInstance<TableRow>()
            .forEach { row ->
                row.children.filterIsInstance<ImageView>().forEach { imageView ->
                    imageView.setOnClickListener(null)
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
