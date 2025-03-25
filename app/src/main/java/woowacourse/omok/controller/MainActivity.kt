package woowacourse.omok.controller

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import omok.domain.omokboard.ColumnPosition
import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.PlayingBoard
import omok.domain.omokboard.Position
import omok.domain.omokboard.RowPosition
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import omok.domain.rule.place.AlreadyExistStoneRule
import omok.domain.rule.place.ExternalRule
import omok.domain.rule.place.InvalidPositionRule
import omok.domain.rule.place.PlaceResult
import omok.domain.rule.place.PlaceRule
import woowacourse.omok.R
import woowacourse.omok.domain.rule.judge.DrawRule
import woowacourse.omok.domain.rule.judge.JudgeResult
import woowacourse.omok.domain.rule.judge.JudgeRule
import woowacourse.omok.domain.rule.judge.WinningRule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val playingBoard = PlayingBoard(board = OmokBoard.create())
        val placeRules: List<PlaceRule> = listOf(InvalidPositionRule(), AlreadyExistStoneRule(), ExternalRule())
        val judgeRules: List<JudgeRule> = listOf(WinningRule(), DrawRule())

        val board = findViewById<TableLayout>(R.id.board)
        board
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
        board
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
                                board
                                    .children
                                    .filterIsInstance<TableRow>()
                                    .flatMap { it.children }
                                    .filterIsInstance<ImageView>()
                                    .forEach { button ->
                                        button.isEnabled = false
                                    }
                            }
                            Snackbar.make(findViewById(R.id.main), judgeResult.toString(), Snackbar.LENGTH_SHORT).show()
                            stoneColor = stoneColor.reversed()
                        }

                        is PlaceResult.Failure -> {
                            Snackbar.make(findViewById(R.id.main), placeResult.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
    }
}
