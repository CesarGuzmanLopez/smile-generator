package com.smiles.v2.main.interfaces;
/**Interface for the SmilesH.
 * @author Cesar Gerardo Guzman Lopez
 */
public  interface SmilesHInterface {
    /**
     * @return name of Smile.
    */
    String  getName();
    /**
     * @return  Smile.
    */
    String  getSmile();
    /**
     * @return  Message.
    */
    String  getMessage();
    /**
     * @return  Hydrogen true if has hydrogen implicit.
    */
    boolean hasHydrogenImplicit();
}
