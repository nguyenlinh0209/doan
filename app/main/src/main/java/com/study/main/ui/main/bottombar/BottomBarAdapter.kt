package com.study.main.ui.main.bottombar

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.study.core.extension.color
import com.study.core.extension.primaryColor
import com.study.main.databinding.ItemBottomBarLayoutBinding
import com.study.main.BR
import com.study.core.ui.TMVVMAdapter
import com.study.core.ui.TMVVMViewHolder

class BottomBarAdapter(private val context: Context, private val listener: OnItemClickListener) :
    TMVVMAdapter<BottomBarMenu>(ArrayList()) {
    interface OnItemClickListener {
        fun onClick(menu: BottomBarMenu)
    }

    override fun onCreateViewHolderBase(parent: ViewGroup?, viewType: Int): TMVVMViewHolder {
        val binding =
            ItemBottomBarLayoutBinding.inflate(LayoutInflater.from(parent?.context), parent, false)

        return TMVVMViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolderBase(holder: TMVVMViewHolder?, position: Int) {
        val menu = list[position]
        val binding = holder?.binding as ItemBottomBarLayoutBinding
        binding.ctContainer.setOnClickListener {
            val previousIndex = list.indexOfFirst { it.isSelected }
            val currentIndex = list.indexOfFirst { it.type == menu.type }

            if (previousIndex != currentIndex) {
                for (item in list) {
                    item.isSelected = item.type == menu.type
                }
                notifyItemChanged(previousIndex)
                notifyItemChanged(currentIndex)
            }
            listener.onClick(menu)
        }

        binding.tvTitle.setTextColor(
            if (menu.isSelected) {
                context.primaryColor
            } else {
                "#E1E0E0".color
            }
        )
        binding.setVariable(BR.menu, menu)
        binding.executePendingBindings()
    }
}