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
package org.terasology.depths.caveGen.worldGen.lighting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.depths.caveGen.worldGen.caverSystem.CaveSystemFacet;
import org.terasology.entitySystem.entity.EntityStore;
import org.terasology.logic.location.LocationComponent;
import org.terasology.math.geom.Vector3i;
import org.terasology.utilities.Assets;
import org.terasology.world.generation.EntityBuffer;
import org.terasology.world.generation.EntityProvider;
import org.terasology.world.generation.Region;

public class LightingRasterizer implements EntityProvider {
    private static final Logger logger = LoggerFactory.getLogger(LightingRasterizer.class);

    @Override
    public void process(Region region, EntityBuffer buffer) {
        LightingFacet lightingFacet = region.getFacet(LightingFacet.class);
        CaveSystemFacet solidFacet = region.getFacet(CaveSystemFacet.class);
        for (Vector3i position : region.getRegion()) {
            if (lightingFacet.getWorld(position.x, position.y, position.z)
                    && !solidFacet.getWorld(position.x, position.y, position.z)) {
                EntityStore store = new EntityStore(Assets.getPrefab("GooeysDepths:WorldLighting").orElse(null));
                LocationComponent component = new LocationComponent();
                component.setWorldPosition(position.toVector3f());
                store.addComponent(component);
                buffer.enqueue(store);
            }
        }
    }
}
