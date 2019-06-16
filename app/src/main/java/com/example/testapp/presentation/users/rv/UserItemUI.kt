package com.example.testapp.presentation.users.rv

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.testapp.R
import org.jetbrains.anko.*

class UserItemUI : AnkoComponent<ViewGroup> {

    lateinit var ivAvatar: ImageView
    lateinit var tvName: TextView
    lateinit var tvEmail: TextView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            isClickable = true
            minimumHeight = dimenAttr(R.attr.actionBarSize)
            orientation = LinearLayout.HORIZONTAL

            ivAvatar = imageView {

            }.lparams(width = dip(50), height = dip(50)) {
                leftMargin = dimen(R.dimen.spacing_large)
                topMargin = dimen(R.dimen.spacing_middle)
                rightMargin = dimen(R.dimen.spacing_large)
            }

            view().lparams(width = dimen(R.dimen.spacing_medium), height = 0)

            verticalLayout {

                verticalLayout {
                    topPadding = dimen(R.dimen.spacing_middle)
                    bottomPadding = dimen(R.dimen.spacing_middle)

                    tvName = textView {
                        maxLines = 1
                        textAppearance = R.style.TextAppearance_AppCompat_Medium
                        textColorResource = R.color.primary_text
                    }.lparams(width = wrapContent, height = wrapContent) {
                        rightMargin = dimen(R.dimen.spacing_middle)
                    }

                    tvEmail = textView {
                        maxLines = 1
                        textAppearance = R.style.TextAppearance_AppCompat_Small
                        textColorResource = R.color.secondary_text
                    }.lparams(width = wrapContent, height = wrapContent) {
                        topMargin = dimen(R.dimen.spacing_medium)
                        rightMargin = dimen(R.dimen.spacing_middle)
                    }

                }.lparams(width = matchParent, height = wrapContent)

                view {
                    backgroundColorResource = R.color.divider
                }.lparams(width = matchParent, height = dip(1))

            }.lparams(width = matchParent, height = wrapContent)
        }
    }

}