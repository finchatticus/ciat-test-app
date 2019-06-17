package com.example.testapp.presentation.users.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.domain.model.User
import org.jetbrains.anko.AnkoContext

typealias OnLoadMore = () -> Unit

class UsersAdapter : RecyclerView.Adapter<UsersViewHolder>() {

    private val ui = UserItemUI()
    private var items = mutableListOf<User>()
    private var isLoading = false

    var onLoadMore: OnLoadMore? = null
    var onUserClickListener: OnUserClickListener? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = UsersViewHolder(ui.createView(AnkoContext.create(parent.context, parent)), ui, onUserClickListener)

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        recyclerView.removeOnScrollListener(recyclerViewOnScrollListener)
        super.onDetachedFromRecyclerView(recyclerView)
    }

    fun addItems(items: List<User>, initialCount: Int) {
        when {
            this.items.isEmpty() -> { // set items
                this.items = items.toMutableList()
                notifyDataSetChanged()
            }
            else -> { // add items to existing items
                val positionStart = this.items.size + 1
                val itemCount = items.size
                this.items.addAll(items)
                notifyItemRangeInserted(positionStart, itemCount)
                if (this.items.size < initialCount) {
                    onLoadMore?.invoke()
                }
            }
        }
    }

    fun setLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            if (!isLoading && linearLayoutManager.findLastCompletelyVisibleItemPosition() == items.size - 1) {
                onLoadMore?.invoke()
                setLoading(true)
            }
        }
    }

}