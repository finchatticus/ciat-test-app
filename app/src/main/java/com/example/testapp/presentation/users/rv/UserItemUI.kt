package com.example.testapp.presentation.users.rv

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.testapp.R
import org.jetbrains.anko.*

private val ID_IV_AVATAR = View.generateViewId()
private val ID_TV_NAME = View.generateViewId()

class UserItemUI : AnkoComponent<ViewGroup> {

    lateinit var ivAvatar: ImageView
    lateinit var tvName: TextView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = wrapContent)
            isClickable = true
            minimumHeight = dimenAttr(R.attr.actionBarSize)

            ivAvatar = imageView {
                id = ID_IV_AVATAR
            }.lparams(width = dip(50), height = dip(50)) {
                leftMargin = dimen(R.dimen.spacing_large)
                topMargin = dimen(R.dimen.spacing_middle)
                rightMargin = dimen(R.dimen.spacing_large)
                alignParentLeft()
            }

            tvName = textView {
                id = ID_TV_NAME
                maxLines = 1
                textAppearance = R.style.TextAppearance_AppCompat_Medium
                textColorResource = R.color.primary_text
                topPadding = dimen(R.dimen.spacing_middle)
                bottomPadding = dimen(R.dimen.spacing_middle)
            }.lparams(width = wrapContent, height = wrapContent) {
                rightOf(ID_IV_AVATAR)
                rightMargin = dimen(R.dimen.spacing_middle)
            }

            view {
                backgroundColorResource = R.color.divider
            }.lparams(width = matchParent, height = dip(1)) {
                rightOf(ID_IV_AVATAR)
                alignParentBottom()
            }
        }
    }

}