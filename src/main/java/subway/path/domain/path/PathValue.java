package subway.path.domain.path;

import subway.station.domain.Station;

import java.util.List;

public class PathValue {
    private List<Station> stations;
    private int distance;
    private int fare;

    public PathValue(List<Station> stations, int distance, int fare) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
    }

    public List<Station> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }
}