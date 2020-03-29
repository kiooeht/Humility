package com.evacipated.cardcrawl.mod.humilty.powers

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.powers.StrengthPower

class DoubleTimePower(
    owner: AbstractCreature
) : AbstractHumilityPower(POWER_ID, "time") {
    companion object {
        val POWER_ID = HumilityMod.makeID("DoubleTime")
        private const val COUNTDOWN = 6
        private const val STR = 1
    }

    init {
        this.owner = owner
        this.amount = 0
        updateDescription()
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0].format(COUNTDOWN, STR)
    }

    override fun onAfterUseCard(card: AbstractCard, action: UseCardAction) {
        flashWithoutSound()
        amount += 1
        if (amount == COUNTDOWN) {
            amount = 0
            playApplyPowerSfx()
            addToBot(ApplyPowerAction(owner, owner, StrengthPower(owner, STR), STR))
        }
        updateDescription()
    }
}
