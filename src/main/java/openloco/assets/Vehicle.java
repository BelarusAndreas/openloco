package openloco.assets;

import com.google.common.base.Objects;

import java.util.List;

public class Vehicle {

    private final String name;
    private final MultiLangString description;
    private final VehicleVars vars;
    private final UseObject trackType;
    private final List<UseObject> trackModifications;
    private final List<CargoCapacity> cargoCapacities;
    private final UseObject visualFx;
    private final UseObject wakeFx;
    private final UseObject rackRail;
    private final UseObject startSound;
    private final List<UseObject> compatibleVehicles;
    private final List<UseObject> sounds;
    private final Sprites sprites;

    public Vehicle(String name, MultiLangString description, VehicleVars vars, UseObject trackType,
                   List<UseObject> trackModifications, List<CargoCapacity> cargoCapacities, UseObject visualFx, UseObject wakeFx, UseObject rackRail,
                   UseObject startSound, List<UseObject> compatibleVehicles, List<UseObject> sounds, Sprites sprites) {

        this.name = name;
        this.description = description;
        this.vars = vars;
        this.trackType = trackType;
        this.trackModifications = trackModifications;
        this.cargoCapacities = cargoCapacities;
        this.visualFx = visualFx;
        this.wakeFx = wakeFx;
        this.rackRail = rackRail;
        this.startSound = startSound;
        this.compatibleVehicles = compatibleVehicles;
        this.sounds = sounds;
        this.sprites = sprites;
    }

    public String getName() {
        return name;
    }

    public VehicleVars getVars() {
        return vars;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public boolean isDualHead() {
        return getVars().getVehicleFlags().contains(VehicleVars.VehicleFlag.DUALHEAD);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("description", description)
                .toString();
    }
}
