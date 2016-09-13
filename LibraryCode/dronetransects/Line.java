package webonise.mapboxdemo.dronetransects;

/**
 * Line is defined by starting point and ending point on 2D dimension.<br>
 * Copyright 2013-present Roman Kushnarenko
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Roman Kushnarenko (sromku@gmail.com)
 * @Original-Git-repo-link : https://github.com/sromku/polygon-contains-point
 */
public class Line {
    private final webonise.mapboxdemo.dronetransects.Point _start;
    private final webonise.mapboxdemo.dronetransects.Point _end;
    private double _a = Double.NaN;
    private double _b = Double.NaN;
    private boolean _vertical = false;

    public Line(webonise.mapboxdemo.dronetransects.Point start, webonise.mapboxdemo.dronetransects.Point end) {
        _start = start;
        _end = end;

        if (_end.x - _start.x != 0) {
            _a = ((_end.y - _start.y) / (_end.x - _start.x));
            _b = _start.y - _a * _start.x;
        } else {
            _vertical = true;
        }
    }

    /**
     * Indicate whereas the point lays on the line.
     *
     * @param point - The point to check
     * @return <code>True</code> if the point lays on the line, otherwise return <code>False</code>
     */
    public boolean isInside(webonise.mapboxdemo.dronetransects.Point point) {
        double maxX = _start.x > _end.x ? _start.x : _end.x;
        double minX = _start.x < _end.x ? _start.x : _end.x;
        double maxY = _start.y > _end.y ? _start.y : _end.y;
        double minY = _start.y < _end.y ? _start.y : _end.y;

        if ((point.x >= minX && point.x <= maxX) && (point.y >= minY && point.y <= maxY)) {
            return true;
        }
        return false;
    }

    /**
     * Indicate whereas the line is vertical. <br>
     * For example, line like x=1 is vertical, in other words parallel to axis Y. <br>
     * In this case the A is (+/-)infinite.
     *
     * @return <code>True</code> if the line is vertical, otherwise return <code>False</code>
     */
    public boolean isVertical() {
        return _vertical;
    }

    /**
     * y = <b>A</b>x + B
     *
     * @return The <b>A</b>
     */
    public double getA() {
        return _a;
    }

    /**
     * y = Ax + <b>B</b>
     *
     * @return The <b>B</b>
     */
    public double getB() {
        return _b;
    }

    /**
     * Get start point
     *
     * @return The start point
     */
    public webonise.mapboxdemo.dronetransects.Point getStart() {
        return _start;
    }

    /**
     * Get end point
     *
     * @return The end point
     */
    public Point getEnd() {
        return _end;
    }

    @Override
    public String toString() {
        return String.format("%s-%s", _start.toString(), _end.toString());
    }
}