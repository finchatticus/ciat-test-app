package com.example.testapp.presentation.user

import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.testapp.R
import org.jetbrains.anko.*

class UserUI : AnkoComponent<UserFragment> {

    lateinit var pb: ProgressBar
    lateinit var ivAvatar: ImageView
    lateinit var tvName: TextView
    lateinit var tvEmail: TextView

    override fun createView(ui: AnkoContext<UserFragment>) = with(ui) {
        frameLayout {

            verticalLayout {
                backgroundColorResource = R.color.primary
                padding = dimen(R.dimen.spacing_mlarge)


                ivAvatar = imageView()
                    .lparams(width = dip(75), height = dip(75)) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                tvName = textView {
                    textAppearance = R.style.TextAppearance_AppCompat_Title
                    textColorResource = android.R.color.white
                }.lparams(width = wrapContent, height = wrapContent) {
                    topMargin = dimen(R.dimen.spacing_large)
                    gravity = Gravity.CENTER_HORIZONTAL
                }

                tvEmail = textView {
                    textAppearance = R.style.TextAppearance_AppCompat_Subhead
                    textColorResource = R.color.secondary_text
                }.lparams(width = wrapContent, height = wrapContent) {
                    topMargin = dimen(R.dimen.spacing_medium)
                    gravity = Gravity.CENTER_HORIZONTAL
                }

            }.lparams(width = matchParent, height = wrapContent)

            pb = horizontalProgressBar {
                isIndeterminate = true
                visibility = View.GONE
            }.lparams(width = matchParent, height = wrapContent) {
                gravity = Gravity.TOP.or(Gravity.FILL_VERTICAL)
                topMargin = dip(-6)
            }

        }
    }

}