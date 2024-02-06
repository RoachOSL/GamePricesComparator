package dev.Roach.datamodel.gameLookup;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GameInfo {
    private String title;
    private String steamAppID;
    private String thumb;
}