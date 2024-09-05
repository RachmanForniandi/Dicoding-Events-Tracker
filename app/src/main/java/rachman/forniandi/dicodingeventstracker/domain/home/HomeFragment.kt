package rachman.forniandi.dicodingeventstracker.domain.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.adapters.CarrouselEventsAdapter
import rachman.forniandi.dicodingeventstracker.adapters.EventsAdapter
import rachman.forniandi.dicodingeventstracker.data.remoteUtils.RemoteResponse
import rachman.forniandi.dicodingeventstracker.databinding.FragmentHomeBinding
import rachman.forniandi.dicodingeventstracker.domain.entity.Events
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.HomeViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding?= null
    private val binding get() = _binding!!
    private val carrouselEventAdapter by lazy { CarrouselEventsAdapter(requireActivity()) }
    private val eventAdapter by lazy { EventsAdapter(requireActivity()) }
    private val upcomingValue=1
    private val pastValue=0
    private lateinit var viewmodel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewmodel = ViewModelProvider(this)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState === null){
            viewmodel.setValueActiveUpcoming(upcomingValue)
            viewmodel.setValueActivePast(pastValue)
        }
        setupAppBarMain()
        setupListUpcomingEvent()
        showDataRemoteUpcomingEvent()
        setupListPastEvent()
        showDataRemotePastEvent()
        Log.e("TAG", "Ini adalah halaman homeFragment")


    }

    private fun setupAppBarMain() {
        binding.apply {
            homeToolbar.inflateMenu(R.menu.menu_main)
            homeToolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_setting ->{
                        val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                        findNavController().navigate(action)
                    }
                }
                false
            }
        }
    }

    private fun showDataRemoteUpcomingEvent() {
        viewmodel.setValueActiveUpcoming(upcomingValue)
        viewmodel.upcomingEvent.observe(viewLifecycleOwner,eventObserverUpComingEvent)
    }

    private fun showDataRemotePastEvent() {
        viewmodel.setValueActivePast(pastValue)
        viewmodel.pastEvent.observe(viewLifecycleOwner,eventObserverPastEvent)
    }



    private val eventObserverUpComingEvent = Observer<RemoteResponse<List<Events>?>?>{ response->
        when (response){
            is RemoteResponse.Loading->{
                showShimmerEffect()
            }

            is RemoteResponse.Success->{
                hideShimmerEffect()
                val events = response.data
                Log.d("test_data_event_1:",""+events)
                if (events != null) {
                    if (events.isEmpty()){
                        binding.imgDataEmpty1.visibility=View.VISIBLE
                        binding.txtNoDataEventUpcomingAvailable.visibility=View.VISIBLE
                    }else{
                        events.let {carrouselEventAdapter.setData(it)}
                        binding.txtNoDataEventUpcomingAvailable.visibility=View.INVISIBLE
                    }
                }
            }
            is RemoteResponse.Error->{
                hideShimmerEffect()
                binding.imgDataEmpty1.visibility=View.VISIBLE
                binding.txtNoDataEventUpcomingAvailable.visibility=View.VISIBLE
                Toast.makeText(requireActivity() ,response.errorMessage, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    private val eventObserverPastEvent = Observer<RemoteResponse<List<Events>?>?>{ response->
        when (response){
            is RemoteResponse.Loading->{
                showShimmerEffect2()
            }

            is RemoteResponse.Success->{
                hideShimmerEffect2()
                val events = response.data
                Log.d("test_data_event_2:",""+events)
                if (events != null) {
                    if (events.isEmpty()){
                        binding.imgDataEmpty2.visibility=View.VISIBLE
                        binding.txtNoDataEventPastAvailable.visibility=View.VISIBLE
                    }else{
                        events.let { eventAdapter.setData(it) }
                        binding.txtNoDataEventPastAvailable.visibility=View.INVISIBLE
                    }
                }
            }
            is RemoteResponse.Error->{
                hideShimmerEffect2()
                binding.imgDataEmpty2.visibility=View.VISIBLE
                binding.txtNoDataEventPastAvailable.visibility=View.VISIBLE
                Toast.makeText(requireActivity() ,response.errorMessage, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    private fun setupListUpcomingEvent() {
        binding.rvEventsFuture.adapter = carrouselEventAdapter
        carrouselEventAdapter.setOnClickListener(object :CarrouselEventsAdapter.OnEventClickListener{
            override fun onClick(position: Int, event: Events) {
                val toDetailEvent = HomeFragmentDirections.actionHomeFragmentToDetailEventsActivity(event)
                findNavController().navigate(toDetailEvent)
            }
        })
        showShimmerEffect()
    }

    private fun setupListPastEvent() {
        binding.rvEventsFinished.adapter = eventAdapter
        eventAdapter.setOnClickListener(object :EventsAdapter.OnEventClickListener{
            override fun onClick(position: Int, event: Events) {
                val toDetailEvent = HomeFragmentDirections.actionHomeFragmentToDetailEventsActivity(event)
                findNavController().navigate(toDetailEvent)
            }
        })
        showShimmerEffect2()
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayoutCarrouselEvent.startShimmer()
        binding.shimmerFrameLayoutCarrouselEvent.visibility = View.VISIBLE
        binding.rvEventsFuture.visibility = View.INVISIBLE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayoutCarrouselEvent.stopShimmer()
        binding.shimmerFrameLayoutCarrouselEvent.visibility = View.INVISIBLE
        binding.rvEventsFuture.visibility = View.VISIBLE

    }

    private fun showShimmerEffect2() {
        binding.shimmerFrameLayoutEventFinished.startShimmer()
        binding.shimmerFrameLayoutEventFinished.visibility = View.VISIBLE
        binding.rvEventsFinished.visibility = View.INVISIBLE
    }

    private fun hideShimmerEffect2() {
        binding.shimmerFrameLayoutEventFinished.stopShimmer()
        binding.shimmerFrameLayoutEventFinished.visibility = View.INVISIBLE
        binding.rvEventsFinished.visibility = View.VISIBLE

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}