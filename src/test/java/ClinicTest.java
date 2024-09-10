import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClinicTest {
    private Clinic clinic;

    @BeforeEach
    void setUp(){
        TriageType doctorTriageType = TriageType.FIFO;
        TriageType radiologyTriageType = TriageType.FIFO;
        clinic = new Clinic(doctorTriageType, radiologyTriageType);
    }

    @Test
    void givenPatientWithMigraine_whenTriage_thenIsInMedecinQueueOnly(){

        clinic.triagePatient("John", 4, VisibleSymptom.MIGRAINE);

        assertEquals("John", clinic.medQueue.peek());
        assertTrue(clinic.radioQueue.isEmpty());
    }

    @Test
    void givenPatientWithBrokenBone_whenTriage_thenIsInMedecinAndRadioQueue(){

        clinic.triagePatient("John", 4, VisibleSymptom.BROKEN_BONE);

        assertEquals("John", clinic.medQueue.peek());
        assertEquals("John", clinic.radioQueue.peek());
    }

    @Test
    void givenPatients_whenNewFluPatientIsTriage_thenIsLastInMedicinQueue(){

        clinic.triagePatient("John", 4, VisibleSymptom.BROKEN_BONE);
        clinic.triagePatient("Martin", 4, VisibleSymptom.FLU);
        clinic.medQueue.poll();
        clinic.radioQueue.poll();

        assertEquals("Martin", clinic.medQueue.poll());
        assertTrue(clinic.radioQueue.isEmpty());
    }
}
