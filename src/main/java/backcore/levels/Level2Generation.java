package backcore.levels;

public class Level2Generation extends LevelGeneration48x48 {
    public static String[] templates = new String[]{
            "backcore:l2/1",
            "backcore:l2/1_v1",
            "backcore:l2/2",
            "backcore:l2/3",
            "backcore:l2/3_r1",
            "backcore:l2/3_v1",
            "backcore:l2/2"
    };

    @Override
    public String[] getTemplates() {
        return templates;
    }

    @Override
    public String getLevelID() {
        return "backcore:level2";
    }
}
