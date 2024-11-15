package rachman.forniandi.dicodingeventstracker.domain.Splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.AndroidEntryPoint
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.domain.home.MainActivity
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.SplashScreenViewModel
import java.util.Timer
import kotlin.concurrent.schedule

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Timer("splashGone", true).schedule(3000) {
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }
        viewModel.getNightTheme().observe(this@SplashScreenActivity){ isActiveTheme->
            if (isActiveTheme){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            viewModel.setNightTheme(isActiveTheme)
        }
    }

}