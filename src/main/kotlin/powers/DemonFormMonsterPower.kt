package com.evacipated.cardcrawl.mod.humilty.powers

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.powers.StrengthPower

class DemonFormMonsterPower(
    owner: AbstractCreature,
    amount: Int
) : AbstractHumilityPower(POWER_ID, "demonForm") {
    companion object {
        val POWER_ID = HumilityMod.makeID("DemonFormMonster")
    }

    init {
        this.owner = owner
        this.amount = amount
        updateDescription()
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0].format(amount)
    }

    override fun atStartOfTurn() {
        flash()
        addToBot(ApplyPowerAction(owner, owner, StrengthPower(owner, amount), amount))
    }
}
