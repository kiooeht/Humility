package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.evacipated.cardcrawl.mod.humilty.powers.BurningStabsPower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.monsters.city.BookOfStabbing
import com.megacrit.cardcrawl.powers.PainfulStabsPower
import javassist.expr.ExprEditor
import javassist.expr.NewExpr

@SpirePatch(
    clz = BookOfStabbing::class,
    method = "usePreBattleAction"
)
class BookOfStabbingBurns {
    companion object {
        @JvmStatic
        fun Instrument(): ExprEditor =
            object : ExprEditor() {
                override fun edit(e: NewExpr) {
                    if (e.className == PainfulStabsPower::class.qualifiedName) {
                        e.replace("\$_ = new ${BurningStabsPower::class.qualifiedName}(\$\$);")
                    }
                }
            }
    }
}
