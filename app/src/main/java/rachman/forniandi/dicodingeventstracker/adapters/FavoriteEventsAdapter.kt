package rachman.forniandi.dicodingeventstracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.adapters.EventsAdapter.EventsHolder
import rachman.forniandi.dicodingeventstracker.adapters.EventsAdapter.OnEventClickListener
import rachman.forniandi.dicodingeventstracker.data.local.room.EventEntity
import rachman.forniandi.dicodingeventstracker.databinding.ItemEventBinding
import rachman.forniandi.dicodingeventstracker.domain.entity.Events

class FavoriteEventsAdapter: RecyclerView.Adapter<FavoriteEventsAdapter.FavoriteEventsHolder>() {

    private var favList = ArrayList<EventEntity>()
    private var onFavClickListener: OnEventFavoriteClickListener?= null

    class FavoriteEventsHolder (view: ItemEventBinding): RecyclerView.ViewHolder(view.root){
        val txtTitleEvent = view.txtTitleNameEvent
        val imgPromoteEvent = view.imgEvent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventsHolder {
        val bindingView = ItemEventBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteEventsHolder(bindingView)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: FavoriteEventsHolder, position: Int) {
        val eventFavData = favList[position].events
        holder.txtTitleEvent.text = eventFavData.name
        Picasso.get()
            .load(eventFavData.imageLogo)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.error_placeholder)
            .into(holder.imgPromoteEvent)

        holder.itemView.setOnClickListener {
            onFavClickListener?.onClick(position,eventFavData)
        }
    }

    fun setOnClickListener(onClickListener: OnEventFavoriteClickListener) {
        this.onFavClickListener = onClickListener
    }

    fun setDataFavorite(favoriteList:List<EventEntity>?){
        if (favoriteList == null)return
        favList.clear()
        favList.addAll(favoriteList)
        notifyDataSetChanged()
    }


    interface OnEventFavoriteClickListener {
        fun onClick(position: Int, event: Events)
    }
}