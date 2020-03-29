package com.evacipated.cardcrawl.mod.humilty.patches.beyond

import com.evacipated.cardcrawl.mod.humilty.actions.ApplyPowerAllMonstersAction
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.monsters.beyond.Spiker
import javassist.expr.ExprEditor
import javassist.expr.NewExpr

@SpirePatch(
    clz = Spiker::class,
    method = "takeTurn"
)
class SpikerBuffAll {
    companion object {
        @JvmStatic
        fun Instrument(): ExprEditor =
            object : ExprEditor() {
                override fun edit(e: NewExpr) {
                    if (e.className == ApplyPowerAction::class.qualifiedName) {
                        e.replace("\$_ = new ${ApplyPowerAllMonstersAction::class.qualifiedName}(\$2, \$3, \$4);")
                    }
                }
            }
    }
}
