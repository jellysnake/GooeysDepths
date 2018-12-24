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
import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;

public class LightingRasterizer implements WorldRasterizer {
    private static final Logger logger = LoggerFactory.getLogger(LightingRasterizer.class);

    private Block dirt;
    private Block grass;

    @Override
    public void initialize() {
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        dirt = blockManager.getBlock("GooeysDepths:GlowingBlock");
        grass = blockManager.getBlock("GooeysDepths:GlowingGrass");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        CaveSystemFacet caveSystemFacet = chunkRegion.getFacet(CaveSystemFacet.class);
        LightingFacet lightingFacet = chunkRegion.getFacet(LightingFacet.class);
        for (Vector3i position : chunkRegion.getRegion()) {
            if (lightingFacet.getWorld(position)) {
                switch (caveSystemFacet.getWorld(position)) {
                    case CaveSystemFacet.GRASS:
                        chunk.setBlock(ChunkMath.calcBlockPos(position), grass);
                        break;
                    case CaveSystemFacet.AIR:
                        break;
                    default:
                        chunk.setBlock(ChunkMath.calcBlockPos(position), dirt);
                }
            }
        }
    }
}
