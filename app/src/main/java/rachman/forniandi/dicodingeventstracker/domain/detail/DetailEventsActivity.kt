package rachman.forniandi.dicodingeventstracker.domain.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.data.remoteUtils.RemoteResponse
import rachman.forniandi.dicodingeventstracker.databinding.ActivityDetailEventsBinding
import rachman.forniandi.dicodingeventstracker.domain.entity.Events
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.DetailEventViewModel
import rachman.forniandi.dicodingeventstracker.utils.animateLoadingProcessData

@AndroidEntryPoint
class DetailEventsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailEventsBinding
    private val navArgs:DetailEventsActivityArgs by navArgs()
    private val viewmodel: DetailEventViewModel by viewModels ()
    private var idEvent:Int=0
    private var linkEvent=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventsBinding.inflate(layoutInflater)
        idEvent = navArgs.idEvent.id!!
        
        setContentView(binding.root)
        if (savedInstanceState === null) {
            viewmodel.setValueId(idEvent)
        }
        
        setViewDetailOfEvents()
        
    }

    private fun setViewDetailOfEvents() {
        viewmodel.eventDetail.observe(this, detailObserver)
    }

    private val detailObserver = Observer<RemoteResponse<Events?>?> { response ->
        when(response){
            is RemoteResponse.Loading -> {
                applyLoadProgressStateDetail(true)
            }
            is RemoteResponse.Success -> {
                applyLoadProgressStateDetail(false)
                val detailEvent = response.data
                Picasso
                    .get()
                    .load(detailEvent?.imageLogo)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.error_placeholder)
                    .into(binding.imgBackDrop)
                linkEvent = detailEvent?.link.toString()
                setUpContentDetail(detailEvent)
                binding.detailEventMain.btnRegisterEvent.isEnabled = true
                setupFunctionalLinkDetailEvent(linkEvent)

            }
            is RemoteResponse.Error -> {
                applyLoadProgressStateDetail(false)
                Toast.makeText(this,response.errorMessage, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun setupFunctionalLinkDetailEvent(linkEvent: String) {
        binding.detailEventMain.btnRegisterEvent.setOnClickListener {
            val toLinkPage =Intent(this,RegisterEventActivity::class.java)
            toLinkPage.putExtra(REGISTER_EVENT,linkEvent)
            startActivity(toLinkPage)
            finish()
        }
    }


    private fun setUpContentDetail(detailEvent: Events?) {
        Picasso
            .get()
            .load(detailEvent?.imageLogo)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.error_placeholder)
            .into(binding.detailEventMain.imgLogoEvent)
        binding.detailEventMain.txtKindOfEvent.text = detailEvent?.category
        binding.detailEventMain.txtTitleEventDetail.text=detailEvent?.name
        binding.detailEventMain.txtHostedBy.text=detailEvent?.ownerName
        binding.detailEventMain.tvStartTime.text = detailEvent?.beginTime
        binding.detailEventMain.tvEndTime.text = detailEvent?.endTime

        if (detailEvent?.cityName.equals("Online")){
            binding.detailEventMain.tvLocation.text="LIVE at YouTube Dicoding Indonesia ${detailEvent?.cityName}"
        }else{
            binding.detailEventMain.tvLocation.text=detailEvent?.cityName
        }

        val quota = detailEvent?.quota
        val registrants = detailEvent?.registrants

        val remainingParticipants = registrants?.let { quota?.minus(it) }
        binding.detailEventMain.tvRemainingQuotaParticipants.text = remainingParticipants.toString()
        binding.detailEventMain.tvSummaryEvent.text = detailEvent?.summary

        binding.detailEventMain.tvDescription.text = Jsoup.parse(detailEvent?.description).text()
    }


    private fun applyLoadProgressStateDetail(onProcess:Boolean){

        binding.detailEventMain.btnRegisterEvent.isEnabled = !onProcess

        if (onProcess){
            binding.maskedViewPgDetail.animateLoadingProcessData(true)
        }else{
            binding.maskedViewPgDetail.animateLoadingProcessData(false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object{
        const val REGISTER_EVENT="register_event"
    }
}