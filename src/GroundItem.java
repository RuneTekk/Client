// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

public class GroundItem extends Entity {

    public Model getModel() {
        ItemDefinition class8 = ItemDefinition.getItemDefinition(itemid);
        return class8.getAmountModel(amount);
    }

    public GroundItem() { }

    public int itemid;
    public int amount;
}
