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

import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;

public class WorldFillingRasterizer implements WorldRasterizer {

    private Block dirt;
    private Block grass;
    private Block air;

    @Override
    public void initialize() {
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        dirt = blockManager.getBlock("Core:Dirt");
        grass = blockManager.getBlock("Core:Grass");
        air = blockManager.getBlock(BlockManager.AIR_ID);
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        CaveSystemFacet caveSystemFacet = chunkRegion.getFacet(CaveSystemFacet.class);
        GrassFacet grassFacet = chunkRegion.getFacet(GrassFacet.class);

        for (Vector3i position : chunkRegion.getRegion()) {
            if (caveSystemFacet.getWorld(position)) {
                if (false) {
                    chunk.setBlock(ChunkMath.calcBlockPos(position), grass);
                } else {
                    chunk.setBlock(ChunkMath.calcBlockPos(position), dirt);
                }
            } else {
                chunk.setBlock(ChunkMath.calcBlockPos(position), air);
            }
        }
    }
}
