package woowacourse.omok.view.games

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.data.OmokDatabaseHelper
import woowacourse.omok.data.dao.GamesDao
import woowacourse.omok.domain.Game
import woowacourse.omok.view.omok.OmokFragment

class GamesFragment : Fragment() {
    private lateinit var gamesRvAdapter: GamesRvAdapter
    private lateinit var gamesDao: GamesDao

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
        setupListeners()
        initializeRecyclerView()
    }

    private fun initializeDb() {
        val dbHelper = OmokDatabaseHelper(requireContext())
        gamesDao = GamesDao(dbHelper)
    }

    private fun setupListeners() {
        requireView().findViewById<Button>(R.id.btn_game_create).setOnClickListener {
            showCreateGameDialog()
        }
    }

    private fun showCreateGameDialog() {
        val input =
            EditText(requireContext()).apply {
                inputType = InputType.TYPE_CLASS_TEXT
            }

        AlertDialog
            .Builder(requireContext())
            .apply {
                setTitle(R.string.dialog_create_game_title)
                setView(input)
                setPositiveButton(R.string.dialog_create_game_button_positive) { _, _ ->
                    val roomName = input.text.toString()
                    createGame(roomName)
                }
                setNegativeButton(R.string.dialog_create_game_button_negative) { dialog, _ ->
                    dialog.cancel()
                }
            }.show()
    }

    private fun createGame(roomName: String) {
        gamesDao.createGame(roomName).onSuccess {
            navigateToOmokFragment(it)
        }
    }

    private fun navigateToOmokFragment(gameId: Int) {
        val bundle = Bundle()
        bundle.putInt(OmokFragment.ARGUMENT_KEY_NAME_GAME_ID, gameId)

        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_main, OmokFragment().apply { arguments = bundle })
            addToBackStack(null)
            commit()
        }
    }

    private fun initializeRecyclerView() {
        gamesDao.getGames().onSuccess { games ->
            setupRecyclerView(games)
        }
    }

    private fun setupRecyclerView(games: List<Game>) {
        gamesRvAdapter =
            GamesRvAdapter(
                games,
                object : GamesRvAdapter.OnGameClickListener {
                    override fun enterGame(gameId: Int) {
                        navigateToOmokFragment(gameId)
                    }

                    override fun deleteGame(gameId: Int) {
                        deleteGameFromDb(gameId)
                    }
                },
            )

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

    private fun deleteGameFromDb(gameId: Int) {
        gamesDao.deleteGame(gameId).onSuccess {
            gamesRvAdapter.updateGames(gameId)
        }
    }
}
