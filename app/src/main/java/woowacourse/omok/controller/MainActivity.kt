package woowacourse.omok.controller

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
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
        val placeRules: List<PlaceRule> = listOf(InvalidPositionRule(), AlreadyExistStoneRule(), ExternalRule())
        val judgeRules: List<JudgeRule> = listOf(WinningRule(), DrawRule())

        binding.board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, button ->
                        button.id = View.generateViewId()
                        button.tag = Position(RowPosition(rowIndex + 1), ColumnPosition(colIndex + 1))
                    }
            }

        var stoneColor = StoneColor.BLACK
        binding.board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { button ->
                button.setOnClickListener {
                    val position = button.tag as Position
                    val playerStone = PlayerStone(stoneColor, position)
                    when (val placeResult = playingBoard.placeStone(placeRules, playerStone)) {
                        is PlaceResult.Success -> {
                            button.setImageResource(
                                when (stoneColor) {
                                    StoneColor.BLACK -> R.drawable.black_stone
                                    StoneColor.WHITE -> R.drawable.white_stone
                                },
                            )
                            val judgeResult = playingBoard.judge(judgeRules, playerStone)
                            if (judgeResult is JudgeResult.Finished) {
                                binding.board
                                    .children
                                    .filterIsInstance<TableRow>()
                                    .flatMap { it.children }
                                    .filterIsInstance<ImageView>()
                                    .forEach { button ->
                                        button.isEnabled = false
                                    }
                            }
                            Snackbar.make(binding.root, judgeResult.toString(), Snackbar.LENGTH_SHORT).show()
                            stoneColor = stoneColor.reversed()
                        }

                        is PlaceResult.Failure -> {
                            Snackbar.make(binding.root, placeResult.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
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
}
