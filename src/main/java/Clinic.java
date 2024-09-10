import java.util.LinkedList;
import java.util.Queue;

public class Clinic {
        private TriageType triageTypeMedecin;
        private TriageType triageTypeRadio;
        public Queue<Patient> medQueue = new LinkedList<>();
        public Queue<Patient> radioQueue = new LinkedList<>();

        public Clinic(TriageType triageTypeMedecin, TriageType triageTypeRadio) {
                this.triageTypeMedecin = triageTypeMedecin;
                this.triageTypeRadio = triageTypeRadio;
        }

        public void triagePatient(String name, int gravity, VisibleSymptom visibleSymptom) {
                Patient patient = new Patient(name, gravity);

            if(triageTypeMedecin == TriageType.FIFO){
                medQueue.add(patient);
            }

            if(triageTypeMedecin == TriageType.GRAVITY){
                    if(medQueue.isEmpty()){
                            medQueue.add(patient);
                    }
                    else{
                        if(patient.gravity > 5){
                                Queue<Patient> tempMedQueue = new LinkedList<>();
                                tempMedQueue.addAll(medQueue);
                                medQueue.clear();
                                medQueue.add(patient);
                                medQueue.addAll(tempMedQueue);
                        }
                        else{
                            medQueue.add(patient);
                        }
                    }
            }

            if (visibleSymptom == VisibleSymptom.SPRAIN || visibleSymptom == VisibleSymptom.BROKEN_BONE){
                    radioQueue.add(patient);
            }
        }

        // D'autres méthodes peuvent être nécessaires

        public static void main(String[] args) {
                // Ceci n'est pas un test!! C'est un exemple d'utilisation.
                TriageType doctorTriageType = TriageType.FIFO;
                TriageType radiologyTriageType = TriageType.FIFO;

                Clinic clinic = new Clinic(doctorTriageType, radiologyTriageType);
                clinic.triagePatient("John", 4, VisibleSymptom.BROKEN_BONE);

                System.out.println(clinic.medQueue.peek());
                System.out.println(clinic.radioQueue.peek());
        }

}
