package com.smiles.v2.main.views.events;

import java.util.EventListener;
/**
 * @author Cesar Gerardo Guzman Lopez
 * License: MIT
 * @version 1.0
*/
public interface AddSmileHListener extends EventListener {
    /**
     * @param evt event to handle.
    */
    void addSmileHEvent(AddSmileHEvent evt);
}
