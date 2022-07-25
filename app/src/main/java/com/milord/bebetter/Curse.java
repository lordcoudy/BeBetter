package com.milord.bebetter;

public class Curse {
    int curse;
    int bless;

    public Curse (int curseCount, int blessCount)
    {
        curse = curseCount;
        bless = blessCount;
    }

    public int GetCurse()
    {
        return curse;
    }

    public int GetBless()
    {
        return bless;
    }

}
