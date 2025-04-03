package woowacourse.omok.ui.lobby

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.OmokApplication
import woowacourse.omok.data.dao.OmokGameDao
import woowacourse.omok.databinding.ActivityLobbyBinding
import woowacourse.omok.domain.model.omokboard.OmokGameEntity
import woowacourse.omok.domain.model.player.PlayerName
import woowacourse.omok.ui.game.OmokGameActivity
import woowacourse.omok.ui.mapper.toData
import woowacourse.omok.ui.mapper.toUI

class LobbyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLobbyBinding
    private lateinit var omokGameDao: OmokGameDao
    private val omokGameAdapter = OmokGameAdapter(::navigateToOmokGame)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLobbyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvLobbyRooms.adapter = omokGameAdapter

        omokGameDao = (applicationContext as OmokApplication).omokGameDao

        setupClickListener()
    }

    override fun onResume() {
        super.onResume()
        val games = omokGameDao.fetchAllGames()
        omokGameAdapter.submitList(games.toUI())
    }

    private fun setupClickListener() {
        createNewOmokRoom()
    }

    private fun createNewOmokRoom() {
        binding.fabLobbyCreateRoom.setOnClickListener {
            val newGame = OmokGameEntity(host = PlayerName.create())
            val newGameId = omokGameDao.createGame(newGame.toData())
            navigateToOmokGame(newGameId)
        }
    }

    private fun navigateToOmokGame(gameId: Int) {
        val intent = OmokGameActivity.getIntent(this, gameId)
        startActivity(intent)
    }
}
