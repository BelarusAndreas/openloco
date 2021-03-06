package openloco.rail;

import openloco.assets.*;
import openloco.graphics.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TrainRenderer implements Renderer<Train> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainRenderer.class);

    private final Assets assets;
    private final VehicleSpriteAtlas vehicleSpriteAtlas;

    public TrainRenderer(Assets assets) {
        this.assets = assets;
        vehicleSpriteAtlas = new VehicleSpriteAtlas();
    }

    @Override
    public List<SpriteInstance> render(Train train) {
        List<SpriteInstance> sprites = new ArrayList<>();

        for (int i=0; i<train.getRailVehicles().size(); i++) {
            RailVehicle railVehicle = train.getRailVehicles().get(i);
            Vehicle vehicleAsset = railVehicle.getVehicleAsset();
            VehicleUnit vehicleUnit = vehicleAsset.getVars().getVehicleUnits().get(railVehicle.getUnitIndex());


            int spriteDetailsIndex = vehicleUnit.getSpriteDetailsIndex();
            boolean reversed = vehicleUnit.isSpriteDetailsReversed();

            VehicleUnitSpriteDetails vehSpriteVar = vehicleAsset.getVars().getVehicleUnitSpriteDetails().get(spriteDetailsIndex);

            double angle = Math.PI * railVehicle.getDirection() / 180;

            if (reversed) {
                angle = (angle + Math.PI) % (2 * Math.PI);
            }

            double xFactor = -Math.sin(angle);
            double yFactor = Math.cos(angle);

            if (!vehicleUnit.isSpacingOnly()) {
                if (vehicleUnit.hasFrontBogey()) {
                    double bogeyScale = (vehSpriteVar.getBogeyPos() - vehicleUnit.getLength()) / 4.0;
                    LOGGER.debug("Outputting front bogey at offset ({}, {})", (int)Math.round(xFactor*bogeyScale), (int)Math.round(yFactor*bogeyScale));
                    sprites.add(drawBogeyAt(railVehicle, railVehicle.getLocation().plus((int)Math.round(xFactor*bogeyScale), (int)Math.round(yFactor*bogeyScale), 0)));
                }
                if (vehicleUnit.hasRearBogey()) {
                    double bogeyScale = (-vehSpriteVar.getBogeyPos() + vehicleUnit.getRearBogeyPosition()) / 4.0;
                    LOGGER.debug("Outputting rear bogey at offset ({}, {})", (int)Math.round(xFactor*bogeyScale), (int)Math.round(yFactor*bogeyScale));
                    sprites.add(drawBogeyAt(railVehicle, railVehicle.getLocation().plus((int)Math.round(xFactor*bogeyScale), (int)Math.round(yFactor*bogeyScale), 0)));
                }

                int spriteIndex = vehicleSpriteAtlas.getFlatSpriteIndex(vehicleAsset, railVehicle.getUnitIndex(), railVehicle.getDirection());
                Sprites.RawSprite rawSprite = vehicleAsset.getSprites().get(spriteIndex);
                sprites.add(new SpriteInstance(OpenGlSprite.createFromRawSprite(rawSprite), SpriteLayer.VEHICLES, railVehicle.getLocation().plus(0, 0, 2)));
            }
        }

        return sprites;
    }

    private SpriteInstance drawBogeyAt(RailVehicle railVehicle, CartCoord bogey1Loc) {
        int bogeySpriteIndex = vehicleSpriteAtlas.getBogeySpriteIndex(railVehicle.getVehicleAsset(), 0, railVehicle.getDirection());
        Sprites.RawSprite bogeyRawSprite = railVehicle.getVehicleAsset().getSprites().get(bogeySpriteIndex);
        return new SpriteInstance(OpenGlSprite.createFromRawSprite(bogeyRawSprite), SpriteLayer.VEHICLES, bogey1Loc);
    }

}
