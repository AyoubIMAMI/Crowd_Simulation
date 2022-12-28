public enum GridQuarterPosition {
    TOP_LEFT, TOP_RIGHT, BOT_LEFT, BOT_RIGHT;

    public static GridQuarterPosition getQuarterPosition(Position currentPosition, Grid grid) {
        int i = currentPosition.getI();
        int j = currentPosition.getJ();
        if(i > grid.lines)
            if(j > grid.columns) return GridQuarterPosition.BOT_RIGHT;
            else return GridQuarterPosition.BOT_LEFT;
        else
        if(j > grid.columns) return GridQuarterPosition.TOP_RIGHT;
        else return GridQuarterPosition.TOP_LEFT;
    }
}
