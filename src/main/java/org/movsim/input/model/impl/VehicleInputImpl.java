/**
 * Copyright (C) 2010, 2011 by Arne Kesting, Martin Treiber,
 *                             Ralph Germ, Martin Budden
 *                             <info@movsim.org>
 * ----------------------------------------------------------------------
 * 
 *  This file is part of 
 *  
 *  MovSim - the multi-model open-source vehicular-traffic simulator 
 *
 *  MovSim is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MovSim is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MovSim.  If not, see <http://www.gnu.org/licenses/> or
 *  <http://www.movsim.org>.
 *  
 * ----------------------------------------------------------------------
 */
package org.movsim.input.model.impl;

import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.movsim.input.XmlElementNames;
import org.movsim.input.impl.XmlUtils;
import org.movsim.input.model.VehicleInput;
import org.movsim.input.model.vehicle.behavior.MemoryInputData;
import org.movsim.input.model.vehicle.behavior.NoiseInputData;
import org.movsim.input.model.vehicle.behavior.impl.MemoryInputDataImpl;
import org.movsim.input.model.vehicle.behavior.impl.NoiseInputDataImpl;
import org.movsim.input.model.vehicle.longModel.ModelInputData;
import org.movsim.input.model.vehicle.longModel.impl.ModelInputDataACCImpl;
import org.movsim.input.model.vehicle.longModel.impl.ModelInputDataGippsImpl;
import org.movsim.input.model.vehicle.longModel.impl.ModelInputDataIDMImpl;
import org.movsim.input.model.vehicle.longModel.impl.ModelInputDataKCAImpl;
import org.movsim.input.model.vehicle.longModel.impl.ModelInputDataNSMImpl;
import org.movsim.input.model.vehicle.longModel.impl.ModelInputDataNewellImpl;
import org.movsim.input.model.vehicle.longModel.impl.ModelInputDataOVM_VDIFFImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO: extract element attr names into XmlElementNames Interface to make them symbolic.
/**
 * The Class VehicleInputImpl.
 */
public class VehicleInputImpl implements VehicleInput {

    /** The Constant logger. */
    final static Logger logger = LoggerFactory.getLogger(VehicleInputImpl.class);

    /** The label. cannot be changed while simulating */
    private final String label;

    /** The length. cannot be changed while simulating */
    private final double length;

    /** The max deceleration. in m/s^2, positive (default: Infinity) */
    private final double maxDeceleration;

    /** The reaction time. cannot be changed while simulating*/
    private final double reactionTime;

    /** The model input data. */
    private ModelInputData modelInputData;

    /** The memory input data. */
    private MemoryInputData memoryInputData = null;

    /** The noise input data. */
    private NoiseInputData noiseInputData = null;

    /**
     * Instantiates a new vehicle input impl.
     * 
     * @param elem
     *            the elem
     */
    @SuppressWarnings("unchecked")
    public VehicleInputImpl(Element elem) {
        this.label = elem.getAttributeValue("label");
        this.length = Double.parseDouble(elem.getAttributeValue("length"));
        this.maxDeceleration = Double.parseDouble(elem.getAttributeValue("b_max"));
        this.reactionTime = Double.parseDouble(elem.getAttributeValue("reaction_time"));

        final List<Element> longModelElems = elem.getChild(XmlElementNames.VehicleLongitudinalModel).getChildren();
        if (longModelElems.size() != 1) {
            logger.error("specify only one long model ! exit ");
            System.exit(-1);
        } else {
            modelInputData = modelInputDataFactory(longModelElems.get(0));
        }

        final Element memoryElem = elem.getChild(XmlElementNames.VehicleMemory);
        if (memoryElem != null) {
            final Map<String, String> map = XmlUtils.putAttributesInHash(memoryElem);
            memoryInputData = new MemoryInputDataImpl(map);
        }

        final Element noiseElem = elem.getChild(XmlElementNames.VehicleNoise);
        if (noiseElem != null) {
            final Map<String, String> map = XmlUtils.putAttributesInHash(noiseElem);
            noiseInputData = new NoiseInputDataImpl(map);
        }
    }

    /**
     * Model input data factory.
     * 
     * @param elem
     *            the elem
     * @return the model input data
     */
    private ModelInputData modelInputDataFactory(Element elem) {
        final String modelName = elem.getName();
        final Map<String, String> map = XmlUtils.putAttributesInHash(elem);
        if (modelName.equalsIgnoreCase(XmlElementNames.VehicleLongModelIDM))
            return new ModelInputDataIDMImpl(XmlElementNames.VehicleLongModelIDM, map);
        else if (modelName.equalsIgnoreCase(XmlElementNames.VehicleLongModelACC))
            return new ModelInputDataACCImpl(XmlElementNames.VehicleLongModelACC, map);
        else if (modelName.equalsIgnoreCase(XmlElementNames.VehicleLongModelOVM_VDIFF))
            return new ModelInputDataOVM_VDIFFImpl(XmlElementNames.VehicleLongModelOVM_VDIFF, map);
        else if (modelName.equalsIgnoreCase(XmlElementNames.VehicleLongModelGIPPS))
            return new ModelInputDataGippsImpl(XmlElementNames.VehicleLongModelGIPPS, map);
        else if (modelName.equalsIgnoreCase(XmlElementNames.VehicleLongModelNEWELL))
            return new ModelInputDataNewellImpl(XmlElementNames.VehicleLongModelNEWELL, map);
        else if (modelName.equalsIgnoreCase(XmlElementNames.VehicleLongModelNSM))
            return new ModelInputDataNSMImpl(XmlElementNames.VehicleLongModelNSM, map);
        else if (modelName.equalsIgnoreCase(XmlElementNames.VehicleLongModelKCA))
            return new ModelInputDataKCAImpl(XmlElementNames.VehicleLongModelKCA, map);
        else {
            logger.error("model with name {} not yet implemented. exit.", modelName);
            System.exit(-1);
        }
        return null; // not reached, instead exit
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.VehicleInput#getLabel()
     */
    @Override
    public String getLabel() {
        return label;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.VehicleInput#getLength()
     */
    @Override
    public double getLength() {
        return length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.VehicleInput#getMaxDeceleration()
     */
    @Override
    public double getMaxDeceleration() {
        return maxDeceleration;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.VehicleInput#getModelInputData()
     */
    @Override
    public ModelInputData getModelInputData() {
        return modelInputData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.VehicleInput#isWithMemory()
     */
    @Override
    public boolean isWithMemory() {
        return (memoryInputData != null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.VehicleInput#getMemoryInputData()
     */
    @Override
    public MemoryInputData getMemoryInputData() {
        return memoryInputData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.VehicleInput#isWithNoise()
     */
    @Override
    public boolean isWithNoise() {
        return (noiseInputData != null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.impl.VehicleInput#getNoiseInputData()
     */
    @Override
    public NoiseInputData getNoiseInputData() {
        return noiseInputData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.movsim.input.model.VehicleInput#getReactionTime()
     */
    @Override
    public double getReactionTime() {
        return reactionTime;
    }

}