package woowacourse.omok.room

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.controller.OmokController
import omok.domain.OmokBoard
import omok.domain.OmokGameListener
import omok.domain.OmokPoint
import omok.domain.gameState.BlackTurn
import omok.domain.gameState.BlackWin
import omok.domain.gameState.GameState
import omok.domain.gameState.WhiteTurn
import omok.domain.gameState.WhiteWin
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState
import woowacourse.omok.R
import woowacourse.omok.data.Player
import woowacourse.omok.dbHelper.OmokBoardDbHelper
import woowacourse.omok.dbHelper.OmokPlayerDbHelper

class RoomActivity : AppCompatActivity() {
    private val boardDb = OmokBoardDbHelper(this)
    private val playerDb = OmokPlayerDbHelper(this)
    private val gameId: Int by lazy { intent.getIntExtra("gameId", 0) }
    private lateinit var opposingPlayer: Player
    private lateinit var player: Player

    private val omokGameListener = object : OmokGameListener {
        override fun onStartGame() {
            Toast.makeText(applicationContext, "게임을 시작합니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onProgressGame(gameState: GameState, omokPoint: OmokPoint?) {
            matrixBoard.forEach { (col, row, imageView) ->
                setStoneImage(gameState.omokBoard, imageView, OmokPoint(col + 1, row + 1))
            }
            omokPoint?.let { boardDb.insert(gameState, omokPoint, gameId) }

            updateCurrentTurn(gameState)
            updateGameInfo(gameState)
        }

        override fun onError(message: String?) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var omokController: OmokController

    private val listeners = listOf<() -> Unit>(
        ::addStoneListener,
        ::addListenerResetGameState,
        ::addListenerBackToRoomList,
    )

    private val matrixBoard get() = findViewById<TableLayout>(R.id.board)
        .children.filterIsInstance<TableRow>().flatMapIndexed { row, tableRow ->
            tableRow.children.filterIsInstance<ImageView>().mapIndexed { col, imageView ->
                Triple(col, row, imageView)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        opposingPlayer = intent.getSerializableExtra("opposingPlayer") as Player
        player = intent.getSerializableExtra("player") as Player

        omokController = OmokController(omokGameListener, boardDb.getGameState(gameId))

        setUpOpposingPlayer(opposingPlayer)
        setUpPlayer(player)

        listeners.forEach { listener -> listener() }
    }

    private fun updateCurrentTurn(gameState: GameState) {
        when (gameState) {
            is BlackTurn -> "흑돌 차례"
            is WhiteTurn -> "백돌 차례"
            is BlackWin -> "흑돌 승리"
            is WhiteWin -> "백돌 승리"
        }.let { findViewById<TextView>(R.id.turn).text = it }
    }
    private fun updateGameInfo(gameState: GameState) {
        when (gameState) {
            is BlackWin -> {
                playerDb.update(opposingPlayer.copy(lose = opposingPlayer.lose + 1))
                playerDb.update(player.copy(win = player.win + 1))
                runRestartActivity(player)
            }
            is WhiteWin -> {
                playerDb.update(opposingPlayer.copy(win = opposingPlayer.win + 1))
                playerDb.update(player.copy(lose = player.lose + 1))
                runRestartActivity(opposingPlayer)
            }
            else -> null
        }?.let {
            opposingPlayer = playerDb.getPlayer(opposingPlayer.id) ?: opposingPlayer
            player = playerDb.getPlayer(player.id) ?: player
            resetGameState()
        }

        setUpOpposingPlayer(opposingPlayer)
        setUpPlayer(player)
    }

    private fun setUpOpposingPlayer(player: Player) = player.run {
        findViewById<ImageView>(R.id.opposing_player_image).setImageResource(profile)
        findViewById<TextView>(R.id.opposing_player_name).text = name
        findViewById<TextView>(R.id.opposing_player_score).text = "$win 승 $lose 패 $draw 무"
    }

    private fun setUpPlayer(player: Player) = player.run {
        findViewById<ImageView>(R.id.player_image).setImageResource(profile)
        findViewById<TextView>(R.id.player_name).text = name
        findViewById<TextView>(R.id.player_score).text = "$win 승 $lose 패 $draw 무"
    }

    private fun addStoneListener() =
        matrixBoard.forEach { (col, row, imageView) ->
            imageView.setOnClickListener { omokController.run(OmokPoint(col + 1, row + 1)) }
        }

    private fun addListenerBackToRoomList() =
        findViewById<Button>(R.id.back_button).setOnClickListener { finish() }

    private fun addListenerResetGameState() =
        findViewById<Button>(R.id.resetButton).setOnClickListener { resetGameState() }

    private fun runRestartActivity(winner: Player) {
        startActivity(
            Intent(this, RestartActivity::class.java).apply {
                putExtra("winner", winner)
            },
        )
    }
    private fun resetGameState() {
        boardDb.delete(gameId)
        omokController = OmokController(omokGameListener)
        matrixBoard.forEach { (_, _, imageView) -> imageView.setImageResource(0) }
    }

    private fun setStoneImage(omokBoard: OmokBoard, imageView: ImageView, omokPoint: OmokPoint) =
        when (omokBoard[omokPoint]) {
            BlackStoneState -> R.drawable.black_stone
            WhiteStoneState -> R.drawable.white_stone
            else -> null
        }?.let { imageView.setImageResource(it) }

    override fun onDestroy() {
        super.onDestroy()
        boardDb.close()
        playerDb.close()
    }
}
