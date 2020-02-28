package dev.itsu.cphoneappmarket

import cn.nukkit.utils.TextFormat
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.application.ApplicationPermission
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ModalResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity
import net.comorevi.moneyapi.MoneySAPI

class ConfirmBuyingAppActivity(manifest: ApplicationManifest, private val app: ApplicationManifest, private val hasApp: Boolean) : ModalActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val modalResponse = response as ModalResponse
        if (!modalResponse.isButton1Clicked) {
            return ReturnType.TYPE_END
        }

        if (hasApp) {
            ApplicationSQLManager.unInstallApplication(bundle.cPhone.player.name, app)
            bundle.cPhone.homeMessage = bundle.getString("uninstalled_app") + " (${app.title})"
            return ReturnType.TYPE_END
        }

        val money = MoneySAPI.getInstance().getMoney(bundle.cPhone.player)
        val isEnough = money >= app.price

        if (!isEnough) {
            bundle.cPhone.homeMessage = bundle.getString("cb_error_money")
            return ReturnType.TYPE_END
        }

        println(app.title)
        ApplicationSQLManager.installApplication(bundle.cPhone.player.name, app)
        MoneySAPI.getInstance().reduceMoney(bundle.cPhone.player.name, app.price)
        bundle.cPhone.homeMessage = bundle.getString("installed_app") + " (${app.title})"

        return ReturnType.TYPE_END
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle

        val money = MoneySAPI.getInstance().getMoney(bundle.cPhone.player)
        val isEnough = money >= app.price

        title = bundle.getString("cb_title")
        content = if (hasApp) bundle.getString("cb_uninstall") else {
            """
                ${bundle.getString("cb_install")}
                
                ${bundle.getString("ad_price")} ${app.price}${MoneySAPI.getInstance().moneyUnit} ${TextFormat.RESET}
                ${bundle.getString("my_money")} ${if (isEnough) "§a" else "§c"} $money${MoneySAPI.getInstance().moneyUnit} ${TextFormat.RESET}
            """.trimIndent()

        }

        button1Text = when {
            app.permission == ApplicationPermission.ATTRIBUTE_DEFAULT -> {
                content += "\n" + bundle.getString("cb_isdefault")
                bundle.getString("back_to_top")
            }
            hasApp -> bundle.getString("uninstall")
            !isEnough -> "§c" + bundle.getString("install")
            else -> bundle.getString("install")
        }

        button2Text = bundle.getString("back_to_top")
    }

}