package draylar.horizon.config;

public class OreConfig {

    public final String block;

    public final int size;
    public final int count;
    public final int maxY;

    public OreConfig(String block, int size, int count, int maxY) {
        this.block = block;
        this.size = size;
        this.count = count;
        this.maxY = maxY;
    }
}
