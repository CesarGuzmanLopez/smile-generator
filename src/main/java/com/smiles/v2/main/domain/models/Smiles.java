package com.smiles.v2.main.domain.models;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;
public class Smiles implements SmilesHInterface {
    private String strSmiles;
    private String message;
    private boolean hydrogenImplicit;
    private String name = "";
    private SmileVerificationInterface smileVerification;
    public Smiles(final String name, final String smiles, final String message, final boolean hydrogenImplicit,
            final SmileVerificationInterface smileVerification) {
        if (smiles == null || message == null || name == null) {
            throw new IllegalArgumentException("Smiles, message or name must be not null");
        }
        if (name.equals("")) {
            throw new IllegalArgumentException("Name must be not empty");
        }
        if (!smileVerification.isValid(smiles)) {
            throw new IllegalArgumentException("Smiles: " + smiles + " is not valid");
        }
        this.smileVerification = smileVerification;
        this.name = name;
        this.strSmiles = smiles;
        this.message = message;
        this.hydrogenImplicit = hydrogenImplicit;
    }

    public Smiles(final SmilesHInterface smile) {
        if (smile == null) {
            throw new IllegalArgumentException("Smiles must be not null");
        }
        if (smile.getName().equals("")) {
            throw new IllegalArgumentException("Name must be not empty");
        }
        this.smileVerification = ((Smiles) smile).smileVerification;
        if (!smileVerification.isValid(smile.smile())) {
            throw new IllegalArgumentException("Smiles is not valid");
        }
        this.name = smile.getName();
        this.strSmiles = smile.smile();
        this.message = smile.getMessage();
        this.hydrogenImplicit = smile.hasHydrogenImplicit();
    }
    /**
     * @param newName
    */
    public void setName(final String newName) {
        this.name = newName;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String smile() {
        return strSmiles;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        return message;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasHydrogenImplicit() {
        return hydrogenImplicit;
    }
    /** Verify the smiles.
     * @param strSmiles the strSmiles to set
     */
    protected void setStrSmiles(final String strSmiles) {
        if (smileVerification.isValid(strSmiles)) this.strSmiles = strSmiles;
        else throw new IllegalArgumentException("Smiles is not valid");

    }
    /**
     *  @return SmileVerificationInterface
     */
    protected SmileVerificationInterface getSmileVerification() {
        return smileVerification;
    }

}
