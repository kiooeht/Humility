package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.animations.SetAnimationAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.city.Byrd
import javassist.expr.ExprEditor
import javassist.expr.MethodCall

@SpirePatch(
    clz = Byrd::class,
    method = "changeState"
)
class ByrdNoStun {
    companion object {
        @JvmStatic
        fun Instrument(): ExprEditor =
            object : ExprEditor() {
                override fun edit(m: MethodCall) {
                    if (m.methodName == "setMove" || m.methodName == "createIntent") {
                        m.replace("")
                    }
                }
            }

        @JvmStatic
        fun Postfix(__instance: Byrd, stateName: String) {
            if (stateName == "GROUNDED") {
                __instance.rollMove()
                __instance.createIntent()
                AbstractDungeon.actionManager.addToBottom(SetAnimationAction(__instance, "head_lift"))
            }
        }
    }
}
