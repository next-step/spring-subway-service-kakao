package subway.path.domain;

import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;

public class Path {
    private List<Station> stations;
    private int distance;
    private int fare;

    public Path(List<Station> stations, int distance, int fare) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }

    public List<Station> getStations() {
        return stations;
    }
}
