package ru.atom.geometry;

public class Bar implements Collider {
    private Point point1;
    private Point point2;

    public Bar(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        return Math.abs(point1.getX() - point2.getX())*
                Math.abs(point1.getY() - point2.getY()) ==
                Math.abs(bar.getPoint1().getX() - bar.getPoint2().getX())*
                Math.abs(bar.getPoint1().getY() - bar.getPoint2().getY());
    }

    @Override
    public boolean isColliding(Collider other) {
        int thisY1 = point1.getY();
        int thisY2 = point2.getY();
        int thisX1 = point1.getX();
        int thisX2 = point2.getX();
        if(thisY1 > thisY2) {
            thisY1 = thisY1^thisY2;
            thisY2 = thisY2^thisY1;
            thisY1 = thisY1^thisY2;
        }
        if(thisX1 > thisX2) {
            thisX1 = thisX1^thisX2;
            thisX2 = thisX2^thisX1;
            thisX1 = thisX1^thisX2;
        }

        if(other instanceof Point) {
            for(int i = point1.getY(); i <= point2.getY(); ++i) {
                for(int j = point1.getX(); j <= point2.getX(); ++j) {
                    if(new Point(j, i).equals(other)) {
                        return true;
                    }
                }
            }
            return false;
        } else if(other instanceof Bar) {
            Bar bar = (Bar) other;
            int barY1 = bar.getPoint1().getY();
            int barY2 = bar.getPoint2().getY();
            int barX1 = bar.getPoint1().getX();
            int barX2 = bar.getPoint2().getX();
            if(barY1 > barY2) {
                barY1 = barY1^barY2;
                barY2 = barY2^barY1;
                barY1 = barY1^barY2;
            }
            if(barX1 > barX2) {
                barY1 = barY1^barY2;
                barY2 = barY2^barY1;
                barY1 = barY1^barY2;
            }
            for(int y2 = barY1; y2 <= barY2; ++y2) {
                for (int x2 = barX1; x2 <= barX2; ++x2) {
                    Point point = new Point(x2, y2);
                    for(int y1 = thisY1; y1 <= thisY2; ++y1) {
                        for(int x1 = thisX1; x1 <= thisX2; ++x1) {
                            if(new Point(x1, y1).equals(point)) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
