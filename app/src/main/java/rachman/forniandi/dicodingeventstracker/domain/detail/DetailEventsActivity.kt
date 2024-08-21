package rachman.forniandi.dicodingeventstracker.domain.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.DetailEventViewModel
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.UpcomingEventsViewmodel

@AndroidEntryPoint
class DetailEventsActivity : AppCompatActivity() {
    private val viewmodel: DetailEventViewModel by viewModels ()
    private var idEvent:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_events)


    }
}