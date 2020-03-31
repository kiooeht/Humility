package com.evacipated.cardcrawl.mod.humilty.powers

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import kotlin.math.max

class SemiIntangiblePower(
    owner: AbstractCreature,
    amount: Int
) : AbstractHumilityPower(POWER_ID, "intangible") {
    companion object {
        val POWER_ID = HumilityMod.makeID("SemiIntangible")
    }

    private var justApplied = true

    init {
        this.owner = owner
        this.amount = amount
        priority = 70
        updateDescription()
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0].format(amount)
    }

    override fun atDamageFinalReceive(damage: Float, type: DamageInfo.DamageType): Float {
        return max(0f, damage - amount)
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (justApplied) {
            justApplied = false
            return
        }

        flash()
        addToBot(RemoveSpecificPowerAction(owner, owner, this))
    }
}
