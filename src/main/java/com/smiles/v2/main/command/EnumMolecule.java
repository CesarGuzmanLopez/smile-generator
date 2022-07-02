package com.smiles.v2.main.command;

import java.util.logging.Level;

import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;

public class EnumMolecule extends Command {

    public EnumMolecule(final SmileVerificationInterface verification, final MoleculeDataFactoryInterface factory) {
        super(verification, factory);
        Command.LOGGER.log(Level.INFO, "Enumerate molecules: ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        super.execute();
        StringBuilder enumeration;
        enumeration = new StringBuilder();
        enumeration.append("\nEnumeration of molecules:\n");
        for (int i = 0; i < molecule().atomCount(); i++) {
            enumeration.append("\t" + i + ". " + molecule().getAtom(i).getSymbol() + " ");
        }
        String outPut = enumeration.toString();
        Command.LOGGER.log(Level.INFO, outPut);
    }
}
