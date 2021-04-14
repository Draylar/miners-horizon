package draylar.horizon.kubejs;

import dev.latvian.kubejs.event.EventJS;
import draylar.horizon.config.OreConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MinersHorizonOreEventJS extends EventJS {

    private static final List<OreConfigJS> loadedJS = new ArrayList<>();

    public OreConfigJS ore(String ore) {
        OreConfigJS oreConfigJS = new OreConfigJS(ore);
        loadedJS.add(oreConfigJS);
        return oreConfigJS;
    }

    public static List<OreConfig> build() {
        return loadedJS.stream().map(OreConfigJS::build).collect(Collectors.toList());
    }
}
