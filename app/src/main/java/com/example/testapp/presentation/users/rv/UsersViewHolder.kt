package com.example.testapp.presentation.users.rv

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.domain.model.User
import com.example.testapp.presentation.util.PicassoCircleTransform
import com.squareup.picasso.Picasso

typealias OnUserClickListener = (User) -> Unit

class UsersViewHolder(
    itemView: View,
    private val ui: UserItemUI,
    private val onUserClickListener: OnUserClickListener?
) : RecyclerView.ViewHolder(itemView) {

    @SuppressLint("SetTextI18n")
    fun bind(user: User) {
        ui.tvName.text = "${user.firstName} ${user.lastName}"
        if (user.avatarPath.isNotEmpty()) {
            Picasso.get()
                .load(user.avatarPath)
                .transform(PicassoCircleTransform)
                .into(ui.ivAvatar)
        }
        itemView.setOnClickListener {
            onUserClickListener?.invoke(user)
        }
    }

}