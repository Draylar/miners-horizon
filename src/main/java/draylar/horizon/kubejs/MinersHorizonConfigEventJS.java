package draylar.horizon.kubejs;

import dev.latvian.kubejs.event.EventJS;
import draylar.horizon.config.OreConfig;

public class MinersHorizonConfigEventJS extends EventJS {

    public final OreConfig ore;

    public MinersHorizonConfigEventJS(OreConfig ore) {
        this.ore = ore;
    }
}
