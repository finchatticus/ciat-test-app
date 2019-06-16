package com.example.testapp.presentation.users

import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class UsersUI : AnkoComponent<UsersFragment> {

    lateinit var pb: ProgressBar
    lateinit var rvUsers: RecyclerView

    override fun createView(ui: AnkoContext<UsersFragment>) = with(ui) {
        frameLayout {

            pb = horizontalProgressBar {
                isIndeterminate = true
                visibility = View.GONE
            }.lparams(width = matchParent, height = wrapContent) {
                gravity = Gravity.TOP.or(Gravity.FILL_VERTICAL)
                topMargin = dip(-6)
            }

            rvUsers = recyclerView {
                isVerticalScrollBarEnabled = true
                layoutManager = LinearLayoutManager(context)
            }.lparams(width = matchParent, height = matchParent)

        }
    }

}