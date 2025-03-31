package woowacourse.omok.view.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.data.OmokDatabaseHelper
import woowacourse.omok.data.dao.GamesDao
import woowacourse.omok.domain.Game
import woowacourse.omok.view.MainActivity
import woowacourse.omok.view.omok.OmokFragment

class GamesFragment :
    Fragment(),
    OnGameClickListener {
    private lateinit var gamesRvAdapter: GamesRvAdapter
    private lateinit var gamesDao: GamesDao
    private lateinit var newGameDialog: NewGameDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_games, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initializeDb()
        initializeDialog()
        setupListeners()
        initializeRecyclerView()
    }

    private fun initializeDb() {
        val dbHelper = OmokDatabaseHelper(requireContext())
        gamesDao = GamesDao(dbHelper)
    }

    private fun setupListeners() {
        requireView().findViewById<Button>(R.id.btn_game_create).setOnClickListener {
            newGameDialog.show()
        }
    }

    private fun initializeDialog() {
        newGameDialog =
            NewGameDialog(requireContext()) { roomName ->
                createGame(roomName)
            }
    }

    private fun createGame(roomName: String) {
        gamesDao
            .createGame(roomName)
            .onSuccess {
                navigateToOmokFragment(it)
            }
    }

    private fun initializeRecyclerView() {
        gamesDao.getGames().onSuccess { games ->
            setupRecyclerView(games)
        }
    }

    private fun setupRecyclerView(games: List<Game>) {
        gamesRvAdapter = GamesRvAdapter(games, this)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        requireView().findViewById<RecyclerView>(R.id.rv_games).apply {
            adapter = gamesRvAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL,
                ),
            )
        }
    }

    override fun enterGame(
        gameId: Int,
        isFinished: Boolean,
    ) {
        navigateToOmokFragment(gameId, isFinished)
    }

    override fun deleteGame(gameId: Int) {
        deleteGameFromDb(gameId)
    }

    private fun navigateToOmokFragment(
        gameId: Int,
        isFinished: Boolean = false,
    ) {
        val bundle = Bundle()
        bundle.putInt(OmokFragment.ARGUMENT_KEY_NAME_GAME_ID, gameId)
        bundle.putBoolean(OmokFragment.ARGUMENT_KEY_NAME_GAME_FINISHED, isFinished)

        (requireActivity() as? MainActivity)?.replaceFragment(
            OmokFragment().apply { arguments = bundle },
            "game-$gameId",
        )
    }

    private fun deleteGameFromDb(gameId: Int) {
        gamesDao.deleteGame(gameId).onSuccess {
            gamesRvAdapter.updateGames(gameId)
        }
    }
}
