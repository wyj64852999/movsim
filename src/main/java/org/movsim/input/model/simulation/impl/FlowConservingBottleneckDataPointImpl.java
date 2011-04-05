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
package org.movsim.input.model.simulation.impl;

import java.util.Map;

import org.movsim.input.model.simulation.FlowConservingBottleneckDataPoint;


public class FlowConservingBottleneckDataPointImpl implements FlowConservingBottleneckDataPoint {
    
    private double x; // in m
    private double alphaT; 
    private double alphaV0;
    
    public FlowConservingBottleneckDataPointImpl(Map<String, String> map) {
        this.x = Double.parseDouble(map.get("x"));
        this.alphaT = Double.parseDouble(map.get("alpha_T"));
        this.alphaV0 = Double.parseDouble(map.get("alpha_v0"));
    }


    /* (non-Javadoc)
     * @see org.movsim.input.model.simulation.impl.FlowConservingBottleneckDataPoint#getPosition()
     */
    public double getPosition() {
        return x;
    }

    /* (non-Javadoc)
     * @see org.movsim.input.model.simulation.impl.FlowConservingBottleneckDataPoint#getAlphaT()
     */
    public double getAlphaT() {
        return alphaT;
    }
    
    /* (non-Javadoc)
     * @see org.movsim.input.model.simulation.impl.FlowConservingBottleneckDataPoint#getAlphaV0()
     */
    public double getAlphaV0() {
        return alphaV0;
    }

   
}
