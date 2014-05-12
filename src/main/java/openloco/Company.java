package openloco;

import openloco.datfiles.MultiLangString;
import openloco.datfiles.Sprites;

public class Company {

    private String name;
    private final CompanyVars companyVars;
    private final MultiLangString companyName;
    private final MultiLangString ceoName;
    private final Sprites sprites;

    public Company(String name, CompanyVars companyVars, MultiLangString ceoName, MultiLangString companyName, Sprites sprites) {
        this.name = name;
        this.companyVars = companyVars;
        this.companyName = companyName;
        this.sprites = sprites;
        this.ceoName = ceoName;
    }

    public String getName() {
        return name;
    }

    public CompanyVars getCompanyVars() {
        return companyVars;
    }

    public MultiLangString getCompanyName() {
        return companyName;
    }

    public MultiLangString getCeoName() {
        return ceoName;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public static class CompanyVars {
        private byte intelligence;
        private byte aggressiveness;
        private byte competitiveness;

        public CompanyVars(byte intelligence, byte aggressiveness, byte competitiveness) {
            this.intelligence = intelligence;
            this.aggressiveness = aggressiveness;
            this.competitiveness = competitiveness;
        }

        public byte getIntelligence() {
            return intelligence;
        }

        public byte getAggressiveness() {
            return aggressiveness;
        }

        public byte getCompetitiveness() {
            return competitiveness;
        }
    }
}
