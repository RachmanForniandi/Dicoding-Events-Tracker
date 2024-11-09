package rachman.forniandi.dicodingeventstracker.domain.favorite

import android.app.AlertDialog.Builder
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.adapters.FavoriteEventsAdapter
import rachman.forniandi.dicodingeventstracker.databinding.FragmentFavoriteEventsBinding
import rachman.forniandi.dicodingeventstracker.domain.entity.Events
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.FavoriteEventsViewModel

@AndroidEntryPoint
class FavoriteEventsFragment : Fragment() {

    private var _binding: FragmentFavoriteEventsBinding?= null
    private val binding get() = _binding!!
    private val adapter by lazy { FavoriteEventsAdapter() }
    private lateinit var viewModel: FavoriteEventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteEventsBinding.inflate(inflater,container,false)
        viewModel= ViewModelProvider(this)[FavoriteEventsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBookmarkBarMain()
        setupListRvFavEvent()
        showListFavEvent()
    }

    private fun setupBookmarkBarMain() {
        binding.apply {
            bookmarkToolbar.inflateMenu(R.menu.menu_favorite)
            bookmarkToolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.deleteAll_bookmark_event ->{
                        checkBeforeClearAllBookmarked()
                    }
                }
                true
            }
        }
    }

    private fun checkBeforeClearAllBookmarked() {
        Builder(requireActivity())
            .setTitle(getString(R.string.delete_all_bookmarks))
            .setMessage(getString(R.string.are_you_sure_do_you_want_to_delete_all_bookmark_events))
            .setNegativeButton(getString(R.string.no), null)
            .setPositiveButton(getString(R.string.yes), object : DialogInterface.OnClickListener {
                override fun onClick(arg0: DialogInterface?, arg1: Int) {
                    viewModel.clearAllFavoriteEvents()
                    showSnackBarBookmarkRemoved()
                }
            }).create().show()
    }

    private fun showSnackBarBookmarkRemoved(){
        Snackbar.make(
            binding.root,
            getString(R.string.all_events_removed),
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }


    private fun setupListRvFavEvent() {
        binding.rvEventsFavourite.adapter = adapter
        binding.rvEventsFavourite.setHasFixedSize(true)
        adapter.setOnClickListener(object :FavoriteEventsAdapter.OnEventFavoriteClickListener{
            override fun onClick(position: Int, event: Events) {
                val toDetailEvent = FavoriteEventsFragmentDirections.actionFavoriteEventsFragmentToDetailEventsActivity(event)
                findNavController().navigate(toDetailEvent)
            }
        })
    }

    private fun showListFavEvent() {
        viewModel.showFavoriteEvents.observe(viewLifecycleOwner){ event->
            setStateOfView(event.isNullOrEmpty())
            adapter.setDataFavorite(event)
        }
    }

    private fun setStateOfView(isEmpty: Boolean){
        binding.apply {
            viewNoEvent.root.isVisible = isEmpty
            rvEventsFavourite.isVisible = !isEmpty
            //viewNoEvent.imgNoDataFavorite.isVisible = isEmpty
            //viewNoEvent.txtNoEventFavorite.isVisible = isEmpty
        }
    }
}