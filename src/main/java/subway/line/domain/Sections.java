package subway.line.domain;

import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Sections {
    private final List<Section> sections;

    public List<Section> getSections() {
        return sections;
    }

    public Sections() {
        sections = new ArrayList<>();
    }

    public Sections(List<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        if (this.sections.isEmpty()) {
            this.sections.add(section);
            return;
        }

        checkAlreadyExisted(section);
        checkExistedAny(section);

        addSectionUpToUp(section);
        addSectionDownToDown(section);

        this.sections.add(section);
    }

    private void checkAlreadyExisted(Section section) {
        List<Station> stations = getStations();
        if (!stations.contains(section.getUpStation()) && !stations.contains(section.getDownStation())) {
            throw new IllegalArgumentException("At least one section should be already added");
        }
    }

    private void checkExistedAny(Section section) {
        List<Station> stations = getStations();
        List<Station> stationsOfNewSection = Arrays.asList(section.getUpStation(), section.getDownStation());
        if (stations.containsAll(stationsOfNewSection)) {
            throw new IllegalArgumentException("Section already exists");
        }
    }

    private void addSectionUpToUp(Section section) {
        this.sections.stream()
                .filter(it -> it.getUpStation().equals(section.getUpStation()))
                .findFirst()
                .ifPresent(it -> replaceSectionWithDownStation(section, it));
    }

    private void addSectionDownToDown(Section section) {
        this.sections.stream()
                .filter(it -> it.getDownStation().equals(section.getDownStation()))
                .findFirst()
                .ifPresent(it -> replaceSectionWithUpStation(section, it));
    }

    private void replaceSectionWithUpStation(Section newSection, Section existSection) {
        if (existSection.getDistance() <= newSection.getDistance()) {
            throw new IllegalArgumentException("New section cannot have distance larger than existing one");
        }
        this.sections.add(new Section(existSection.getUpStation(), newSection.getUpStation(), existSection.getDistance() - newSection.getDistance()));
        this.sections.remove(existSection);
    }

    private void replaceSectionWithDownStation(Section newSection, Section existSection) {
        if (existSection.getDistance() <= newSection.getDistance()) {
            throw new IllegalArgumentException("New section cannot have distance larger than existing one");
        }
        this.sections.add(new Section(newSection.getDownStation(), existSection.getDownStation(), existSection.getDistance() - newSection.getDistance()));
        this.sections.remove(existSection);
    }

    public List<Station> getStations() {
        if (sections.isEmpty()) {
            return Arrays.asList();
        }

        List<Station> stations = new ArrayList<>();
        Section upEndSection = findUpEndSection();
        stations.add(upEndSection.getUpStation());

        Section nextSection = upEndSection;
        while (nextSection != null) {
            stations.add(nextSection.getDownStation());
            nextSection = findSectionByNextUpStation(nextSection.getDownStation());
        }

        return stations;
    }

    private Section findUpEndSection() {
        List<Station> downStations = this.sections.stream()
                .map(Section::getDownStation)
                .collect(Collectors.toList());

        return this.sections.stream()
                .filter(it -> !downStations.contains(it.getUpStation()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private Section findSectionByNextUpStation(Station station) {
        return this.sections.stream()
                .filter(it -> it.getUpStation().equals(station))
                .findFirst()
                .orElse(null);
    }

    public void removeStation(Station station) {
        if (sections.size() <= 1) {
            throw new IllegalArgumentException("Cannot delete if there is only one section left");
        }

        Optional<Section> upSection = sections.stream()
                .filter(it -> it.getUpStation().equals(station))
                .findFirst();
        Optional<Section> downSection = sections.stream()
                .filter(it -> it.getDownStation().equals(station))
                .findFirst();

        if (upSection.isPresent() && downSection.isPresent()) {
            Station newUpStation = downSection.get().getUpStation();
            Station newDownStation = upSection.get().getDownStation();
            int newDistance = upSection.get().getDistance() + downSection.get().getDistance();
            sections.add(new Section(newUpStation, newDownStation, newDistance));
        }

        upSection.ifPresent(sections::remove);
        downSection.ifPresent(sections::remove);
    }
}
