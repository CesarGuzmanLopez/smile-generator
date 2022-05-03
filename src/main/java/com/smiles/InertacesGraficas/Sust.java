package com.smiles.InertacesGraficas;

public enum Sust {
    Alcohol("[OH]","Over Oxygen",false), 
    Thiol("[SH]","Over Sulfur",false), 
    Amine("[NH2]","Over Nitrogen",false), 
    Clorine("[Cl]","Over Chloride",false),
    CarboxilicAcid("[C](=O)O","Over Carbon",false), 
    ClorMethane("[CH2]Cl","Over CH2",false), 
    Aldehide("[CH]=O","Over Carbon",false),
    Benzene("c1ccc[c]c1","Over C",false), 
    DiClorMethane("[CH](Cl)Cl","Over Carbon",false), 
    FloroMethane("[CH2]F","Over Carbon",false), 
    DiFloroMethane("[CH](F)F","Over Carbon",false), 
    Nitro("[N](=O)([O])","Over Nitrogen",false),
    CH2_SH("[CH2]S","Over CH2",false),
    S_CH3("C[S]","Over Sulfur",false),
    C2C("[CH]=C","Over CH",false),
    C4ClH9("CCCCCl","ASD",false);
    String Smile;
    String Mensaje;
    boolean HydrogenImpli;
    
    Sust(String Smile, String Mensaje, boolean HydrogenImpli ){
        this.Smile =Smile;
        this.Mensaje= Mensaje;
        this.HydrogenImpli=HydrogenImpli;
    }
    String getSmi(){
        return this.Smile;
    }
    boolean getHydrogen() {
        return HydrogenImpli;
    }
    
}