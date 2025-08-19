package backcore.levels;

public class Level1Generation extends LevelGeneration48x48{

    public static String[] templates = new String[]{
            "backcore:l1/0",
            "backcore:l1/1",
            "backcore:l1/2",
            "backcore:l1/3",
            "backcore:l1/4",
            "backcore:l1/6",
            "backcore:l1/7",
            "backcore:l1/8"
    };

    @Override
    protected String[] getTemplates() {
        return templates;
    }

    @Override
    protected String getLevelID() {
        return "backcore:level1";
    }

}
