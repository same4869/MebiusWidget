package com.pokemon.mebius.widget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pokemon.mebius.widget.databinding.ActivityRecyclerViewBinding

class SodaRecyclerViewActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRecyclerViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.mBaseAdapter.setOnClickListener {
            startTargetActivity(SodaRecyclerBaseAdapterActivity::class.java)
        }

        mBinding.mSimpleAdapter.setOnClickListener {
            startTargetActivity(SodaRecyclerSimpleAdapterActivity::class.java)
        }

        mBinding.mDiyAdapter.setOnClickListener {
            startTargetActivity(SodaRecyclerDiyAdapterActivity::class.java)
        }
    }

    private fun startTargetActivity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }
}