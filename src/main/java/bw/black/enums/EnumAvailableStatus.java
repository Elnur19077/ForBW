package bw.black.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum EnumAvailableStatus {

    ACTIVE(1), DEACTIVE(0);


    private int value;

}
