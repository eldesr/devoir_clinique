import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClinicTest {
    private Clinic clinic;
    private TriageType doctorTriageType;
    private TriageType radiologyTriageType;

    @Test
    void givenFIFOPatientWithMigraine_whenTriage_thenIsInDoctorQueueOnly(){
        doctorTriageType = TriageType.FIFO;
        radiologyTriageType = TriageType.FIFO;
        clinic = new Clinic(doctorTriageType, radiologyTriageType);

        clinic.triagePatient("John", 4, VisibleSymptom.MIGRAINE);

        assertEquals("John", clinic.medQueue.peek().name);
        assertTrue(clinic.radioQueue.isEmpty());
    }

    @Test
    void givenFIFOPatientWithBrokenBone_whenTriage_thenIsInDoctorAndRadioQueue(){
        doctorTriageType = TriageType.FIFO;
        radiologyTriageType = TriageType.FIFO;
        clinic = new Clinic(doctorTriageType, radiologyTriageType);

        clinic.triagePatient("John", 4, VisibleSymptom.BROKEN_BONE);

        assertEquals("John", clinic.medQueue.peek().name);
        assertEquals("John", clinic.radioQueue.peek().name);
    }

    @Test
    void givenFIFOPatients_whenNewFluPatientIsTriage_thenIsLastInDoctorQueue(){
        doctorTriageType = TriageType.FIFO;
        radiologyTriageType = TriageType.FIFO;
        clinic = new Clinic(doctorTriageType, radiologyTriageType);

        clinic.triagePatient("John", 4, VisibleSymptom.BROKEN_BONE);
        clinic.triagePatient("Martin", 4, VisibleSymptom.FLU);
        clinic.medQueue.poll();
        clinic.radioQueue.poll();

        assertEquals("Martin", clinic.medQueue.poll().name);
        assertTrue(clinic.radioQueue.isEmpty());
    }

    @Test
    void givenGravityDoctorQueueFIFORadioQueueAndPatientWithHigherThan5_whenTriage_thenIsFirstInDoctorQueue(){
        doctorTriageType = TriageType.GRAVITY;
        radiologyTriageType = TriageType.FIFO;
        clinic = new Clinic(doctorTriageType, radiologyTriageType);

        clinic.triagePatient("John", 4, VisibleSymptom.BROKEN_BONE);
        clinic.triagePatient("Martin", 9, VisibleSymptom.FLU);

        assertEquals("Martin", clinic.medQueue.peek().name);
        assertEquals("John", clinic.radioQueue.peek().name);
    }
}
