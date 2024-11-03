package rachman.forniandi.dicodingeventstracker.domain.upcoming

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import rachman.forniandi.dicodingeventstracker.adapters.EventsAdapter
import rachman.forniandi.dicodingeventstracker.data.remoteUtils.RemoteResponse
import rachman.forniandi.dicodingeventstracker.databinding.FragmentUpcomingEventsBinding
import rachman.forniandi.dicodingeventstracker.domain.entity.Events
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.UpcomingEventsViewmodel

@AndroidEntryPoint
class UpcomingEventsFragment : Fragment() {
    private var _binding:FragmentUpcomingEventsBinding?= null
    private val binding get() = _binding!!
    private val eventAdapter by lazy { EventsAdapter(requireActivity()) }
    private val activeValue:Int=1
    private val upcomingEventsViewmodel:UpcomingEventsViewmodel by viewModels ()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventsBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState === null){
            upcomingEventsViewmodel.setValueActive(activeValue)
        }

        setListUpComingEvent()
        setSwipeRefreshEvent()
        showDataUpComingEvent()
        binding.swipeRefreshUpcomingEvent.isRefreshing = true

        Log.e("TAG", "Ini adalah halaman upcomingEventFragment")
    }

    private fun showDataUpComingEvent() {
        upcomingEventsViewmodel.setValueActive(activeValue)
        upcomingEventsViewmodel.upcomingEvent.observe(viewLifecycleOwner,eventObserver)
        }

    private val eventObserver = Observer<RemoteResponse<List<Events>?>?>{ response->
        when (response){
            is RemoteResponse.Loading->{
                showShimmerEffect()
            }

            is RemoteResponse.Success->{
                hideShimmerEffect()
                val events = response.data
                Log.d("test_data_event:",""+events)
                if (events != null) {
                    if (events.isEmpty()){
                        binding.swipeRefreshUpcomingEvent.isRefreshing = false
                        binding.imgDataEmpty.visibility= View.VISIBLE
                        binding.txtLblEventNotAvailable.visibility=View.VISIBLE
                    }else{
                        events.let { eventAdapter.setData(it) }
                        binding.swipeRefreshUpcomingEvent.isRefreshing = false
                        binding.imgDataEmpty.visibility= View.GONE
                        binding.txtLblEventNotAvailable.visibility=View.GONE
                    }


                }
            }
            is RemoteResponse.Error->{
                hideShimmerEffect()
                binding.swipeRefreshUpcomingEvent.isRefreshing = false
                binding.imgDataEmpty.visibility= View.VISIBLE
                binding.txtLblEventNotAvailable.visibility=View.VISIBLE
                binding.btnReloadPageUpcoming.visibility= View.VISIBLE
                binding.btnReloadPageUpcoming.isClickable = true
                binding.btnReloadPageUpcoming.setOnClickListener {
                    showDataUpComingEvent()
                }
                Toast.makeText(requireActivity() ,response.errorMessage, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    private fun setListUpComingEvent() {
        binding.rvEventsFuture.adapter = eventAdapter
        eventAdapter.setOnClickListener(object :EventsAdapter.OnEventClickListener{
            override fun onClick(position: Int, event: Events) {
                val toDetailEvent = UpcomingEventsFragmentDirections.actionUpcomingEventsFragmentToDetailEventsActivity(event)
                findNavController().navigate(toDetailEvent)
            }
        })
        showShimmerEffect()
    }

    private fun setSwipeRefreshEvent() {
        binding.swipeRefreshUpcomingEvent.setOnRefreshListener {
            showDataUpComingEvent()
            hideShimmerEffect()
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout1.startShimmer()
        binding.shimmerFrameLayout1.visibility = View.VISIBLE
        binding.rvEventsFuture.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout1.stopShimmer()
        binding.shimmerFrameLayout1.visibility = View.GONE
        binding.rvEventsFuture.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}