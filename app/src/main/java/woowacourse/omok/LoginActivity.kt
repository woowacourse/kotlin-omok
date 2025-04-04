package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.model.Extras

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        setupLoginButton()
    }

    private fun setupLoginButton() {
        findViewById<Button>(R.id.loginButton).setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val nickname = getNicknameInput()
        navigateToRoomList(nickname)
        finish()
    }

    private fun getNicknameInput(): String {
        return findViewById<EditText>(R.id.nicknameInput).text.toString()
    }

    private fun navigateToRoomList(nickname: String) {
        val intent = Intent(this, RoomListActivity::class.java).apply {
            putExtra(Extras.NICKNAME, nickname)
        }
        startActivity(intent)
    }
}
