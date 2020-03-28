package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.evacipated.cardcrawl.mod.humilty.patches.utils.addPreBattleAction
import com.evacipated.cardcrawl.mod.humilty.powers.CrushingBlowsPower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob
import javassist.CtBehavior

@SpirePatch(
    clz = GremlinNob::class,
    method = SpirePatch.CONSTRUCTOR,
    paramtypez = [Float::class, Float::class, Boolean::class]
)
class NobWounds {
    companion object {
        @JvmStatic
        fun Raw(ctBehavior: CtBehavior) {
            ctBehavior.addPreBattleAction(::doPreBattleAction)
        }

        @JvmStatic
        fun doPreBattleAction(nob: GremlinNob) {
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(nob, nob, CrushingBlowsPower(nob))
            )
        }
    }
}
