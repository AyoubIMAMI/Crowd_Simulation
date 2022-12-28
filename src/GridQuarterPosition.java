public enum GridQuarterPosition {
    TOP_LEFT, TOP_RIGHT, BOT_LEFT, BOT_RIGHT;

    public static GridQuarterPosition getQuarterPosition(Position currentPosition, Grid grid) {
        int i = currentPosition.getI();
        int j = currentPosition.getJ();
        if(i > grid.lines/2)
            if(j > grid.columns/2) return GridQuarterPosition.BOT_RIGHT;
            else return GridQuarterPosition.BOT_LEFT;
        else
        if(j > grid.columns/2) return GridQuarterPosition.TOP_RIGHT;
        else return GridQuarterPosition.TOP_LEFT;
    }
}
