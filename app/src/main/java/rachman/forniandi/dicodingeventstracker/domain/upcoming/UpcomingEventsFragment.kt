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
import androidx.lifecycle.ViewModelProvider
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
                events?.let { eventAdapter.setData(it) }
            }
            is RemoteResponse.Error->{
                hideShimmerEffect()
                Toast.makeText(requireActivity() ,response.errorMessage, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    private fun setListUpComingEvent() {
        binding.rvEventsFuture.adapter = eventAdapter
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