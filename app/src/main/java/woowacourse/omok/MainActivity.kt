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
import omok.mapper.BlackRuleChecker
import omok.mapper.PointMapper
import omok.model.game.Game
import omok.model.stone.StoneColor
import omok.model.stone.position.Col
import omok.model.stone.position.Row
import omok.model.stone.position.Position
import rule.BlackRenjuRule
import woowacourse.omok.model.rule.PlacementError.AlreadyOccupiedViolation
import woowacourse.omok.model.rule.PlacementError.DoubleFourViolation
import woowacourse.omok.model.rule.PlacementError.DoubleThreeViolation
import woowacourse.omok.model.rule.PlacementError.NoViolation
import woowacourse.omok.model.rule.PlacementError.OverlineViolation

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

        val blackRuleChecker =
            BlackRuleChecker(
                rule = BlackRenjuRule(),
                mapper = { position -> PointMapper().from(position) },
            )

        val game = Game(blackRuleChecker)

        val board = findViewById<TableLayout>(R.id.board)
        val rows = board.children
            .filterIsInstance<TableRow>()
            .toList()
            .reversed()

        rows.forEachIndexed { rowIndex, row ->
            row.children
                .filterIsInstance<ImageView>()
                .forEachIndexed { colIndex, cell ->
                    cell.setOnClickListener {
                        val position = Position(Row(rowIndex), Col(colIndex))

                        val stoneRes =
                            if (game.turn == StoneColor.BLACK) R.drawable.black_stone else R.drawable.white_stone

                        val violation = game.playTurn(position)
                        val result = when(violation){
                            AlreadyOccupiedViolation -> "현재 위치에 돌이 있습니다"
                            DoubleThreeViolation -> "3-3 반칙이 발생했습니다"
                            DoubleFourViolation -> "4-4 반칙이 발생했습니다"
                            OverlineViolation -> "장목 반칙이 발생했습니다"
                            NoViolation -> ""
                        }

                        if (violation != NoViolation) {
                            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }

                        cell.setImageResource(stoneRes)

                        val winColor = when(game.lastStone?.stoneColor){
                            StoneColor.WHITE -> "백"
                            StoneColor.BLACK -> "흑"
                            null -> ""
                        }

                        if (game.isOmok()) {
                            Toast.makeText(
                                this,
                                "${winColor}이 우승했습니다!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
        }
    }
}