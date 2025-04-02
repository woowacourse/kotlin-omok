package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.data.db.DbHelper
import woowacourse.omok.data.db.DbProvider
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.OmokResult
import woowacourse.omok.domain.StoneColor
import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.OmokGrid
import woowacourse.omok.domain.grid.OmokGrid.Companion.DEFAULT_SIZE
import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.grid.Row
import woowacourse.omok.domain.grid.Stone
import woowacourse.omok.domain.rule.ValidationResult

class MainActivity : AppCompatActivity() {
    private val omokGame = OmokGame(OmokGrid())
    private var isGameOver = false
    private val dbProvider = DbProvider(DbHelper(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        initGame()
        var nowTurn = omokGame.getStartingPlayer()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()
            .forEachIndexed { index, view ->
                val row = index / DEFAULT_SIZE
                val col = index % DEFAULT_SIZE
                val point = Point(Row(row + INDEX_OFFSET), Column(col + INDEX_OFFSET))
                view.tag = point

                val pointColor = omokGame.grid.getStoneColorByPoint(point)
                if (pointColor != null) view.setImageResource(getStoneImage(pointColor))

                view.setOnClickListener {
                    if (isGameOver) return@setOnClickListener
                    nowTurn = proceedTurn(view, nowTurn)
                }
            }
    }

    // 디비에 저장된 돌 상태들을 들고 온다
    private fun initGame() {
        dbProvider.createTable()
        val stoneState = dbProvider.readAll()
        stoneState.forEach { stone ->
            omokGame.grid.putStone(stone)
        }
    }

    // 턴을 진행한다
    private fun proceedTurn(
        view: ImageView,
        stoneColor: StoneColor,
    ): StoneColor {
        val point = Stone(view.tag as Point, stoneColor)
        if (isViolation(stoneColor, point)) return stoneColor
        playMove(stoneColor, view)
        if (checkGameOver(stoneColor, point)) completeGame(stoneColor, point)
        return omokGame.changeTurn(stoneColor)
    }

    // 둘 수 있는 위치인지 확인한다
    private fun isViolation(
        stoneColor: StoneColor,
        point: Stone,
    ): Boolean {
        when (val result = omokGame.validatePoint(stoneColor, point)) {
            is ValidationResult.Success -> return false
            is ValidationResult.Failure -> showViolation(result)
        }
        return true
    }

    // 착수한다
    private fun playMove(
        stoneColor: StoneColor,
        view: ImageView,
    ) {
        view.setImageResource(getStoneImage(stoneColor))
        omokGame.playMove(Stone(view.tag as Point, stoneColor))
        dbProvider.insertStone(Stone(view.tag as Point, stoneColor))
    }

    // 돌 색깔별로 이미지를 받아온다
    private fun getStoneImage(stoneColor: StoneColor): Int {
        return when (stoneColor) {
            StoneColor.BLACK -> R.drawable.black_stone
            StoneColor.WHITE -> R.drawable.white_stone
        }
    }

    // 게임이 끝났는지 확인한다
    private fun checkGameOver(
        stoneColor: StoneColor,
        point: Stone,
    ): Boolean {
        return omokGame.checkWin(stoneColor, point) || omokGame.isBoardFull()
    }

    // 게임을 끝낸다
    private fun completeGame(
        stoneColor: StoneColor,
        point: Stone,
    ) {
        showGameResult(stoneColor, point)
        isGameOver = true
        dbProvider.dropTable()
    }

    // 게임 결과를 출력한다
    private fun showGameResult(
        stoneColor: StoneColor,
        point: Stone,
    ) {
        when {
            omokGame.checkWin(stoneColor, point) -> {
                showToast(MESSAGE_WINNER.format(OmokResult.getWinner(stoneColor)))
            }

            omokGame.isBoardFull() -> {
                showToast(MESSAGE_WINNER.format(OmokResult.DRAW))
            }
        }
    }

    private fun showViolation(violation: ValidationResult.Failure) {
        val errorText =
            when (violation) {
                ValidationResult.Failure.DoubleFour -> getString(R.string.error_double_four)
                ValidationResult.Failure.DoubleThree -> getString(R.string.error_double_three)
                ValidationResult.Failure.Occupied -> getString(R.string.error_occupied)
                ValidationResult.Failure.OverLine -> getString(R.string.error_over_line)
            }

        showToast(errorText)
    }

    private fun showToast(toastText: String) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        dbProvider.closeDB()
        super.onDestroy()
    }

    companion object {
        private const val INDEX_OFFSET: Int = 1
        private const val MESSAGE_WINNER = "%s !!"
    }
}
