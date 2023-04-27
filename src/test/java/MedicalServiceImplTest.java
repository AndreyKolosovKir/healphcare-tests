import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

public class MedicalServiceImplTest {
    @Test
    public void testCheckBloodPressure() {
        String patientId = "testPatientId";

        PatientInfo patientInfo = new PatientInfo(patientId, "Иван", "Петров",
                LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(140, 80)));
        PatientInfoRepository patientInfoRepository = mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(eq(patientId))).thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);

        medicalService.checkBloodPressure(patientId, new BloodPressure(new BigDecimal("140"), new BigDecimal("120")));

        Mockito.verify(sendAlertService, Mockito.only()).send(anyString());
    }

    @Test
    public void testCheckTemperature (){
        String patientId = "testPatientId";
        PatientInfo patientInfo = new PatientInfo(patientId, "Иван", "Петров",
                LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(140, 80)));

        PatientInfoRepository patientInfoRepository = mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(eq(patientId))).thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);

        medicalService.checkTemperature(patientId, new BigDecimal("1.65"));

        Mockito.verify(sendAlertService, Mockito.only()).send(anyString());
    }

    @Test
    public void testCheckBloodPressureArgumentCaptor() {
        String patientId = "testPatientId";

        PatientInfo patientInfo = new PatientInfo(patientId, "Иван", "Петров",
                LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(110, 80)));
        PatientInfoRepository patientInfoRepository = mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(eq(patientId))).thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);

        medicalService.checkBloodPressure(patientId, new BloodPressure(new BigDecimal("110"), new BigDecimal("120")));

        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        Assertions.assertEquals(String.format("Warning, patient with id: %s, need help", patientId), argumentCaptor.getValue());
    }
}
