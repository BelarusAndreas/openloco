package openloco.industry;

import openloco.Assets;
import openloco.entities.BuildingAsset;
import openloco.entities.IndustryAsset;
import openloco.entities.Sprites;
import openloco.graphics.*;

import java.util.ArrayList;
import java.util.List;

public class IndustryRenderer implements Renderer<IndustryInstance> {

    private final Assets assets;

    public IndustryRenderer(Assets assets) {
        this.assets = assets;
    }

    @Override
    public List<SpriteInstance> render(IndustryInstance industryInstance) {
        IndustryAsset industryAsset = assets.getIndustry(industryInstance.getType());
        List<SpriteInstance> spriteInstances = new ArrayList<>();

        for (BuildingInstance buildingInstance: industryInstance.getBuildings()) {
            spriteInstances.addAll(render(buildingInstance, industryAsset));
        }

        return spriteInstances;
    }

    private List<SpriteInstance> render(BuildingInstance buildingInstance, IndustryAsset industryAsset) {
        ArrayList<SpriteInstance> spriteInstances = new ArrayList<>();
        BuildingAsset buildingAsset = industryAsset.getBuilding(buildingInstance.getType());

        int previousZ = 0;

        for (int i=0; i< buildingAsset.getSpriteCount(); i++) {
            int spriteOffset = 4* buildingAsset.getSpriteOffset(i);
            Sprites.RawSprite sprite = industryAsset.getSprites().get(spriteOffset);
            OpenGlSprite glSprite = OpenGlSprite.createFromRawSprite(sprite);

            CartCoord cartCoord = new CartCoord(buildingInstance.getX(), buildingInstance.getY(), previousZ);
            ScreenCoord screenCoord = IsoUtil.calculateScreenCoord(cartCoord);
            spriteInstances.add(new SpriteInstance(glSprite, screenCoord, SpriteLayer.BUILDING, cartCoord));

            previousZ = buildingAsset.getSpriteHeight(i);
        }

        return spriteInstances;
    }
}
