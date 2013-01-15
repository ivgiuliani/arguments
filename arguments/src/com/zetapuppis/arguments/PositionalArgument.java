package com.zetapuppis.arguments;

public class PositionalArgument implements Comparable<PositionalArgument>, ArgumentItem {
    private final String mName;
    private final int mPosition;

    public PositionalArgument(final String name, final int position) {
        mName = name;
        mPosition = position;
    }

    public String getName() {
        return mName;
    }

    public int getPosition() {
        return mPosition;
    }

    @Override
    public int compareTo(PositionalArgument positionalArgument) {
        return Integer.compare(mPosition, positionalArgument.getPosition());
    }
}
