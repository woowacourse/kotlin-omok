package woowacourse.omok.ui.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.databinding.ActivityLobbyBinding

class LobbyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLobbyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLobbyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
