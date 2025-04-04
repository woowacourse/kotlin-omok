package woowacourse.omok.ui.lobby

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.databinding.ActivityLobbyBinding
import woowacourse.omok.domain.model.game.LobbyManager
import woowacourse.omok.ui.game.OmokGameActivity

class LobbyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLobbyBinding
    private lateinit var lobbyManager: LobbyManager
    private val omokGameAdapter = OmokGameAdapter(::navigateToOmokGame)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLobbyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lobbyManager = LobbyManager(applicationContext)

        binding.rvLobbyRooms.adapter = omokGameAdapter
        setupClickListener()
    }

    override fun onResume() {
        super.onResume()
        val games = lobbyManager.getAllGames()
        omokGameAdapter.submitList(games)
    }

    private fun setupClickListener() {
        binding.fabLobbyCreateRoom.setOnClickListener {
            val newGameId = lobbyManager.createNewGame()
            navigateToOmokGame(newGameId)
        }
    }

    private fun navigateToOmokGame(gameId: Int) {
        val intent = OmokGameActivity.getIntent(this, gameId)
        startActivity(intent)
    }
}
