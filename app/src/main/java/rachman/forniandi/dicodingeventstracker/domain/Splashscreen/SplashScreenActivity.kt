package rachman.forniandi.dicodingeventstracker.domain.Splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.domain.home.MainActivity
import java.util.Timer
import kotlin.concurrent.schedule

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Timer("splashGone", true).schedule(3000) {
            Intent(this@SplashScreenActivity, MainActivity::class.java).also { backToMain->
                startActivity(backToMain)
                finish()
            }
        }
    }
}