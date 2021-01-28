package subway.path.dto;

import subway.path.domain.PathVertices;
import subway.station.domain.Station;

import java.util.List;

public class PathResult {
    private PathVertices pathVertices;
    private int distance;

    public PathResult() {
    }
    public PathResult(PathVertices pathVertices, int distance) {
        this.pathVertices = pathVertices;
        this.distance = distance;
    }
    public PathVertices getPathVertices() {
        return pathVertices;
    }

    public int getDistance() {
        return distance;
    }
}