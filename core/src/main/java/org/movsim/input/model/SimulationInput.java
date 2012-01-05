/**
 * Copyright (C) 2010, 2011 by Arne Kesting, Martin Treiber, Ralph Germ, Martin Budden
 *                             <movsim.org@gmail.com>
 * ---------------------------------------------------------------------------------------------------------------------
 * 
 *  This file is part of 
 *  
 *  MovSim - the multi-model open-source vehicular-traffic simulator 
 *
 *  MovSim is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 *  version.
 *
 *  MovSim is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with MovSim.
 *  If not, see <http://www.gnu.org/licenses/> or <http://www.movsim.org>.
 *  
 * ---------------------------------------------------------------------------------------------------------------------
 */
package org.movsim.input.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.movsim.input.XmlElementNames;
import org.movsim.input.XmlUtils;
import org.movsim.input.model.simulation.TrafficCompositionInputData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SimulationInput.
 */
public class SimulationInput {

    /** The Constant logger. */
    final static Logger logger = LoggerFactory.getLogger(SimulationInput.class);

    private final double timestep;

    /** The duration of the simulation. */
    private final double maxSimTime;

    private boolean withFixedSeed;

    private boolean withCrashExit;

    private final int randomSeed;

    private final List<TrafficCompositionInputData> trafficCompositionInputData;

    private boolean isWithWriteFundamentalDiagrams;

    Map<String, RoadInput> roadInputMap;

    private final OutputInput outputInput;

    /**
     * Instantiates a new simulation input.
     * 
     * @param elem
     *            the elem
     */
    public SimulationInput(final Element elem) {
        timestep = Double.parseDouble(elem.getAttributeValue("dt"));
        maxSimTime = Double.parseDouble(elem.getAttributeValue("duration"));
        randomSeed = Integer.parseInt(elem.getAttributeValue("seed"));
        if (elem.getAttributeValue("fixed_seed").equalsIgnoreCase("true")) {
            withFixedSeed = true;
        } else {
            withFixedSeed = false;
        }
        if (elem.getAttributeValue("crash_exit").equalsIgnoreCase("true")) {
            withCrashExit = true;
        } else {
            withCrashExit = false;
        }

        // default heterogeneity element with vehicle types
        trafficCompositionInputData = new ArrayList<TrafficCompositionInputData>();
        final Element heterogenElem = elem.getChild(XmlElementNames.TrafficComposition);

        // optional for specific road
        if (heterogenElem != null) {
            isWithWriteFundamentalDiagrams = heterogenElem.getAttributeValue("write_fund_diagrams").equals("true") ? true
                    : false;
            @SuppressWarnings("unchecked")
            final List<Element> vehTypeElems = elem.getChild(XmlElementNames.TrafficComposition).getChildren(
                    XmlElementNames.RoadVehicleType);
            for (final Element vehTypeElem : vehTypeElems) {
                final Map<String, String> map = XmlUtils.putAttributesInHash(vehTypeElem);
                trafficCompositionInputData.add(new TrafficCompositionInputData(map));
            }
        }

        // -----------------------------------------------------------

        // quick hack: for road segment a mapping to ids is needed
        @SuppressWarnings("unchecked")
        final List<Element> roadElems = elem.getChildren(XmlElementNames.Road);
        final List<RoadInput> roadInputList = new ArrayList<RoadInput>();
        for (final Element roadElem : roadElems) {
            roadInputList.add(new RoadInput(roadElem));
        }

        roadInputMap = new HashMap<String, RoadInput>();
        for (final RoadInput roadInputData : roadInputList) {
            roadInputMap.put(roadInputData.getId(), roadInputData);
        }

        // -------------------------------------------------------
        // Output
        outputInput = new OutputInput(elem.getChild(XmlElementNames.RoadOutput));

    }

    /**
     * @param xodrFilename
     * @return
     */
    private boolean validateOpenDriveFileName(String xodrFilename) {
        final int i = xodrFilename.lastIndexOf(".xodr");
        if (i < 0) {
            System.out
                    .println("Please provide OpenDRIVE file with ending \".xodr\" as argument with option -n, exit. ");
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.SimulationInput#getTimestep()
     */
    public double getTimestep() {
        return timestep;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.SimulationInput#getMaxSimulationTime()
     */
    public double getMaxSimTime() {
        return maxSimTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.SimulationInput#isWithFixedSeed()
     */
    public boolean isWithFixedSeed() {
        return withFixedSeed;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.SimulationInput#getRandomSeed()
     */
    public int getRandomSeed() {
        return randomSeed;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.SimulationInput#getRoadInput()
     */
    public Map<String, RoadInput> getRoadInput() {
        return roadInputMap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.SimulationInput#getSingleRoadInput()
     */
    public RoadInput getSingleRoadInput() {
        // Quick hack: assume only one single main road !!!
        return roadInputMap.get(0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.SimulationInput#getOutputInput()
     */
    public OutputInput getOutputInput() {
        return outputInput;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.SimulationInput#isWithCrashExit()
     */
    public boolean isWithCrashExit() {
        return withCrashExit;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.SimulationInput#getHeterogeneityInputData()
     */
    public List<TrafficCompositionInputData> getTrafficCompositionInputData() {
        return trafficCompositionInputData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.RoadInput#isWithWriteFundamentalDiagrams()
     */
    public boolean isWithWriteFundamentalDiagrams() {
        return isWithWriteFundamentalDiagrams;
    }

}

// }
//
// /**
// * Gets the timestep.
// *
// * @return the timestep
// */
// double getTimestep();
//
// /**
// * Gets the duration of the simulation.
// *
// * @return the max simulation time
// */
// double getMaxSimTime();
//
// /**
// * Checks if is with fixed seed.
// *
// * @return true, if is with fixed seed
// */
// boolean isWithFixedSeed();
//
// /**
// * Checks if is with crash exit.
// *
// * @return true, if is with crash exit
// */
// boolean isWithCrashExit();
//
// /**
// * Gets the random seed.
// *
// * @return the random seed
// */
// int getRandomSeed();
//
//
// /**
// * Checks if is with write fundamental diagrams.
// *
// * @return true, if is with write fundamental diagrams
// */
// boolean isWithWriteFundamentalDiagrams();
//
// /**
// * Gets the heterogeneity input data.
// *
// * @return the heterogeneity input data
// */
// List<TrafficCompositionInputData> getTrafficCompositionInputData();
//
//
// //ArrayList<RoadInput> getRoadInput();
// Map<Long, RoadInput> getRoadInput();
//
//
// /**
// * Gets the single road input. Quick hack: only one single main road
// *
// * @return the single road input
// */
// RoadInput getSingleRoadInput();
//
// /**
// * Gets the output input.
// *
// * @return the output input
// */
// OutputInput getOutputInput();
//
// }