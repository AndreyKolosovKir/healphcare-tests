import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;

public class PatientInfoFileRepositoryMock implements PatientInfoRepository {

    PatientInfo patientInfo;
    String patientId;

    @Override
    public PatientInfo getById(String id) {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    @Override
    public String add(PatientInfo patientInfo) {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Override
    public RuntimeException remove(String id) {
        return new RuntimeException("Not implemented");
    }

    @Override
    public PatientInfo update(PatientInfo patientInfo) {
        return null;
    }
}
