package dev.foltz.stoneage.world.gen.desc;

import dev.foltz.stoneage.world.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;

public class WorldDescIsland extends WorldDesc {
    private static final double SCALE_INLAND_HEIGHT = 0.08d;
    private static final double SCALE_LANDMASS = 2d;

    private final int centerX, centerZ;
    private final int minRadius, maxRadius;
    private final int waterLevel;
    private final int maxHeight;
    private final OpenSimplexNoise noise;
    private final SharedSeedRandom random;

    public WorldDescIsland(int x, int z, int minRadius, int maxRadius, int waterLevel, int maxHeight, long seed) {
        centerX = x;
        centerZ = z;
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.waterLevel = waterLevel;
        this.maxHeight = maxHeight;
        noise = new OpenSimplexNoise(seed);
        random = new SharedSeedRandom(seed);
    }

    double radiusFor(double theta) {
        double nx = Math.cos(theta) * SCALE_LANDMASS;
        double ny = Math.sin(theta) * SCALE_LANDMASS;
        return (float) (1 + noise.eval(nx, ny)) / 2 * (maxRadius - minRadius) + minRadius;
    }

    int heightFor(int x, int z) {
        int dx = centerX - x;
        int dz = centerZ - z;
        double xx = x - centerX;
        double zz = z - centerZ;
        double theta = Math.atan2(zz, xx);
        double radius = radiusFor(theta) + noise.eval(Math.cos(theta) * dx * SCALE_INLAND_HEIGHT, Math.sin(theta) * dz * SCALE_INLAND_HEIGHT) * (maxRadius - minRadius) * 0.25;
        double dist = Math.sqrt(dx * dx + dz * dz);
        // dist <= radius

        double propDistToCenter = 1 - dist / radius;

        double L = 1;
        double k = 14;
        double m = 0.4d;
        double varianceDist = (L / (1 + Math.pow(Math.E, -k * (propDistToCenter - m))));
        double varianceNoise = (1 + noise.eval(x * SCALE_INLAND_HEIGHT, z * SCALE_INLAND_HEIGHT)) / 2.0d;
        double variance = (varianceDist * 0.6 +  varianceNoise * varianceDist * propDistToCenter * 0.4) * maxHeight;

        return (int) Math.round(waterLevel + variance);
    }

    @Override
    public BlockState blockAt(int x, int y, int z) {
        if ((y > waterLevel + maxHeight + 1)
         || (x > centerX + maxRadius) || (x < centerX - maxRadius)
         || (z > centerZ + maxRadius) || (z < centerZ - maxRadius)) {
            return AIR;
        }

        int dx = centerX - x;
        int dz = centerZ - z;
        double xx = x - centerX;
        double zz = z - centerZ;
        double d_sq = dx * dx + dz * dz;
        if (d_sq > maxRadius * maxRadius) {
            return AIR;
        }
        double theta = Math.atan2(zz, xx);
        //System.out.print("(" + x  + ", " + y + ": " + theta + "), ");
        double radius = radiusFor(theta);
        if (d_sq > radius * radius) return AIR;

        int height = heightFor(x, z);
        if (y > height + 1) {
            return AIR;
        }
        // Shore
        else if (height == waterLevel) {
            if (height >= height - 3 && y <= height) {
                return SAND;
            }
            else {
                return AIR;
            }
        }
        // Mainland -> Top decoration
        else if (y == height + 1) {
            random.setBaseChunkSeed(x, z);
            double val = random.nextDouble();
            if (val <= 0.75) {
                if (val <= 0.5) {
                    return SHRUB;
                }
                else {
                    return Blocks.TALL_GRASS.getDefaultState();
                }
            }
            else if (val <= 0.79) {
                return ROCK_FLINT_SMALL;
            }
            else if (val <= 0.80) {
                return ROCK_FLINT_MEDIUM;
            }
            else if (val <= 0.84) {
                return ROCK_GRANITE_SMALL;
            }
            else if (val <= 0.85) {
                return ROCK_GRANITE_MEDIUM;
            }
            else {
                return AIR;
            }
        }
        // Grass
        else if (y == height) {
            return GRASS;
        }
        else if (y >= height - 3) {
            return DIRT;
        }
        else {
            return STONE;
        }
    }
}
