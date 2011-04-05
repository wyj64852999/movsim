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
package org.movsim.simulator.roadSection.impl;

import java.util.List;

import org.movsim.input.model.simulation.FlowConservingBottleneckDataPoint;
import org.movsim.simulator.roadSection.FlowConservingBottlenecks;
import org.movsim.utilities.Tables;


public class FlowConservingBottlenecksImpl implements FlowConservingBottlenecks{
    //final static Logger logger = LoggerFactory.getLogger(FlowConservingBottlenecksImpl.class);

    private double[] posValues;
    private double[] alphaTValues;
    private double[] alphaV0Values;
    
    
    public FlowConservingBottlenecksImpl(List<FlowConservingBottleneckDataPoint> flowConsDataPoints){
        generateSpaceSeriesData(flowConsDataPoints);
    }
    
    private void generateSpaceSeriesData(List<FlowConservingBottleneckDataPoint> data){
        final int size = data.size();
        posValues  = new double[size];
        alphaTValues = new double[size];
        alphaV0Values  = new double[size];
        for(int i=0; i<size; i++){
            posValues[i]     = data.get(i).getPosition(); 
            alphaTValues[i]  = data.get(i).getAlphaT();
            alphaV0Values[i] = data.get(i).getAlphaV0();
            //logger.debug("add data: alphaT={}, alphaV0={}", alphaTValues[i], alphaV0Values[i]);
        }
    }

    public double alphaT(double x) {
        return (alphaTValues.length==0) ? 1 : Tables.intpextp(posValues, alphaTValues, x);
    }

    public double alphaV0(double x) {
        return  (alphaV0Values.length==0) ? 1 : Tables.intpextp(posValues, alphaV0Values, x);
    }

}
