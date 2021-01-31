package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FareCalculator {
	public static int calculate(Path shortestPath, List<Line> lines, int age) {
		DistanceFare distanceFare = DistanceFare.getDistanceFare(shortestPath.getDistance());
		int fare = distanceFare.calculateExtraFareByDistance(shortestPath.getDistance());

		fare += getMaxExtraFareOfLine(shortestPath.getStations(), lines);

		AgeFare ageFare = AgeFare.getAgeFare(age);
		fare -= ageFare.calculateDiscountFareByAge(fare);

		return fare;
	}

	private static int getMaxExtraFareOfLine(List<Station> stations, List<Line> lines) {
		Sections allSections = new Sections();
		lines.forEach(line -> allSections.addSections(line.getSections()));
		Set<Integer> extraFares = new HashSet<>();

		for (int i = 0; i < stations.size() - 1; i++) {
			Station upStation = stations.get(i);
			Station downStation = stations.get(i + 1);
			Section section = allSections.getSection(upStation, downStation);
			extraFares.add(lines.stream().filter(line -> line.getSections().hasSection(section)).findFirst().get()
					.getExtraFare());
		}

		return Collections.max(extraFares);
	}
}
