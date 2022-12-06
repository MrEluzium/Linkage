package org.elzzz.linkage.data;

import net.minecraft.util.math.Vec3d;

public class PlayerData
{
    protected String dimension;
    protected Vec3d pos;

    public PlayerData(String dimension, Vec3d pos){
        this.dimension = dimension;
        this.pos = pos;
    }

    public String getDimension() {
        return this.dimension;
    }

    public Vec3d getPos() {
        return this.pos;
    }
};