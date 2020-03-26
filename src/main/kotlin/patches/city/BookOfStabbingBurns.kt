package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.cards.status.Wound
import com.megacrit.cardcrawl.powers.PainfulStabsPower
import javassist.expr.ExprEditor
import javassist.expr.NewExpr

@SpirePatch(
    clz = PainfulStabsPower::class,
    method = "onInflictDamage"
)
class BookOfStabbingBurns {
    companion object {
        @JvmStatic
        fun Instrument(): ExprEditor =
            object : ExprEditor() {
                override fun edit(e: NewExpr) {
                    if (e.className == Wound::class.qualifiedName) {
                        e.replace("\$_ = new ${Burn::class.qualifiedName}();")
                    }
                }
            }
    }
}
