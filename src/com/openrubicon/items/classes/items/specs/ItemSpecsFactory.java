package com.openrubicon.items.classes.items.specs;

import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.helpers.Constants;
import org.bukkit.Bukkit;

public class ItemSpecsFactory {

    public static ItemSpecs generateFromCoreValues(double rarity, double sockets, double power)
    {
        int durability = (int)calculateDurability(rarity, power, sockets);
        return new ItemSpecs(rarity, power, sockets, calculateAttributePoints(rarity, power), durability, durability, total(), 0);
    }

    public static ItemSpecs generateFromCoreValues(double rarity, double sockets, double power, int durability)
    {
        return new ItemSpecs(rarity, power, sockets, calculateAttributePoints(rarity, power), durability, durability, total(), 0);
    }

    public static ItemSpecs generateFromCoreValues(double rarity, double sockets, double power, int durability, int points)
    {
        return new ItemSpecs(rarity, power, sockets, points, durability, durability, total(), 0);
    }


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
                    double calc = (int)Math.pow(i * j * k, Constants.SPREAD);
                    type -= calc;
                    if (type <= 0)
                    {
                        int durability = (int)calculateDurability(Constants.MAX_RARITY + 1 - i, Constants.MAX_POWER + 1 - j, Constants.MAX_SOCKETS + 1 - k);
                        return new ItemSpecs(Constants.MAX_RARITY + 1 - i, Constants.MAX_SOCKETS + 1 - k, Constants.MAX_POWER + 1 - j, calculateAttributePoints(Constants.MAX_RARITY + 1 - i, Constants.MAX_POWER + 1 - j), durability, durability, total(), bg);
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

        for (int i = Constants.MAX_RARITY; i > 0; i--)
        {
            for (int j = Constants.MAX_POWER; j > 0; j--)
            {
                for (int k = Constants.MAX_SOCKETS; k > 0; k--)
                {
                    total += Math.pow(i * j * k, Constants.SPREAD);
                }
            }
        }

        return total;
    }

    private static double randomIndex()
    {
        return (int)(Helpers.randomDouble(1, total()));
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
        double rarityFactor = Helpers.scale(rarity, 1, 11, 1, 5);
        double powerFactor = Helpers.scale(rarity, 1, 11, 1, 5);

        double totalAttribs = rarity + (rarity / 2) + (power / 2) - sockets;
        if(totalAttribs < 1)
            totalAttribs = 1;

        double combinedFactor = Helpers.scale(totalAttribs, 1, Constants.MAX_POWER + Constants.MAX_RARITY - 1, 2.5, 5.75);
        double rCombinedFactor = Helpers.randomDouble(2, combinedFactor + 1);

        double rChance = Helpers.randomDouble(10, 22) / 100;
        double mod = Helpers.randomDouble(Constants.MAX_RARITY / 2 + 7, (Constants.MAX_RARITY / 2) + rarity + 7);
        double exponent = rChance * (powerFactor + rarity) / ((sockets + Constants.MAX_SOCKETS) / mod);

        if(exponent < 0.5)
            exponent = 0.5;
        if(exponent > 10)
            exponent = 10;

        exponent = Helpers.scale(exponent, 0.5, 10, 1.75, 2.92);

        double base = (20.7 * rarityFactor) / ((Constants.MAX_RARITY + 1 - rarity) * 2 / 3);

        //Bukkit.broadcastMessage(base+":"+base*1.32);

        double value = Math.pow(Helpers.randomDouble(base, base * 1.32), exponent);

        value *= rCombinedFactor;

        return value;
    }

}
