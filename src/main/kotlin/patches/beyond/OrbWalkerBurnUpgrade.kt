package com.evacipated.cardcrawl.mod.humilty.patches.beyond

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.monsters.beyond.OrbWalker
import javassist.expr.ExprEditor
import javassist.expr.NewExpr

@SpirePatch(
    clz = OrbWalker::class,
    method = "takeTurn"
)
class OrbWalkerBurnUpgrade {
    companion object {
        @JvmStatic
        fun Instrument(): ExprEditor =
            object : ExprEditor() {
                override fun edit(e: NewExpr) {
                    if (e.className == Burn::class.qualifiedName) {
                        e.replace("\$_ = \$proceed(\$\$); \$_.upgrade();")
                    }
                }
            }
    }
}
