package de.wevsvirushackathon.coronareport.firstReport;

import de.wevsvirushackathon.coronareport.user.Client;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class FirstReport {




    @Id
    @GeneratedValue
    private Long id;


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
