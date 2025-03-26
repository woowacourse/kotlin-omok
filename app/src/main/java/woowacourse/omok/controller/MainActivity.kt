package woowacourse.omok.controller

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.R
import woowacourse.omok.databinding.ActivityMainBinding
import woowacourse.omok.domain.omokboard.ColumnPosition
import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.omokboard.PlayingBoard
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.omokboard.RowPosition
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor
import woowacourse.omok.domain.rule.judge.DrawRule
import woowacourse.omok.domain.rule.judge.JudgeResult
import woowacourse.omok.domain.rule.judge.JudgeRule
import woowacourse.omok.domain.rule.judge.WinningRule
import woowacourse.omok.domain.rule.place.AlreadyExistStoneRule
import woowacourse.omok.domain.rule.place.ExternalRule
import woowacourse.omok.domain.rule.place.InvalidPositionRule
import woowacourse.omok.domain.rule.place.PlaceResult
import woowacourse.omok.domain.rule.place.PlaceRule

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()

        val playingBoard = PlayingBoard(OmokBoard.create())
        val placeRules = listOf(InvalidPositionRule(), AlreadyExistStoneRule(), ExternalRule())
        val judgeRules = listOf(WinningRule(), DrawRule())

        setupStoneTags()
        setupClickListeners(playingBoard, placeRules, judgeRules)
    }

    private fun setupView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupStoneTags() =
        with(binding.board) {
            children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
                row.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, button ->
                    button.tag = Position(RowPosition(rowIndex + 1), ColumnPosition(colIndex + 1))
                }
            }
        }

    private fun setupClickListeners(
        playingBoard: PlayingBoard,
        placeRules: List<PlaceRule>,
        judgeRules: List<JudgeRule>,
    ) = with(binding.board) {
        children.filterIsInstance<TableRow>().flatMap { it.children }.filterIsInstance<ImageView>().forEach { button ->
            button.setOnClickListener {
                val position = button.tag as Position
                val playerStone = PlayerStone(playingBoard.stoneColor, position)

                when (val result = playingBoard.placeStone(placeRules, position)) {
                    is PlaceResult.Success -> handlePlaceSuccess(button, playerStone, playingBoard, judgeRules)
                    is PlaceResult.Failure -> showSnackBar(getFailureMessage(result))
                }
            }
        }
    }

    private fun handlePlaceSuccess(
        button: ImageView,
        playerStone: PlayerStone,
        playingBoard: PlayingBoard,
        judgeRules: List<JudgeRule>,
    ) {
        button.setImageResource(
            when (playingBoard.stoneColor) {
                StoneColor.BLACK -> R.drawable.black_stone
                StoneColor.WHITE -> R.drawable.white_stone
            },
        )

        handleJudge(playingBoard, playerStone, judgeRules)
        playingBoard.reverseTurn()
    }

    private fun handleJudge(
        playingBoard: PlayingBoard,
        playerStone: PlayerStone,
        judgeRules: List<JudgeRule>,
    ) {
        val judgeResult = playingBoard.judge(judgeRules, playerStone)

        if (judgeResult is JudgeResult.Finished) {
            disableBoard()
            showResultDialog(getJudgeMessage(judgeResult))
        }
    }

    private fun disableBoard() =
        with(binding.board) {
            children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .forEach { it.isEnabled = false }
        }

    private fun showResultDialog(message: String) {
        AlertDialog
            .Builder(this)
            .setTitle("게임 종료")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("나가기") { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("알림 닫기") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun getJudgeMessage(result: JudgeResult.Finished): String =
        when (result) {
            is JudgeResult.Finished.Win -> {
                val color =
                    when (result.stone) {
                        StoneColor.BLACK -> "흑"
                        StoneColor.WHITE -> "백"
                    }
                "${color}의 우승을 축하드립니다!"
            }

            is JudgeResult.Finished.Draw -> "무승부!"
        }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun getFailureMessage(result: PlaceResult.Failure): String =
        when (result) {
            PlaceResult.Failure.AlreadyExistStone -> "이미 돌이 있는 자리에 둘 수 없습니다."
            PlaceResult.Failure.DoubleFourViolation -> "4 x 4은 금지입니다."
            PlaceResult.Failure.DoubleThreeViolation -> "3 x 3은 금지입니다."
            PlaceResult.Failure.InvalidPosition -> "잘못된 위치 입니다."
            PlaceResult.Failure.OverlineViolation -> "6목은 금지입니다."
        }
}
