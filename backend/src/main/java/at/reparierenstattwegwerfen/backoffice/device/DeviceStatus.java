package at.reparierenstattwegwerfen.backoffice.device;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Fabian Feichter
 */
@RequiredArgsConstructor
@Getter
public enum DeviceStatus {

    ORDERED("bestellt / gekauft", 0, Classification.COMMON, false),
    ARRIVED("eingetroffen", 1, Classification.COMMON, false),
    IN_REPAIR("in Reparatur", 2, Classification.COMMON, false),
    REPAIRED("repariert", 3, Classification.COMMON, false),
    LISTED_FOR_SALE("zum Verkauf angeboten", 4, Classification.COMMON, false),
    SOLD("verkauft", 5, Classification.COMMON, true),
    AVAILABLE_FOR_PARTS("zum Ausschlachten verfügbar", 6, Classification.COMMON, true),
    // ------
    WRONG_ITEM_ARRIVED("falscher Artikel eingetroffen", 7, Classification.RARE, true),
    RETURNED("zurückgegeben", 8, Classification.RARE, true),
    SELLER_CHANGED_MIND("Verkäufer/in hat sich anders entschieden", 9, Classification.RARE, true),
    NEVER_RECEIVED_NOT_REFUNDED("nie bekommen - Geld nicht zurückbekommen", 10, Classification.RARE, true),
    NEVER_RECEIVED_REFUNDED("nie bekommen - Geld zurückbekommen", 11, Classification.RARE, true);

    private final String displayName;
    private final int sortOrder;
    private final Classification classification;
    private final boolean endStatus;

    public static List<DeviceStatus> orderedBySortOrder() {
        return Stream.of(values())
                .sorted(Comparator.comparingInt(deviceStatus -> deviceStatus.sortOrder))
                .collect(Collectors.toList());
    }

    public static List<DeviceStatus> endStatuses() {
        return Stream.of(values())
                .filter(DeviceStatus::isEndStatus)
                .collect(Collectors.toList());
    }

    public static List<DeviceStatus> activeStatuses() {
        return Stream.of(values())
                .filter(DeviceStatus::isActive)
                .collect(Collectors.toList());
    }

    public boolean isActive() {
        return !endStatus;
    }

    public enum Classification {
        COMMON,
        RARE
    }
}