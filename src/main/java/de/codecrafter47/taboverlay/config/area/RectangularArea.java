package de.codecrafter47.taboverlay.config.area;

import com.google.common.base.Preconditions;
import de.codecrafter47.taboverlay.Icon;
import de.codecrafter47.taboverlay.handler.RectangularTabOverlay;

import java.util.UUID;

public interface RectangularArea extends Area {

    @Override
    default void setSlot(int index, UUID uuid, Icon icon, String text, int ping) {
        setSlot(index % getColumns(), index / getColumns(), uuid, icon, text, ping);
    }

    @Override
    default void setSlot(int index, Icon icon, String text, char alternateColorChar, int ping) {
        setSlot(index % getColumns(), index / getColumns(), icon, text, alternateColorChar, ping);
    }

    @Override
    default void setSlot(int index, Icon icon, String text, int ping) {
        setSlot(index % getColumns(), index / getColumns(), icon, text, ping);
    }

    @Override
    default void setSlot(int index, UUID uuid, Icon icon, String text, char alternateColorChar, int ping) {
        setSlot(index % getColumns(), index / getColumns(), uuid, icon, text, alternateColorChar, ping);
    }

    @Override
    default void setUuid(int index, UUID uuid) {
        setUuid(index % getColumns(), index / getColumns(), uuid);
    }

    @Override
    default void setIcon(int index, Icon icon) {
        setIcon(index % getColumns(), index / getColumns(), icon);
    }

    @Override
    default void setText(int index, String text) {
        setText(index % getColumns(), index / getColumns(), text);
    }

    @Override
    default void setText(int index, String text, char alternateColorChar) {
        setText(index % getColumns(), index / getColumns(), text, alternateColorChar);
    }

    @Override
    default void setPing(int index, int ping) {
        setPing(index % getColumns(), index / getColumns(), ping);
    }

    default void setSlot(int column, int row, Icon icon, String text, int ping) {
        setSlot(column, row, null, icon, text, ping);
    }

    default void setSlot(int column, int row, Icon icon, String text, char alternateColorChar, int ping) {
        setSlot(column, row, null, icon, text, alternateColorChar, ping);
    }

    void setSlot(int column, int row, UUID uuid, Icon icon, String text, int ping);

    void setSlot(int column, int row, UUID uuid, Icon icon, String text, char alternateColorChar, int ping);

    void setUuid(int column, int row, UUID uuid);

    void setIcon(int column, int row, Icon icon);

    void setText(int column, int row, String text);

    void setText(int column, int row, String text, char alternateColorChar);

    void setPing(int column, int row, int ping);

    int getRows();

    int getColumns();

    default RectangularArea createRectangularChild(int column, int row, int columns, int rows) {
        Preconditions.checkArgument(column < getColumns(), "column < getColumns()");
        Preconditions.checkArgument(row < getRows(), "row < getRows()");
        Preconditions.checkArgument(column + columns <= getColumns(), "column + columns <= getColumns()");
        Preconditions.checkArgument(row + rows <= getRows(), "row + rows <= getRows()");
        return new RectangularAreaWithOffset(this, column, row, columns, rows);
    }

    @Override
    default RectangularArea asRectangularArea() {
        return this;
    }

    static RectangularArea of(RectangularTabOverlay tabOverlay) {
        int columns = tabOverlay.getSize().getColumns();
        int rows = tabOverlay.getSize().getRows();
        int size = columns * rows;
        return new RectangularArea() {
            @Override
            public void setSlot(int column, int row, UUID uuid, Icon icon, String text, int ping) {
                tabOverlay.setSlot(column, row, uuid, icon, text, ping);
            }

            @Override
            public void setSlot(int column, int row, UUID uuid, Icon icon, String text, char alternateColorChar, int ping) {
                tabOverlay.setSlot(column, row, uuid, icon, text, alternateColorChar, ping);
            }

            @Override
            public void setUuid(int column, int row, UUID uuid) {
                tabOverlay.setUuid(column, row, uuid);
            }

            @Override
            public void setIcon(int column, int row, Icon icon) {
                tabOverlay.setIcon(column, row, icon);
            }

            @Override
            public void setText(int column, int row, String text) {
                tabOverlay.setText(column, row, text);
            }

            @Override
            public void setText(int column, int row, String text, char alternateColorChar) {
                tabOverlay.setText(column, row, text, alternateColorChar);
            }

            @Override
            public void setPing(int column, int row, int ping) {
                tabOverlay.setPing(column, row, ping);
            }

            @Override
            public int getRows() {
                return rows;
            }

            @Override
            public int getColumns() {
                return columns;
            }

            @Override
            public int getSize() {
                return size;
            }
        };
    }
}
