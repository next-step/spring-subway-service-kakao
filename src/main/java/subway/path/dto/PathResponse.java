package subway.path.dto;

import subway.path.domain.Path;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PathResponse {
    private List<StationResponse> stations;
    private int distance;
    private int fare;

    public PathResponse() {
    }

    public PathResponse(List<StationResponse> stations, int distance) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
    }

    public PathResponse(PathResult pathResult){
        this.stations = pathResult.getStations()
                .stream()
                .map(i -> new StationResponse(i.getId(), i.getName()))
                .collect(Collectors.toList());
        this.distance = pathResult.getDistance();
    }


    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }
}
