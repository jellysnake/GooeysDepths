/*
 * Copyright 2018 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.depths.caveGen.worldGen.caverSystem;

import org.terasology.math.Region3i;
import org.terasology.math.Side;
import org.terasology.math.geom.Vector3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetBorder;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.Requires;

@Produces(GrassFacet.class)
@Requires(
        @Facet(value = CaveSystemFacet.class))
public class GrassProvider implements FacetProvider {
    @Override
    public void process(GeneratingRegion region) {
//        CaveSystemFacet caveFacet = region.getRegionFacet(CaveSystemFacet.class);
//
//        Border3D border = region.getBorderForFacet(GrassFacet.class);
//        GrassFacet facet = new GrassFacet(region.getRegion(), border);
//
//        Region3i processRegion = caveFacet.getWorldRegion();
//        for (Vector3i position : processRegion) {
//            if (caveFacet.getWorld(position)
//                    && !caveFacet.getWorld(position.add(Side.TOP.getVector3i()))) {
//                facet.setWorld(position, true);
//            }
//        }
//
//        region.setRegionFacet(GrassFacet.class, facet);
    }
}
