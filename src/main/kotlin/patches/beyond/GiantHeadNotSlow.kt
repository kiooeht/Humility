package com.evacipated.cardcrawl.mod.humilty.patches.beyond

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.monsters.beyond.GiantHead
import javassist.expr.ExprEditor
import javassist.expr.MethodCall

@SpirePatch(
    clz = GiantHead::class,
    method = "usePreBattleAction"
)
class GiantHeadNotSlow {
    companion object {
        @JvmStatic
        fun Instrument(): ExprEditor =
            object : ExprEditor() {
                override fun edit(m: MethodCall) {
                    if (m.methodName == "addToBottom") {
                        m.replace("")
                    }
                }
            }
    }
}
