package com.example.testapp.presentation.util

import android.graphics.Rect
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R


inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

inline fun <reified T : Fragment> AppCompatActivity.addFragment(frameId: Int = R.id.fragment_container, useFragment: (fragment: T) -> Unit = {}, createFragment: () -> T) {
    supportFragmentManager.addFragment(frameId, useFragment, createFragment)
}


inline fun <reified T : Fragment> AppCompatActivity.replaceFragment(fragment: T, frameId: Int = R.id.fragment_container, useFragment: (fragment: T) -> Unit = {}) {
    supportFragmentManager.replaceFragment(fragment, frameId, useFragment)
}

inline fun <reified T : Fragment> FragmentManager.addFragment(frameId: Int = R.id.fragment_container, useFragment: (fragment: T) -> Unit = {}, createFragment: () -> T) {
    if (currentFragment(frameId) == null) {
        val fragment = createFragment.invoke()
        useFragment.invoke(fragment)
        inTransaction {
            add(frameId, fragment)
        }
    }
}

inline fun <reified T : Fragment> FragmentManager.replaceFragment(fragment: T, frameId: Int = R.id.fragment_container, useFragment: (fragment: T) -> Unit = {}) {
    if (currentFragment(frameId) !is T) {
        useFragment.invoke(fragment)
        inTransaction {
            replace(frameId, fragment)
            addToBackStack(null)
        }
    }
}

fun AppCompatActivity.currentFragment(frameId: Int = R.id.fragment_container): Fragment? = this.supportFragmentManager.currentFragment(frameId)

fun FragmentManager.currentFragment(frameId: Int = R.id.fragment_container): Fragment? = this.findFragmentById(frameId)

fun RecyclerView.getInitialRecyclerViewLoadCount(): Int {
    var itemsToLoad = 0
    val layoutManager = this.layoutManager
    val firstChild: View? = this.getChildAt(0)

    if (firstChild != null && layoutManager is LinearLayoutManager) {
        val bounds = Rect()
        this.getDecoratedBoundsWithMargins(firstChild, bounds)
        if (layoutManager.canScrollVertically()) {
            val recyclerHeightForItems = (this.height - this.paddingTop - this.paddingBottom)
            itemsToLoad = (recyclerHeightForItems + bounds.height() - 1) / bounds.height()
        } else if (layoutManager.canScrollHorizontally()) {
            val recyclerWidthForItems = (this.width - this.paddingLeft - this.paddingRight)
            itemsToLoad = (recyclerWidthForItems + bounds.width() - 1) / bounds.width()
        }
    }
    return itemsToLoad
}