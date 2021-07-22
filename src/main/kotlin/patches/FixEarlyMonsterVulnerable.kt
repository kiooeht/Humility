package com.evacipated.cardcrawl.mod.humilty.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.powers.VulnerablePower

// This stops Vulnerable applied by a front or middle monster from
// changing the damage dealt by monsters behind them
// Also affects Fungi Beasts, if you kill one before the other attacks
// (via Thorns or Orbs etc.) the living one won't suddenly deal extra damage
@SpirePatch2(
    clz = VulnerablePower::class,
    method = "atDamageReceive"
)
class FixEarlyMonsterVulnerable {
    companion object {
        @JvmStatic
        fun Prefix(damage: Float, ___justApplied: Boolean): SpireReturn<Float> {
            if (___justApplied) {
                return SpireReturn.Return(damage)
            }
            return SpireReturn.Continue()
        }
    }
}
