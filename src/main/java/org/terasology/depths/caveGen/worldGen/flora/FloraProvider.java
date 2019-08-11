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
package org.terasology.depths.caveGen.worldGen.flora;

import org.terasology.depths.caveGen.worldGen.caverSystem.CaveSystemFacet;
import org.terasology.math.Region3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.utilities.random.FastRandom;
import org.terasology.utilities.random.Random;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetBorder;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.Requires;

@Produces(FloraFacet.class)
@Requires(@Facet(value = CaveSystemFacet.class, border = @FacetBorder(bottom = 1)))
public class FloraProvider implements FacetProvider {

    private Random random;

    @Override
    public void setSeed(long seed) {
        random = new FastRandom(seed);
    }

    @Override
    public void process(GeneratingRegion region) {
        CaveSystemFacet caveFacet = region.getRegionFacet(CaveSystemFacet.class);

        Border3D border = region.getBorderForFacet(FloraFacet.class);
        FloraFacet facet = new FloraFacet(region.getRegion(), border);
        Region3i processRegion = facet.getRelativeRegion();

        for (Vector3i position : processRegion) {
            if (caveFacet.get(position.x, position.y - 1, position.z) == CaveSystemFacet.GRASS) { /* Above grass is always air */
                if (random.nextFloat() < 0.2) {
                    facet.set(position, (byte) random.nextInt(FloraFacet.FLOWERS.length));
                }
            }
        }

        region.setRegionFacet(FloraFacet.class, facet);
    }
}

