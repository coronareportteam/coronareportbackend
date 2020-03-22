package de.wevsvirushackathon.coronareport.firstReport;

import de.wevsvirushackathon.coronareport.user.Client;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
