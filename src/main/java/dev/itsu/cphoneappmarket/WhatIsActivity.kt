package dev.itsu.cphoneappmarket

import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ModalResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity

class WhatIsActivity(manifest: ApplicationManifest) : ModalActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val modalResponse = response as ModalResponse
        if (modalResponse.isButton1Clicked) {
            MainActivity(manifest).start(bundle)
            return ReturnType.TYPE_CONTINUE
        } else {
            return ReturnType.TYPE_END
        }
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        this.title = bundle.getString("w_title")
        this.content = bundle.getString("w_content")
        this.button1Text = bundle.getString("back_to_top")
        this.button2Text = bundle.getString("back_to_home")
    }

}