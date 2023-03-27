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

    private val backButton by lazy { findViewById<Button>(R.id.back_button) }
    private val resetButton by lazy { findViewById<Button>(R.id.reset_button) }
    private val opposingPlayerImage by lazy { findViewById<ImageView>(R.id.opposing_player_image) }
    private val opposingPlayerName by lazy { findViewById<TextView>(R.id.opposing_player_name) }
    private val opposingPlayerScore by lazy { findViewById<TextView>(R.id.opposing_player_score) }
    private val playerImage by lazy { findViewById<ImageView>(R.id.player_image) }
    private val playerName by lazy { findViewById<TextView>(R.id.player_name) }
    private val playerScore by lazy { findViewById<TextView>(R.id.player_score) }
    private val omokBoard by lazy { findViewById<TableLayout>(R.id.board) }
    private val currentTurn by lazy { findViewById<TextView>(R.id.current_turn) }

    private lateinit var opposingPlayer: Player
    private lateinit var player: Player

    private val omokGameListener = object : OmokGameListener {
        override fun onStartGame() {
            Toast.makeText(applicationContext, "게임을 시작합니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onProgressGame(gameState: GameState, omokPoint: OmokPoint?) {
            drawBoard(gameState)
            recordGame(gameState, omokPoint)
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

    private val matrixBoard get() = omokBoard
        .children.filterIsInstance<TableRow>().flatMapIndexed { row, tableRow ->
            tableRow.children.filterIsInstance<ImageView>().mapIndexed { col, imageView ->
                Triple(col, row, imageView)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        setUpPlayers()
        omokController = OmokController(omokGameListener, boardDb.getGameState(gameId))
        listeners.forEach { listener -> listener() }
    }

    private fun drawBoard(gameState: GameState) {
        matrixBoard.forEach { (col, row, imageView) ->
            setStoneImage(gameState.omokBoard, imageView, OmokPoint(col + 1, row + 1))
        }
    }

    private fun recordGame(gameState: GameState, omokPoint: OmokPoint?) {
        omokPoint?.let { boardDb.insert(gameState, omokPoint, gameId) }
    }

    private fun setUpPlayers() {
        opposingPlayer = intent.getSerializableExtra("opposingPlayer") as Player
        updateViewOpposingPlayer(opposingPlayer)
        player = intent.getSerializableExtra("player") as Player
        updateViewPlayer(player)
    }

    private fun updateViewOpposingPlayer(player: Player) = player.run {
        opposingPlayerImage.setImageResource(profile)
        opposingPlayerName.text = name
        opposingPlayerScore.text = "$win 승 $lose 패 $draw 무"
    }

    private fun updateViewPlayer(player: Player) = player.run {
        playerImage.setImageResource(profile)
        playerName.text = name
        playerScore.text = "$win 승 $lose 패 $draw 무"
    }

    private fun updateGameInfo(gameState: GameState) {
        updateCurrentTurn(gameState)
        when (gameState) {
            is BlackWin -> updateBlackWin()
            is WhiteWin -> updateWhiteWin()
            else -> null
        }?.let { updateGameEnd() }
    }

    private fun updateCurrentTurn(gameState: GameState) {
        when (gameState) {
            is BlackTurn -> "흑돌 차례"
            is WhiteTurn -> "백돌 차례"
            is BlackWin -> "흑돌 승리"
            is WhiteWin -> "백돌 승리"
        }.let { currentTurn.text = it }
    }

    private fun updateBlackWin() {
        playerDb.update(opposingPlayer.copy(lose = opposingPlayer.lose + 1))
        playerDb.update(player.copy(win = player.win + 1))
        runRestartActivity(player)
    }

    private fun updateWhiteWin() {
        playerDb.update(opposingPlayer.copy(win = opposingPlayer.win + 1))
        playerDb.update(player.copy(lose = player.lose + 1))
        runRestartActivity(opposingPlayer)
    }

    private fun updateGameEnd() {
        opposingPlayer = playerDb.getPlayer(opposingPlayer.id) ?: opposingPlayer
        player = playerDb.getPlayer(player.id) ?: player
        resetGameState()
        updateViewOpposingPlayer(opposingPlayer)
        updateViewPlayer(player)
    }

    private fun addStoneListener() =
        matrixBoard.forEach { (col, row, imageView) ->
            imageView.setOnClickListener { omokController.run(OmokPoint(col + 1, row + 1)) }
        }

    private fun addListenerBackToRoomList() =
        backButton.setOnClickListener { finish() }

    private fun addListenerResetGameState() =
        resetButton.setOnClickListener { resetGameState() }

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
