package dev.Roach.datamodel.game;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GameInfoPojo {
    private String title;
    private String steamAppID;
    private String thumb;
}
