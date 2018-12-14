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
package org.terasology.depths.caveGen.miner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.entitySystem.entity.EntityBuilder;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.delay.DelayManager;
import org.terasology.logic.delay.PeriodicActionTriggeredEvent;
import org.terasology.logic.players.LocalPlayer;
import org.terasology.logic.players.event.OnPlayerSpawnedEvent;
import org.terasology.registry.In;
import org.terasology.utilities.random.FastRandom;
import org.terasology.world.WorldProvider;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;

import java.util.ArrayList;
import java.util.List;

@RegisterSystem
public class MinerBuildSystem extends BaseComponentSystem {

    private static final Logger logger = LoggerFactory.getLogger(MinerBuildSystem.class);

    /**
     * Number of empty cells around the miner to kill it
     */
    private static final int KILL_NUM = 19;
    /**
     * Chance to spawn in a new miner each frame
     */
    private static final float CLONE_CHANCE = 0.6f;
    /**
     * Minimum number of solid blocks for a cell to survive
     */
    private static final int CELL_PRUNE_NUM = 6;

    /**
     * How often to tick through one iteration of the miners
     */
    private static final int UPDATE_RATE = 2000;
    private static final String UPDATE_ID = "depthsMinerUpdate";

    private final FastRandom random = new FastRandom();
    private Block airBlock;

    private List<Miner> miners = new ArrayList<>();
    private List<Miner> toRemove = new ArrayList<>();
    private List<Miner> toAdd = new ArrayList<>();

    @In
    private DelayManager delayManager;
    @In
    private WorldProvider worldProvider;
    @In
    private BlockManager blockManager;
    @In
    private LocalPlayer localPlayer;
    @In
    private EntityManager entityManager;

    @Override
    public void postBegin() {
        airBlock = blockManager.getBlock(BlockManager.AIR_ID);
        miners.add(new Miner(0, 0, 0));

        EntityBuilder entityBuilder = entityManager.newBuilder();
        entityBuilder.setPersistent(true);
        EntityRef entity = entityBuilder.build();
        delayManager.addPeriodicAction(entity, UPDATE_ID, 0, UPDATE_RATE);
    }

    /**
     * Starts the periodic action with the given entity once the world has loaded.
     * <p>
     * Called when the player is first spawned
     *
     * @see OnPlayerSpawnedEvent
     */
    @ReceiveEvent
    public void onPlayerSpawned(OnPlayerSpawnedEvent event, EntityRef entity) {
        delayManager.addPeriodicAction(entity, UPDATE_ID, 0, UPDATE_RATE);
    }


    private void processMiners() {
        logger.info("Processing " + miners.size() + " miners.");
        /* Process each miner */
        for (Miner miner : miners) {
            /* If the miner cannot survive */
            if (!isMinerSafe(miner)) {
                toRemove.add(miner);
                continue;
            }
            trySpawnClone(miner);
            moveMiner(miner);
            mineBlock(miner);
        }

        /* Remove dead miners */
        toRemove.forEach(miners::remove);
        toRemove.clear();
        /* Add new ones */
        miners.addAll(toAdd);
        toAdd.clear();
    }

    private boolean isMinerSafe(Miner miner) {
        int empty = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (worldProvider.getBlock(x + miner.pos.x, y + miner.pos.y, z + miner.pos.z) == airBlock) {
                        empty++;
                    }
                }
            }
        }
        return empty < KILL_NUM;
    }

    private void trySpawnClone(Miner miner) {
        if (random.nextFloat() < CLONE_CHANCE) {
            toAdd.add(new Miner(miner));
        }
    }

    private void moveMiner(Miner miner) {
        miner.pos.add(random.nextInt(-1, 1), random.nextInt(-1, 1), random.nextInt(-1, 1));
    }

    private void mineBlock(Miner miner) {
        worldProvider.setBlock(miner.pos, airBlock);
    }

    /**
     * Ticks all the miners over by one.
     * <p>
     * Called every {@link #UPDATE_RATE} milliseconds
     *
     * @see PeriodicActionTriggeredEvent
     */
    @ReceiveEvent
    public void onPeriodicActionTriggered(PeriodicActionTriggeredEvent event, EntityRef entity) {
        processMiners();
    }
}
