package backcore;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BackcoreConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue EXTRACTOR_RANGE = BUILDER
            .comment("The extracting time interval of extractors (tick)")
            .defineInRange("extractor_time", 800, 1, 4000);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
