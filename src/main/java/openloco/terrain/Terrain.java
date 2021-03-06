package openloco.terrain;

public class Terrain {

    private int[] tileHeights;
    private int[] tileTypes;
    private int[] cornerHeight;
    private String[] groundTypes;

    private int xMax;
    private int yMax;

    public static final int W = 0;
    public static final int S = 1;
    public static final int E = 2;
    public static final int N = 3;

    public Terrain(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
        int tileCount = xMax*yMax;
        tileHeights = new int[tileCount];
        tileTypes = new int[tileCount];
        cornerHeight = new int[4 * tileCount];
        groundTypes = new String[tileCount];
    }

    private int tileIndex(int i, int j) {
        return i + j * xMax;
    }

    private void computeCornerHeight(int tileIndex) {
        int tileHeight = tileHeights[tileIndex];
        int tileType = tileTypes[tileIndex];

        cornerHeight[4*tileIndex + W] = tileHeight + (tileType & 1);
        cornerHeight[4*tileIndex + S] = tileHeight + ((tileType & 2) >> 1);
        cornerHeight[4*tileIndex + E] = tileHeight + ((tileType & 4) >> 2);
        cornerHeight[4*tileIndex + N] = tileHeight + ((tileType & 8) >> 3);
    }

    public int getXMax() {
        return xMax;
    }

    public int getYMax() {
        return yMax;
    }

    public int getTileType(int i, int j) {
        return tileTypes[tileIndex(i, j)];
    }

    public int getTileHeight(int i, int j) {
        return tileHeights[tileIndex(i, j)];
    }

    public void setTileType(int i, int j, int tileType) {
        int tileIndex = tileIndex(i, j);
        tileTypes[tileIndex] = tileType;
        computeCornerHeight(tileIndex);
    }

    public int getCornerHeight(int i, int j, int corner) {
        int tileIndex = tileIndex(i, j);
        return cornerHeight[4*tileIndex + corner];
    }

    public void setTileHeight(int i, int j, int height) {
        int tileIndex = tileIndex(i, j);
        tileHeights[tileIndex] = height;
        computeCornerHeight(tileIndex);
    }

    public String getGroundType(int i, int j) {
        int tileIndex = tileIndex(i, j);
        String groundType = groundTypes[tileIndex];
        return groundType == null ? "GRASS1" : groundType;
    }

    public void setGroundType(int i, int j, String groundType) {
        int tileIndex = tileIndex(i, j);
        groundTypes[tileIndex] = groundType;
    }
}
