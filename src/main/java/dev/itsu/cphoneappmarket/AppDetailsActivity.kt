package dev.itsu.cphoneappmarket

import cn.nukkit.utils.TextFormat
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ModalResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity
import net.comorevi.moneyapi.MoneySAPI

class AppDetailsActivity(manifest: ApplicationManifest, val app: ApplicationManifest) : ModalActivity(manifest) {

    private lateinit var bundle: Bundle
    private var hasApp: Boolean = false

    override fun onStop(response: Response): ReturnType {
        val modalResponse = response as ModalResponse
        return when {
            modalResponse.isButton1Clicked -> {
                ConfirmBuyingAppActivity(manifest, app, hasApp).start(bundle)
                ReturnType.TYPE_CONTINUE
            }
            modalResponse.isButton2Clicked -> ReturnType.TYPE_END
            else -> ReturnType.TYPE_END
        }
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        hasApp = ApplicationSQLManager.getApplications(bundle.cPhone.player.name).contains(app.title)

        this.title = app.title
        this.content = """
            §a${app.title} ${TextFormat.RESET}
            ${if (hasApp) bundle.getString("sr_installed") else bundle.getString("sr_not_installed")} ${TextFormat.RESET}
            
            ${app.description} ${TextFormat.RESET}
            
            ${bundle.getString("ad_price")} ${app.price}${MoneySAPI.getInstance().moneyUnit} ${TextFormat.RESET}
            ${bundle.getString("ad_version")} ${app.version}
            ${bundle.getString("ad_author")} ${app.author}
        """.trimIndent()

        this.button1Text = if (hasApp) bundle.getString("uninstall") else bundle.getString("install")
        this.button2Text = bundle.getString("back_to_top")
    }

}