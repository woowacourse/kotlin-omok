package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        findViewById<Button>(R.id.loginButton).setOnClickListener {
            val nickname = findViewById<EditText>(R.id.nicknameInput).text.toString()
            startActivity(Intent(this, RoomListActivity::class.java).apply {
                putExtra("nickname", nickname)
            })
            finish()
        }
    }
}
