package de.wevsvirushackathon.coronareport.diary;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class FirstReport {

    @Id
    private LocalDateTime dateTime;

    private boolean min15MinutesContactWithC19Pat;
    private boolean nursingActionOnC19Pat;
    private boolean directContactWithLiquidsOfC19pat;
    private boolean flightPassengerCloseRowC19Pat;
    private boolean flightCrewMemberWithC19Pat;
    private boolean belongToMedicalStaff;
    private boolean belongToNursingStaff;
    private boolean belongToLaboratoryStaff;
    private boolean familyMember;
    private boolean isPassengerOnSameFlightAsPatient;
    private boolean OtherContactType;

}
