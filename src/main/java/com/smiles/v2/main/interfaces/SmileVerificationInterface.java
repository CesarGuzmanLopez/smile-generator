package com.smiles.v2.main.interfaces;
/**Interface for the SmileVerification.
 * @author Cesar Gerardo Guzman Lopez
 */
public interface SmileVerificationInterface {
    /**
     * Verify if the smile is a valid smile.
     * @param smile smile to verify.
     * @return true if the smile is valid.
     */
    boolean isValid(String smile);
    /**
     * Verify if the smile is a valid smile.
     * @param smile smile to verify.
     * @return true if the smile is valid.
     */
    boolean isValid(SmilesHInterface smile);
    /**
     * @param smile to AbsoluteSmiles.
     * @return AbsoluteSmiles.
    */
    String toAbsoluteSmiles(String smile);

}
