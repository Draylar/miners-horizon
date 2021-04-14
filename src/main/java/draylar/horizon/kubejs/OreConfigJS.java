package draylar.horizon.kubejs;

import draylar.horizon.config.OreConfig;

public class OreConfigJS {

    private final String ore;
    private int size;
    private int count;
    private int minY;
    private int maxY;

    public OreConfigJS(String ore) {
        this.ore = ore;
    }

    public OreConfigJS size(int size) {
        this.size = size;
        return this;
    }

    public OreConfigJS count(int count) {
        this.count = count;
        return this;
    }

    public OreConfigJS minY(int minY) {
        this.minY = minY;
        return this;
    }

    public OreConfigJS maxY(int maxY) {
        this.maxY = maxY;
        return this;
    }

    public OreConfig build() {
        return new OreConfig(ore, size, count, minY, maxY);
    }
}
