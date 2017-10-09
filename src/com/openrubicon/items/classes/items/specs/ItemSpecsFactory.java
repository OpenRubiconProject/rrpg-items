package com.openrubicon.items.classes.items.specs;

import com.openrubicon.core.helpers.Helpers;

public class ItemSpecsFactory {

    private static final float SPREAD = 1;
    private static final int MAX_SOCKETS = 11;
    private static final int MAX_RARITY = 11;
    private static final int MAX_POWER = 11;

    public static ItemSpecs generateSpecs()
    {
        double type = randomIndex();
        double bg = type;

        for (int i = 11; i > 0; i--)
        {
            for (int j = 11; j > 0; j--)
            {
                for (int k = 11; k > 0; k--)
                {
                    double calc = (int)Math.pow(i * j * k, SPREAD);
                    type -= calc;
                    if (type <= 0)
                    {
                        int durability = (int)calculateDurability(MAX_RARITY + 1 - i, MAX_POWER + 1 - j, MAX_SOCKETS + 1 - k);
                        return new ItemSpecs(MAX_RARITY + 1 - i, MAX_SOCKETS + 1 - k, MAX_POWER + 1 - j, calculateAttributePoints(MAX_RARITY + 1 - i, MAX_POWER + 1 - j), durability, durability, total(), bg);
                    }
                }
            }
        }

        int durability = (int)calculateDurability(1, 1, 1);
        return new ItemSpecs(1, 1, 1, calculateAttributePoints(1,1), durability, durability, 1, 1);
    }

    private static double total()
    {
        double total = 0;

        for (int i = MAX_RARITY; i > 0; i--)
        {
            for (int j = MAX_POWER; j > 0; j--)
            {
                for (int k = MAX_SOCKETS; k > 0; k--)
                {
                    total += Math.pow(i * j * k, SPREAD);
                }
            }
        }

        return total;
    }

    private static double randomIndex()
    {
        return (int)(Helpers.randomDouble(1, total()));
    }

    private static boolean socketObfuscated(ItemSpecs specs)
    {
        int max = (int)(specs.getRarity() * 2 - specs.getSockets());
        if(max < 3)
            max = 3;
        int min = 1;

        int chance = Helpers.randomInt(min, max);

        if(chance == min)
            return true;

        return false;
    }

    private static int calculateAttributePoints(double rarity, double power)
    {
        int max = (int) (rarity * power) + 1;
        int min = (int)((rarity * power * 3) / 4);
        int points = Helpers.randomInt(min, max);
        if (points < 1)
            points = 1;
        return points;
    }

    private static double calculateDurability(double rarity, double power, double sockets)
    {
        //double rChance = (r.nextInt(20 + 1) + 10) / 100.00;
        double rChance = Helpers.randomDouble(10, 41) / 100;
        double mod = Helpers.randomDouble(MAX_RARITY / 2 + 5, (MAX_RARITY / 2) + rarity + 5);
        double exponent = rChance * (power + rarity) / ((sockets + MAX_SOCKETS) / mod);
        if(exponent < 0.5)
            exponent = 0.5;
        if(exponent > 10)
            exponent = 10;
        double base = 50 / (MAX_RARITY + 1 - rarity);

        base = Math.pow(Helpers.randomDouble(base, base * 1.5), exponent);

        return base;
    }

}
