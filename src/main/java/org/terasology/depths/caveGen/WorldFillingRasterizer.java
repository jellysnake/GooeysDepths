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
package org.terasology.depths.caveGen;

import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;

public class WorldFillingRasterizer implements WorldRasterizer {

    private Block dirt;
    private Block air;

    @Override
    public void initialize() {
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        dirt = blockManager.getBlock("Core:dirt");
        air = blockManager.getBlock(BlockManager.AIR_ID);
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        /* Fill it all in */
        for (int x = 0; x < chunk.getChunkSizeX(); ++x) {
            for (int z = 0; z < chunk.getChunkSizeZ(); ++z) {
                for (int y = 0; y < chunk.getChunkSizeY(); ++y) {
                    chunk.setBlock(x, y, z, dirt);
                }
            }
        }

        /* We want to leave a space for the player */
        Vector3i zero = new Vector3i(0, 0, 0);
        if (chunkRegion.getRegion().encompasses(zero)) {
            for (int x = 0; x < 3; ++x) {
                for (int z = 0; z < 3; ++z) {
                    for (int y = 0; y < 3; ++y) {
                        chunk.setBlock(x, y, z, air);
                    }
                }
            }
        }
    }
}
