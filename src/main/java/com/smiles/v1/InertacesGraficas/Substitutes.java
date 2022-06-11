package com.smiles.v1.InertacesGraficas;

@SuppressWarnings({"java:S115", "java:S1192"})
public enum Substitutes {
    Alcohol("[OH]","Over Oxygen",false),
    Thiol("[SH]","Over Sulfur",false),
    Amine("[NH2]","Over Nitrogen",false),
    Chlorine("[Cl]","Over Chloride",false),
    CarboxylicAcid("[C](=O)O","Over Carbon",false),
    Chloromethane("[CH2]Cl","Over CH2",false),
    Aldehyde("[CH]=O","Over Carbon",false),
    Benzene("c1ccc[c]c1","Over C",false),
    Diichloromethane("[CH](Cl)Cl","Over Carbon",false),
    FloroMethane("[CH2]F","Over Carbon",false),
    DiFloroMethane("[CH](F)F","Over Carbon",false),
    Nitro("[N](=O)([O])","Over Nitrogen",false),
    CH2_SH("[CH2]S","Over CH2",false),
    S_CH3("C[S]","Over Sulfur",false),
    C2C("[CH]=C","Over CH",false),
    C4ClH9("CCCCCl","ASD",false);

    private String smile;
    private boolean hydrogenImplicit;
    private String mensaje;
    Substitutes(String smile, String mensaje, boolean hydrogenImplicit ){
        this.smile =smile;
        this.mensaje = mensaje;
        this.hydrogenImplicit=hydrogenImplicit;
    }
    String getSmi(){
        return this.smile;
    }
    String getMessage(){
        return this.mensaje;
    }
    boolean getHydrogen() {
        return hydrogenImplicit;
    }

}
