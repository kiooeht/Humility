package com.evacipated.cardcrawl.mod.humilty.powers

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.status.Slimed
import com.megacrit.cardcrawl.core.AbstractCreature

class SlimeCoatingPower(
    owner: AbstractCreature,
    amount: Int
) : AbstractHumilityPower(POWER_ID, "split") {
    companion object {
        val POWER_ID = HumilityMod.makeID("SlimeCoating")
    }

    init {
        this.owner = owner
        this.amount = amount
        updateDescription()
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0].format(amount)
    }

    override fun onAttacked(info: DamageInfo?, damageAmount: Int): Int {
        if (info?.owner != null && damageAmount > 0 && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS) {
            flash()
            addToTop(MakeTempCardInDiscardAction(Slimed(), amount))
        }
        return damageAmount
    }
}
