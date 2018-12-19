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
package org.terasology.depths.caveGen.worldGen;

import org.terasology.core.world.generator.facetProviders.FlatSurfaceHeightProvider;
import org.terasology.depths.caveGen.worldGen.caverSystem.CaveSystemProvider;
import org.terasology.depths.caveGen.worldGen.caverSystem.WorldFillingRasterizer;
import org.terasology.depths.caveGen.worldGen.lighting.LightingProvider;
import org.terasology.depths.caveGen.worldGen.lighting.LightingRasterizer;
import org.terasology.engine.SimpleUri;
import org.terasology.registry.In;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;

@RegisterWorldGenerator(id = "gooeysDepths", displayName = "Gooey's Depths", description = "The world generator for the Gooey's Depths gameplay")
public class GooeysDepthsWorldGenerator extends BaseFacetedWorldGenerator {

    @In
    private WorldGeneratorPluginLibrary worldGeneratorPluginLibrary;

    public GooeysDepthsWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    @Override
    protected WorldBuilder createWorld() {
        return new WorldBuilder(worldGeneratorPluginLibrary)
                .addProvider(new FlatSurfaceHeightProvider(0))
                .addProvider(new CaveSystemProvider())
                .addProvider(new LightingProvider())
                .addRasterizer(new WorldFillingRasterizer())
                .addEntities(new LightingRasterizer());
    }
}

