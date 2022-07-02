package com.smiles.v2.main.command;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;

/**
 * --img.
 *
 */
public class Img extends Command {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    public Img(final SmileVerificationInterface verification, final MoleculeDataFactoryInterface moleculeFactory) {
        super(verification, moleculeFactory);
        LOGGER.log(Level.INFO, "Generate image: ");
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void execute() {
        super.execute();
        int width = WIDTH;
        int height = HEIGHT;
        if (getArg("-width") != null) {
            width = Integer.parseInt(getArg("-width"));
        }
        if (getArg("-height") != null) {
            height = Integer.parseInt(getArg("-height"));
        }
        BufferedImage image = molecule().getImage(width, height, getName());
        String route = "./";
        if (getArg("-path") != null) {
            route = getArg("-path");
        } else {
            route += getName() + ".png";
        }

        try {
            ImageIO.write(image, "png", new File(route));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
