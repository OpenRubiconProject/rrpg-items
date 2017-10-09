package com.openrubicon.items.classes.sockets.nbt;

import com.openrubicon.core.api.interfaces.Observeable;
import com.openrubicon.core.api.nbt.Properties;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;

import java.util.ArrayList;
import java.util.Map;

public class SocketProperties extends Properties implements Observeable {

    @Override
    public ArrayList<String> getObservation() {

        ArrayList<String> observation = new ArrayList<>();

        for (Map.Entry<String, String> entry : this.getProperties().entrySet()) {

            String propName = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);

            if(propName.toLowerCase().equals(Constants.OBFUSCATED))
                continue;

            String propValue = entry.getValue();

            if(Helpers.isNumeric(propValue))
                propValue = String.valueOf(Helpers.round(Double.parseDouble(propValue), 2));


            observation.add("&2"+propName+"&f: &a"+propValue);
        }

        return Helpers.colorize(observation);
    }

}
