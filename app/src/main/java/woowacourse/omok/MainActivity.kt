package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omock.model.GameTurn
import omock.model.board.Board
import omock.model.player.BlackPlayer
import omock.model.player.Player
import omock.model.player.WhitePlayer
import omock.model.position.Column
import omock.model.position.Row
import omock.model.rule.LoadMap
import omock.model.rule.OMockRule
import omock.model.ruletype.FourToFourCount
import omock.model.ruletype.IsClearFourToFourCount
import omock.model.ruletype.IsReverseTwoAndThree
import omock.model.ruletype.ThreeToThreeCount
import omock.model.search.VisitedDirectionFirstClearResult
import omock.model.search.VisitedDirectionResult
import omock.model.stone.Stone
import omock.view.OutputView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val oMockRule = OMockRule(
            ruleTypes = listOf(
                ThreeToThreeCount,
                FourToFourCount,
                IsClearFourToFourCount,
                IsReverseTwoAndThree,
            )
        )
        val mainBoard = findViewById<TableLayout>(R.id.board)
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        val blackPlayer = BlackPlayer()
        val whitePlayer = WhitePlayer()

        OutputView.outputGameStart()

        fun getStoneIcon(player: Player): Int {
            return when (player) {
                is BlackPlayer -> R.drawable.black_stone
                is WhitePlayer -> R.drawable.white_stone
            }
        }

        fun playerPutStone(
            player: Player,
            playerStone: Stone,
        ): Result<Unit> {
            return runCatching {
                board.setStoneState(player, playerStone)
            }
        }

        fun applyPlayerSelected(
            player: Player,
            playerStone: Stone,
        ): Result<Unit> {
            return runCatching {
                val visitedDirectionResult = VisitedDirectionResult(loadMap.loadMap(playerStone))
                val visitedDirectionFirstClearResult =
                    VisitedDirectionFirstClearResult(loadMap.firstClearLoadMap(playerStone))
                oMockRule.checkPlayerRules(
                    player,
                    visitedDirectionResult,
                    visitedDirectionFirstClearResult
                )
                board.applyPlayerJudgement(player, visitedDirectionResult)
            }
        }

        fun start(
            player: Player,
            playerStone: Stone,
            view: ImageView,
        ) {
            playerPutStone(player,playerStone)
                .onSuccess {
                    applyPlayerSelected(player, playerStone)
                        .onSuccess {
                            player.stoneHistory.add(playerStone)
                            view.setImageResource(getStoneIcon(player))
                        }.onFailure { throwable ->
                            OutputView.outputFailureMessage(throwable)
                            board.rollbackState(playerStone)
                        }
                }
                .onFailure { throwable ->
                    OutputView.outputFailureMessage(throwable)
                }
        }

        fun userTurnFlow(
            player: Player,
            playerPick: Pair<String, String>,
            view: ImageView,
        ) {
            OutputView.outputUserTurn(player)
            OutputView.outputLastStone(player.stoneHistory.lastOrNull())
            val playerStone = player.turn {
                playerPick
            }
            start(player = player, playerStone, view)
        }

        mainBoard
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, rows ->
                rows.children.filterIsInstance<ImageView>()
                    .forEachIndexed { columIndex, view ->
                        view.setOnClickListener {
                            OutputView.outputBoardForm()
                            val playerPick =
                                Column.transformIndex(columIndex) to Row.transformIndex(rowIndex)
                            when (board.getTurn()) {
                                GameTurn.BLACK_TURN -> userTurnFlow(blackPlayer, playerPick, view)
                                GameTurn.WHITE_TURN -> userTurnFlow(whitePlayer, playerPick, view)
                                GameTurn.FINISHED -> OutputView.outputSuccessOMock()
                            }
                        }
                    }
            }

    }
}
