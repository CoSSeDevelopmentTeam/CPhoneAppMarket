package dev.itsu.cphoneappmarket

import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity

class ConfirmBuyingAppActivity(manifest: ApplicationManifest, private val hasApp: Boolean) : ModalActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        // TODO install and uninstall app
        bundle.cPhone.homeMessage = bundle.getString("installed_app")
        return ReturnType.TYPE_END
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
    }

}