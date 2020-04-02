package com.evacipated.cardcrawl.mod.humilty.powers

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.status.Dazed
import com.megacrit.cardcrawl.core.AbstractCreature

class DeathRattlePower(
    owner: AbstractCreature,
    amount: Int
) : AbstractHumilityPower(POWER_ID, "hex") {
    companion object {
        val POWER_ID = HumilityMod.makeID("DeathRattle")
    }

    init {
        this.owner = owner
        this.amount = amount
        updateDescription()
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0].format(amount)
    }

    override fun onDeath() {
        flashWithoutSound()
        addToTop(MakeTempCardInDrawPileAction(Dazed(), amount, false, true))
    }
}
