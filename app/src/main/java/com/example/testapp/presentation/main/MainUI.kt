package com.example.testapp.presentation.main

import androidx.appcompat.widget.Toolbar
import com.example.testapp.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.themedAppBarLayout

class MainUI : AnkoComponent<MainActivity> {

    lateinit var toolbar: Toolbar

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout {

            themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {

                toolbar = toolbar {
                    backgroundColor = colorAttr(R.attr.colorPrimary)
                    popupTheme = R.style.AppTheme_PopupOverlay
                }.lparams(width = matchParent, height = dimenAttr(R.attr.actionBarSize))

            }.lparams(width = matchParent, height = wrapContent)

            frameLayout {
                id = R.id.fragment_container
            }.lparams(width = matchParent, height = matchParent)

        }
    }

}