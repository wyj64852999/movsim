/**
 * Copyright (C) 2010, 2011 by Arne Kesting <movsim@akesting.de>, 
 *                             Martin Treiber <treibi@mtreiber.de>,
 *                             Ralph Germ <germ@ralphgerm.de>,
 *                             Martin Budden <mjbudden@gmail.com>
 *
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
package org.movsim.input.model;

import java.util.List;

import org.movsim.input.model.simulation.FlowConservingBottleneckDataPoint;
import org.movsim.input.model.simulation.HeterogeneityInputData;
import org.movsim.input.model.simulation.ICMacroData;
import org.movsim.input.model.simulation.ICMicroData;
import org.movsim.input.model.simulation.RampData;
import org.movsim.input.model.simulation.SpeedLimitDataPoint;
import org.movsim.input.model.simulation.TrafficLightData;
import org.movsim.input.model.simulation.UpstreamBoundaryData;


public interface SimulationInput {

    double getTimestep();

    double getRoadLength();

    double getMaxSimulationTime();

    boolean isWithFixedSeed();

    int getRandomSeed();

    boolean isWithWriteFundamentalDiagrams();
    
    List<HeterogeneityInputData> getHeterogeneityInputData();

    List<ICMacroData> getIcMacroData();

    List<ICMicroData> getIcMicroData();

    UpstreamBoundaryData getUpstreamBoundaryData();

    List<FlowConservingBottleneckDataPoint> getFlowConsBottleneckInputData();
    
    List<SpeedLimitDataPoint> getSpeedLimitInputData();

    List<RampData> getRamps();

    List<TrafficLightData> getTrafficLightData();

    int getLanes();

}