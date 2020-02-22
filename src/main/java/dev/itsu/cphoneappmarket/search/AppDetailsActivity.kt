package dev.itsu.cphoneappmarket.search

import cn.nukkit.utils.TextFormat
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity

class AppDetailsActivity(manifest: ApplicationManifest, val app: ApplicationManifest) : ModalActivity(manifest) {

    private lateinit var bundle: Bundle
    private val hasApp: Boolean

    init {
        hasApp = ApplicationSQLManager.getApplications(bundle.cPhone.player.name).contains(app.title)
    }

    override fun onStop(response: Response): ReturnType {

        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle

        this.title = app.title
        this.content = """
            ${bundle.getString("ad_name")} ${app.title}\n ${TextFormat.RESET}
            ${if (hasApp) bundle.getString("sr_installed") else bundle.getString("sr_not_installed")}\n ${TextFormat.RESET}
            \n
            ${app.description}\n ${TextFormat.RESET}
            \n
            ${bundle.getString("ad_price")} ${app.price}\n ${TextFormat.RESET}
            ${bundle.getString("ad_version")} ${app.version}\n
            ${bundle.getString("ad_author")} ${app.author}\n
        """.trimIndent()

        this.button1Text = if (hasApp) bundle.getString("install") else bundle.getString("uninstall")
        this.button2Text = bundle.getString("back_to_top")
    }

}