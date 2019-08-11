/*
 * Copyright 2019 MovingBlocks
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

import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;


public class FloraRasterizer implements WorldRasterizer {

    private Block[] shrooms = new Block[FloraFacet.FLOWERS.length];

    @Override
    public void initialize() {
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        for (int i = 0; i < FloraFacet.FLOWERS.length; i++) {
            shrooms[i] = blockManager.getBlock(FloraFacet.FLOWERS[i]);
        }
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        FloraFacet floraFacet = chunkRegion.getFacet(FloraFacet.class);

        for (Vector3i position : floraFacet.getRelativeRegion()) {
            int flower = floraFacet.getWorld(position);
            if (flower < FloraFacet.FLOWERS.length) {
                chunk.setBlock(position, shrooms[flower]);
            }
        }
    }
}
